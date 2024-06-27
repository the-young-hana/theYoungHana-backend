package hana.story.service;

import hana.account.dto.DeptAccountTransactionResDto;
import hana.account.service.TransactionService;
import hana.common.annotation.TypeInfo;
import hana.story.domain.Story;
import hana.story.domain.StoryRepository;
import hana.story.dto.StoriesReadResDto;

import java.util.ArrayList;
import java.util.List;
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
        Page<Story> stories = storyRepository.findByDept_DeptIdx(deptIdx, PageRequest.of(page, 10 , Sort.by(Sort.Direction.DESC, "createdAt")));

        // 모두 합쳐서 거래내역 생성.
        List<StoriesReadResDto.Data> datas = new ArrayList<>();

        for (Story s : stories) {
            long storyIdx = s.getStoryIdx();
            long storyCommentNum = storyCommentService.getStoryCommentNum(storyIdx);
            String storyComment = storyCommentService.getStoryComment(storyIdx);
            long storyLikeNum = storyLikeService.getStoryLikeNum(storyIdx);
            List<DeptAccountTransactionResDto> transactionList =
                    transactionService.getTransactionsByStory(storyIdx);

            datas.add(
                    StoriesReadResDto.Data.builder()
                            .storyCommentNum(storyCommentNum)
                            .storyIdx(storyIdx)
                            .storyLikeNum(storyLikeNum)
                            .storyTitle(s.getStoryTitle())
                            .storyComment(storyComment)
                            .transactionList(transactionList)
                            .build());
        }

        return StoriesReadResDto.builder().data(datas).build();
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
