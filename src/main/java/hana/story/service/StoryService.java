package hana.story.service;

import hana.account.dto.DeptAccountTransactionResDto;
import hana.account.service.TransactionService;
import hana.common.annotation.TypeInfo;
import hana.story.domain.Story;
import hana.story.domain.StoryRepository;
import hana.story.dto.StoriesReadResDto;
import hana.story.dto.StoryReadResDto;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@TypeInfo(name = "StoryService", description = "스토리 서비스")
@Service
public class StoryService {
    private final StoryRepository storyRepository;
    private final StoryCommentService storyCommentService;
    private final StoryLikeService storyLikeService;
    private final TransactionService transactionService;

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

    public StoryService(
            StoryRepository storyRepository,
            StoryLikeService storyLikeService,
            StoryCommentService storyCommentService,
            TransactionService transactionService) {
        this.storyRepository = storyRepository;
        this.storyCommentService = storyCommentService;
        this.storyLikeService = storyLikeService;
        this.transactionService = transactionService;
    }
}
