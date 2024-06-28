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
                storyRepository.findByDept_DeptIdx(
                        deptIdx,
                        PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));

        // 모두 합쳐서 거래내역 생성.
        List<StoriesReadResDto.Data> datas = new ArrayList<>();

        for (Story s : stories) {
            long storyIdx = s.getStoryIdx();
            long storyCommentNum = storyCommentService.getStoryCommentNum(storyIdx);
            long storyLikeNum = storyLikeService.getStoryLikeNum(storyIdx);
            List<DeptAccountTransactionResDto> transactionList =
                    transactionService.getTransactionsByStory(storyIdx);

            datas.add(
                    StoriesReadResDto.Data.builder()
                            .storyCommentNum(storyCommentNum)
                            .storyIdx(storyIdx)
                            .storyLikeNum(storyLikeNum)
                            .storyTitle(s.getStoryTitle())
                            .transactionList(transactionList)
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
        Matcher matcher = Pattern.compile("\"([^\"]+)\"").matcher(story.getStoryImageList());

        List<String> imageList = new ArrayList<>();

        while (matcher.find()) {
            imageList.add(matcher.group(1));
        }
        List<DeptAccountTransactionResDto> transactionList =
                transactionService.getTransactionsByStory(storyIdx);
        return StoryReadResDto.builder()
                .data(
                        StoryReadResDto.Data.builder()
                                .storyIdx(story.getStoryIdx())
                                .storyTitle(story.getStoryTitle())
                                .storyContent(story.getStoryContent())
                                .storyLikeNum(storyLikeService.getStoryLikeNum(storyIdx))
                                .storyCommentNum(storyCommentService.getStoryCommentNum(storyIdx))
                                .storyComment(storyCommentService.getStoryComment(storyIdx))
                                .storyImageList(imageList)
                                .transactionList(transactionList)
                                .build())
                .build();
    }

    @Transactional
    public StoryCreateResDto createStory(StoryCreateReqDto reqDto, List<MultipartFile> imgs) {

        Story story =
                Story.builder()
                        .storyTitle(reqDto.getStoryTitle())
                        .storyContent(reqDto.getStoryContent())
                        .dept(deptService.findByDeptIdx(reqDto.getDeptIdx()))
                        .build();
        Story savedStory = storyRepository.save(story);

        if (imgs != null) {
            String directory = "story/" + savedStory.getStoryIdx();
            List<String> imgURLs = imageUtils.createImages(directory, imgs);
            savedStory.postImages(imgURLs.toString());
        }

        // 거래 저장
        transactionDetailService.saveTransactionDetails(reqDto.getTransactionList(), savedStory);

        return StoryCreateResDto.builder()
                .data(
                        StoryCreateResDto.Data.builder()
                                .storyIdx(savedStory.getStoryIdx())
                                .storyTitle(savedStory.getStoryTitle())
                                .storyContent(savedStory.getStoryContent())
                                .storyImageList(savedStory.getStoryImageList())
                                .build())
                .build();
    }

    @Transactional
    public StoryUpdateResDto updateStory(
            Long storyIdx, StoryUpdateReqDto reqDto, List<MultipartFile> imgs) {
        Story story = findByStoryIdx(storyIdx);
        story.update(reqDto);

        String directory = "story/" + storyIdx;
        imageUtils.deleteImagesByDirectory(directory);
        String url =
                (imgs == null || imgs.isEmpty())
                        ? null
                        : imageUtils.createImages(directory, imgs).toString();
        story.postImages(url);

        // 거래 삭제 및 저장
        transactionDetailService.deleteTransactionDetailsByStory(storyIdx);
        transactionDetailService.saveTransactionDetails(reqDto.getTransactionList(), story);

        return StoryUpdateResDto.builder()
                .data(
                        StoryUpdateResDto.Data.builder()
                                .storyIdx(story.getStoryIdx())
                                .storyTitle(story.getStoryTitle())
                                .storyContent(story.getStoryContent())
                                .storyImageList(story.getStoryImageList())
                                .build())
                .build();
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
