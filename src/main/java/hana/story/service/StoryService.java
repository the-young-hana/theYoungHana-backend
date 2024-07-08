package hana.story.service;

import hana.account.domain.TransactionTypeEnumType;
import hana.account.dto.DeptAccountTransactionResDto;
import hana.account.service.TransactionService;
import hana.college.service.DeptService;
import hana.common.annotation.TypeInfo;
import hana.common.utils.ImageUtils;
import hana.common.utils.JwtUtils;
import hana.member.domain.Student;
import hana.member.service.StudentService;
import hana.story.domain.Story;
import hana.story.domain.StoryRepository;
import hana.story.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final StudentService studentService;
    private final ImageUtils imageUtils;
    private final JwtUtils jwtUtils;

    @Transactional(readOnly = true)
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
            boolean isLiked = storyLikeService.checkLike(s, jwtUtils.getStudent());
            List<DeptAccountTransactionResDto> transactionList =
                    transactionService.getTransactionsByStory(storyIdx);
            datas.add(
                    StoriesReadResDto.Data.builder()
                            .storyCommentNum(s.getStoryCommentAmount())
                            .storyIdx(storyIdx)
                            .storyLikeNum(s.getStoryLikeAmount())
                            .storyTitle(s.getStoryTitle())
                            .isLiked(isLiked)
                            .transactionList(transactionList)
                            .startDate(transactionList.get(0).getTransactionDate().toLocalDate())
                            .endDate(
                                    transactionList
                                            .get(transactionList.size() - 1)
                                            .getTransactionDate()
                                            .toLocalDate())
                            .totalAmount(
                                    transactionList.stream()
                                            .filter(
                                                    x ->
                                                            x.getTransactionType()
                                                                    .equals(
                                                                            TransactionTypeEnumType
                                                                                    .출금))
                                            .mapToLong(
                                                    DeptAccountTransactionResDto
                                                            ::getTransactionAmount)
                                            .sum())
                            .createdAt(s.getCreatedAt())
                            .build());
        }

        return StoriesReadResDto.builder().data(datas).build();
    }

    @Transactional(readOnly = true)
    public StoryReadResDto getStory(Long storyIdx) {
        Story story = findByStoryIdx(storyIdx);
        return makeStoryResDto(
                story,
                story.getStoryImageList() == null ? null : makeImgList(story.getStoryImageList()));
    }

    @Transactional
    public StoryReadResDto createStory(StoryCreateReqDto reqDto, List<MultipartFile> imgs) {

        studentService.checkIsAdmin();

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

        return makeStoryResDto(story, imgURLs);
    }

    @Transactional
    public StoryReadResDto updateStory(Long storyIdx, StoryUpdateReqDto reqDto) {
        studentService.checkIsAdmin();
        Story story = findByStoryIdx(storyIdx);
        story.update(reqDto);

        // 거래 삭제 및 저장
        transactionDetailService.deleteTransactionDetailsByStory(storyIdx);
        transactionDetailService.saveTransactionDetails(reqDto.getTransactionList(), story);

        return makeStoryResDto(
                story,
                story.getStoryImageList() == null ? null : makeImgList(story.getStoryImageList()));
    }

    @Transactional
    public StoryDeleteResDto deleteStory(Long storyIdx) {
        studentService.checkIsAdmin();
        // 스토리 삭제
        Story story = findByStoryIdx(storyIdx);
        story.delete();
        // 거래내역 삭제
        transactionDetailService.deleteTransactionDetailsByStory(storyIdx);
        return StoryDeleteResDto.builder()
                .data(StoryDeleteResDto.Data.builder().storyIdx(storyIdx).build())
                .build();
    }

    @Transactional
    public StoryReadResDto toggleStoryLike(Long storyIdx, Student student) {
        Story story = findByStoryIdx(storyIdx);
        if (storyLikeService.checkLike(story, student)) {
            storyLikeService.deleteLike(story, student);
            story.decrementLikeAmount();
        } else {
            storyLikeService.createLike(story, student);
            story.incrementLikeAmount();
        }

        return getStory(storyIdx);
    }

    private List<String> makeImgList(String storyImageList) {
        Matcher matcher = Pattern.compile("https?://[^,\\s]+\\.(png|jpg)").matcher(storyImageList);

        List<String> imageList = new ArrayList<>();

        while (matcher.find()) {
            imageList.add(matcher.group());
        }
        return imageList;
    }

    public Story findByStoryIdx(Long storyIdx) {
        return storyRepository
                .findById(storyIdx)
                .orElseThrow(() -> new IllegalArgumentException("스토리를 찾을 수 없습니다."));
    }

    private StoryReadResDto makeStoryResDto(Story story, List<String> storyImageList) {
        Long storyIdx = story.getStoryIdx();

        return StoryReadResDto.builder()
                .data(
                        StoryReadResDto.Data.builder()
                                .storyIdx(storyIdx)
                                .storyTitle(story.getStoryTitle())
                                .storyContent(story.getStoryContent())
                                .isLiked(storyLikeService.checkLike(story, jwtUtils.getStudent()))
                                .storyLikeNum(story.getStoryLikeAmount())
                                .storyCommentNum(story.getStoryCommentAmount())
                                .storyComment(storyCommentService.getStoryComment(storyIdx))
                                .storyImageList(storyImageList)
                                .transactionList(
                                        transactionService.getTransactionsForStoryDetail(storyIdx))
                                .createdAt(story.getCreatedAt())
                                .build())
                .build();
    }

    public StoryService(
            StoryRepository storyRepository,
            JwtUtils jwtUtils,
            StoryLikeService storyLikeService,
            StoryCommentService storyCommentService,
            TransactionService transactionService,
            TransactionDetailService transactionDetailService,
            DeptService deptService,
            StudentService studentService,
            ImageUtils imageUtils) {
        this.storyRepository = storyRepository;
        this.jwtUtils = jwtUtils;
        this.storyCommentService = storyCommentService;
        this.storyLikeService = storyLikeService;
        this.transactionService = transactionService;
        this.transactionDetailService = transactionDetailService;
        this.deptService = deptService;
        this.studentService = studentService;
        this.imageUtils = imageUtils;
    }
}
