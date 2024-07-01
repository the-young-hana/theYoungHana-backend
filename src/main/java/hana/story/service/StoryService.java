package hana.story.service;

import hana.account.dto.DeptAccountTransactionResDto;
import hana.account.service.TransactionService;
import hana.college.service.DeptService;
import hana.common.annotation.TypeInfo;
import hana.common.utils.ImageUtils;
import hana.story.domain.Story;
import hana.story.domain.StoryRepository;
import hana.story.dto.*;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@TypeInfo(name = "StoryService", description = "스토리 서비스")
@Service
public class StoryService {
    private final StoryRepository storyRepository;
    private final StoryCommentService storyCommentService;
    private final StoryLikeService storyLikeService;
    private final TransactionService transactionService;
    private final TransactionDetailService transactionDetailService;
    private final DeptService deptService;
    private final ImageUtils imageUtils;

    @Transactional
    public StoriesReadResDto getStories(Integer page, Long deptIdx) {

        // deptIdx로 스토리 조회
        Page<Story> stories =
                storyRepository.findByDept_DeptIdxAndDeletedYn(
                        deptIdx,
                        PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "createdAt")),
                        false);

        // 모두 합쳐서 거래내역 생성.
        List<StoriesReadResDto.Data> datas = new ArrayList<>();

        for (Story s : stories) {
            long storyIdx = s.getStoryIdx();
            long storyCommentNum = storyCommentService.getStoryCommentNum(storyIdx);
            long storyLikeNum = storyLikeService.getStoryLikeNum(storyIdx);
            LocalDateTime createdAt = s.getCreatedAt();
            List<DeptAccountTransactionResDto> transactionList =
                    transactionService.getTransactionsByStory(storyIdx);

            datas.add(
                    StoriesReadResDto.Data.builder()
                            .storyCommentNum(storyCommentNum)
                            .storyIdx(storyIdx)
                            .storyLikeNum(storyLikeNum)
                            .storyTitle(s.getStoryTitle())
                            .transactionList(transactionList)
                            .createdAt(createdAt)
                            .build());
        }

        return StoriesReadResDto.builder().data(datas).build();
    }

    @Transactional
    public StoryReadResDto getStory(Long storyIdx) {
        Story story =
                storyRepository
                        .findById(storyIdx)
                        .orElseThrow(() -> new RuntimeException("스토리를 찾을 수 없습니다."));
        Matcher matcher =
                Pattern.compile("https?://[^,\\s]+.png").matcher(story.getStoryImageList());

        List<String> imageList = new ArrayList<>();

        while (matcher.find()) {
            imageList.add(matcher.group());
        }

        return makeStoryResDto(
                storyIdx,
                story.getStoryTitle(),
                storyLikeService.getStoryLikeNum(storyIdx),
                storyCommentService.getStoryCommentNum(storyIdx),
                story.getStoryContent(),
                imageList,
                storyCommentService.getStoryComment(storyIdx),
                transactionService.getTransactionsByStory(storyIdx),
                story.getCreatedAt());
    }

    @Transactional
    public StoryReadResDto createStory(StoryCreateReqDto reqDto, List<MultipartFile> imgs) {

        Story story =
                Story.builder()
                        .storyTitle(reqDto.getStoryTitle())
                        .storyContent(reqDto.getStoryContent())
                        .dept(deptService.findByDeptIdx(reqDto.getDeptIdx()))
                        .build();
        Story savedStory = storyRepository.save(story);
        List<String> imgURLs = null;
        if (imgs != null) {
            String directory = "story/" + savedStory.getStoryIdx();
            imgURLs = imageUtils.createImages(directory, imgs);
            savedStory.postImages(imgURLs.toString());
        }

        // 거래 저장
        transactionDetailService.saveTransactionDetails(reqDto.getTransactionList(), savedStory);

        return makeStoryResDto(
                savedStory.getStoryIdx(),
                reqDto.getStoryTitle(),
                0L,
                0L,
                reqDto.getStoryContent(),
                imgURLs,
                storyCommentService.getStoryComment(savedStory.getStoryIdx()),
                transactionService.getTransactionsByStory(savedStory.getStoryIdx()),
                story.getCreatedAt());
    }

    @Transactional
    public StoryReadResDto updateStory(
            Long storyIdx, StoryUpdateReqDto reqDto, List<MultipartFile> imgs) {
        Story story = findByStoryIdx(storyIdx);
        story.update(reqDto);

        String directory = "story/" + storyIdx;
        imageUtils.deleteImagesByDirectory(directory);

        List<String> imgURLList =
                (imgs == null || imgs.isEmpty()) ? null : imageUtils.createImages(directory, imgs);
        String imgURLs = imgURLList == null ? null : imgURLList.toString();
        story.postImages(imgURLs);

        // 거래 삭제 및 저장
        transactionDetailService.deleteTransactionDetailsByStory(storyIdx);
        transactionDetailService.saveTransactionDetails(reqDto.getTransactionList(), story);

        return makeStoryResDto(
                storyIdx,
                reqDto.getStoryTitle(),
                storyLikeService.getStoryLikeNum(storyIdx),
                storyCommentService.getStoryCommentNum(storyIdx),
                reqDto.getStoryContent(),
                imgURLList,
                storyCommentService.getStoryComment(storyIdx),
                transactionService.getTransactionsByStory(storyIdx),
                story.getCreatedAt());
    }

    @Transactional
    public StoryDeleteResDto deleteStory(Long storyIdx) {
        // 스토리 삭제
        Story story = findByStoryIdx(storyIdx);
        story.delete();
        // 거래내역 삭제
        transactionDetailService.deleteTransactionDetailsByStory(storyIdx);
        return StoryDeleteResDto.builder()
                .data(StoryDeleteResDto.Data.builder().storyIdx(storyIdx).build())
                .build();
    }

    public Story findByStoryIdx(Long storyIdx) {
        return storyRepository
                .findById(storyIdx)
                .orElseThrow(() -> new RuntimeException("스토리를 찾을 수 없습니다."));
    }

    private StoryReadResDto makeStoryResDto(
            Long storyIdx,
            String storyTitle,
            Long storyLikeNum,
            Long storyCommentNum,
            String storyContent,
            List<String> storyImageList,
            StoryRepresentativeCommentResDto storyComment,
            List<DeptAccountTransactionResDto> transactionList,
            LocalDateTime createdAt) {
        return StoryReadResDto.builder()
                .data(
                        StoryReadResDto.Data.builder()
                                .storyIdx(storyIdx)
                                .storyTitle(storyTitle)
                                .storyContent(storyContent)
                                .storyLikeNum(storyLikeNum)
                                .storyCommentNum(storyCommentNum)
                                .storyComment(storyComment)
                                .storyImageList(storyImageList)
                                .transactionList(transactionList)
                                .createdAt(createdAt)
                                .build())
                .build();
    }

    public StoryService(
            StoryRepository storyRepository,
            StoryLikeService storyLikeService,
            StoryCommentService storyCommentService,
            TransactionService transactionService,
            TransactionDetailService transactionDetailService,
            DeptService deptService,
            ImageUtils imageUtils) {
        this.storyRepository = storyRepository;
        this.storyCommentService = storyCommentService;
        this.storyLikeService = storyLikeService;
        this.transactionService = transactionService;
        this.transactionDetailService = transactionDetailService;
        this.deptService = deptService;
        this.imageUtils = imageUtils;
    }
}
