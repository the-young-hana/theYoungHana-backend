package hana.story.service;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hana.common.utils.JwtUtils;
import hana.member.domain.Student;
import hana.member.domain.StudentRepository;
import hana.story.domain.*;
import hana.story.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoryCommentService {
    private final StoryCommentRepository storyCommentRepository;
    private final StoryRepository storyRepository;
    private final StudentRepository studentRepository;
    private final JPAQueryFactory queryFactory;
    private final JwtUtils jwtUtils;

    public StoryCommentService(
            StoryCommentRepository storyCommentRepository,
            StoryRepository storyRepository,
            StudentRepository studentRepository,
            JPAQueryFactory queryFactory,
            JwtUtils jwtUtils) {
        this.storyCommentRepository = storyCommentRepository;
        this.storyRepository = storyRepository;
        this.studentRepository = studentRepository;
        this.queryFactory = queryFactory;
        this.jwtUtils = jwtUtils;
    }

    public StoryRepresentativeCommentResDto getStoryComment(Long storyIdx) {
        Optional<StoryComment> storyComment =
                storyCommentRepository.findFirstByStory_StoryIdxAndDeletedYnFalse(storyIdx);
        if (storyComment.isPresent()) {
            StoryComment sc = storyComment.get();
            return StoryRepresentativeCommentResDto.builder()
                    .commentIdx(sc.getStoryCommentIdx())
                    .commentContent(sc.getStoryCommentContent())
                    .createdAt(sc.getCreatedAt())
                    .build();
        }
        return null;
    }

    public StoryReadCommentsResDto readStoryComments(Long storyIdx, Long lastCommentIdx) {
        List<StoryCommentPaginationDto> commentsPage =
                paginationNoOffset(storyIdx, lastCommentIdx, 10);

        List<StoryReadCommentsResDto.Data> commentDataList =
                commentsPage.stream()
                        .map(
                                comment -> {
                                    List<StoryReadCommentsResDto.Data.Reply> replies =
                                            storyCommentRepository
                                                    .findAllByStoryCommentParent_StoryCommentIdx(
                                                            comment.getCommentIdx())
                                                    .stream()
                                                    .map(
                                                            reply ->
                                                                    StoryReadCommentsResDto.Data
                                                                            .Reply.builder()
                                                                            .commentIdx(
                                                                                    reply
                                                                                            .getStoryCommentIdx())
                                                                            .commentParentIdx(
                                                                                    reply
                                                                                                            .getStoryCommentParent()
                                                                                                    != null
                                                                                            ? reply.getStoryCommentParent()
                                                                                                    .getStoryCommentIdx()
                                                                                            : null)
                                                                            .commentContent(
                                                                                    reply
                                                                                                    .isDeletedYn()
                                                                                            ? "삭제된 댓글입니다"
                                                                                            : reply
                                                                                                    .getStoryCommentContent())
                                                                            .studentNickname(
                                                                                    getStudentNicknameByMemberId(
                                                                                            reply.getCreatedBy()
                                                                                                    .getMemberIdx()))
                                                                            .createdAt(
                                                                                    reply
                                                                                            .getCreatedAt())
                                                                            .createdBy(
                                                                                    reply.getCreatedBy()
                                                                                            .getMemberIdx())
                                                                            .build())
                                                    .sorted(
                                                            (r1, r2) ->
                                                                    r1.getCommentIdx()
                                                                            .compareTo(
                                                                                    r2
                                                                                            .getCommentIdx()))
                                                    .collect(
                                                            Collectors.toCollection(
                                                                    ArrayList::new));

                                    return StoryReadCommentsResDto.Data.builder()
                                            .commentIdx(comment.getCommentIdx())
                                            .commentContent(
                                                    comment.isDeletedYn()
                                                            ? "삭제된 댓글입니다"
                                                            : comment.getCommentContent())
                                            .replyList(replies)
                                            .replyList(replies)
                                            .studentNickname(
                                                    getStudentNicknameByMemberId(
                                                            comment.getCreatedBy()))
                                            .createdAt(comment.getCreatedAt())
                                            .createdBy(comment.getCreatedBy())
                                            .build();
                                })
                        .sorted((c1, c2) -> c1.getCommentIdx().compareTo(c2.getCommentIdx()))
                        .collect(Collectors.toCollection(ArrayList::new));

        return StoryReadCommentsResDto.builder().data(commentDataList).build();
    }

    private List<StoryCommentPaginationDto> paginationNoOffset(
            Long storyIdx, Long lastCommentIdx, int pageSize) {
        QStoryComment storyComment = QStoryComment.storyComment;
        return queryFactory
                .select(
                        Projections.constructor(
                                StoryCommentPaginationDto.class,
                                storyComment.storyCommentIdx,
                                storyComment.storyCommentContent,
                                storyComment.createdAt,
                                storyComment.createdBy.memberIdx,
                                storyComment.deletedYn))
                .from(storyComment)
                .where(
                        gtCommentId(lastCommentIdx),
                        storyComment.story.storyIdx.eq(storyIdx),
                        storyComment.storyCommentParent.isNull())
                .orderBy(storyComment.storyCommentIdx.asc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression gtCommentId(Long commentId) {
        if (commentId == null) {
            return null;
        }
        return QStoryComment.storyComment.storyCommentIdx.gt(commentId);
    }

    private String getStudentNicknameByMemberId(Long memberId) {
        Student student = studentRepository.findByMember_MemberIdx(memberId);
        return student != null ? student.getStudentNickname() : "Unknown";
    }

    @Transactional
    public StoryCreateCommentResDto createStoryComment(
            Long storyIdx, StoryCreateCommentReqDto storyCreateCommentReqDto) {

        Story story =
                storyRepository
                        .findById(storyIdx)
                        .orElseThrow(() -> new RuntimeException("Story not found"));

        StoryComment parentComment = null;
        if (storyCreateCommentReqDto.getCommentParentIdx() != null) {
            parentComment =
                    storyCommentRepository
                            .findById(storyCreateCommentReqDto.getCommentParentIdx())
                            .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        }

        StoryComment storyComment =
                StoryComment.builder()
                        .story(story)
                        .storyCommentParent(parentComment)
                        .storyCommentContent(storyCreateCommentReqDto.getCommentContent())
                        .build();

        storyCommentRepository.save(storyComment);
        story.incrementCommentAmount();
        return StoryCreateCommentResDto.builder()
                .data(
                        StoryCreateCommentResDto.Data.builder()
                                .commentIdx(storyComment.getStoryCommentIdx())
                                .commentParentIdx(
                                        storyComment.getStoryCommentParent() != null
                                                ? storyComment
                                                        .getStoryCommentParent()
                                                        .getStoryCommentIdx()
                                                : null)
                                .commentContent(storyComment.getStoryCommentContent())
                                .build())
                .build();
    }

    @Transactional
    public StoryUpdateCommentResDto updateStoryComment(
            Long storyIdx, Long commentIdx, StoryUpdateCommentReqDto storyUpdateCommentReqDto) {
        StoryComment storyComment =
                storyCommentRepository
                        .findById(commentIdx)
                        .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!storyComment.getStory().getStoryIdx().equals(storyIdx)) {
            throw new RuntimeException("Story ID mismatch");
        }

        storyComment.update(storyUpdateCommentReqDto.getCommentContent());
        storyCommentRepository.save(storyComment);

        return StoryUpdateCommentResDto.builder()
                .data(
                        StoryUpdateCommentResDto.Data.builder()
                                .commentIdx(storyComment.getStoryCommentIdx())
                                .commentParentIdx(
                                        storyComment.getStoryCommentParent() != null
                                                ? storyComment
                                                        .getStoryCommentParent()
                                                        .getStoryCommentIdx()
                                                : null)
                                .commentContent(storyComment.getStoryCommentContent())
                                .build())
                .build();
    }

    @Transactional
    public void deleteStoryComment(Long storyIdx, Long commentIdx) {
        StoryComment storyComment =
                storyCommentRepository
                        .findById(commentIdx)
                        .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!storyComment.getStory().getStoryIdx().equals(storyIdx)) {
            throw new RuntimeException("Story ID mismatch");
        }

        if (!storyComment.isDeletedYn()) {
            storyComment.delete();
            storyCommentRepository.save(storyComment);

            storyRepository
                    .findById(storyIdx)
                    .orElseThrow(() -> new RuntimeException("Story not found"))
                    .decrementCommentAmount();
        }
    }
}
