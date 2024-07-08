package hana.reward.service;

import hana.college.domain.DeptRepository;
import hana.common.annotation.TypeInfo;
import hana.member.domain.Student;
import hana.member.domain.StudentRepository;
import hana.reward.domain.*;
import hana.reward.dto.*;
import hana.reward.exception.PresentException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.stereotype.Service;

@TypeInfo(name = "RewardService", description = "리워드 서비스")
@Service
public class RewardService {
    private final QuizRepository quizRepository;
    private final StudentRepository studentRepository;
    private final DeptRepository deptRepository;
    private final RewardHistoryRepository rewardHistoryRepository;

    // 퀴즈 보여주기
    public RewardQuestionQuizResDto getRandomQuiz(Long studentIdx) {
        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
        LocalDate date = LocalDate.now(koreaZoneId);

        if (checkQuizHistory(studentIdx, date)) {
            throw new PresentException();
        }

        List<Quiz> getAllQuiz = quizRepository.findAll();
        Long count = quizRepository.count();

        Random random = new Random();
        int randomIndex = random.nextInt(Math.toIntExact(count));

        Quiz randomQuiz = getAllQuiz.get(randomIndex);

        RewardQuestionQuizResDto.Data data =
                RewardQuestionQuizResDto.Data.builder()
                        .quizIdx(randomQuiz.getQuizIdx())
                        .quizContent(randomQuiz.getQuizContent())
                        .build();

        return RewardQuestionQuizResDto.builder().data(data).build();
    }

    // 퀴즈 참여
    public RewardAnswerQuizResDto isCollect(
            Long studentIdx, RewardAnswerQuizReqDto rewardAnswerQuizReqDto) {
        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
        LocalDate date = LocalDate.now(koreaZoneId);

        if (checkQuizHistory(studentIdx, date)) {
            throw new PresentException();
        }

        Quiz findQuiz =
                quizRepository
                        .findById(rewardAnswerQuizReqDto.getQuizIdx())
                        .orElseThrow(IllegalArgumentException::new);

        Optional<Student> findStudent = studentRepository.findById(studentIdx);

        if (rewardAnswerQuizReqDto.getAnswer().equals(findQuiz.getQuizAnswer())) {
            // 정답일 때
            findStudent.ifPresent(
                    student -> {
                        // 학과 포인트 증가
                        deptRepository.updatePointByDeptIdx(student.getDept().getDeptIdx(), 5L);

                        // 퀴즈 참가 이력 저장
                        RewardHistory history =
                                RewardHistory.builder()
                                        .rewardCategory(RewardHistoryCategoryEnumType.퀴즈)
                                        .student(student)
                                        .build();
                        rewardHistoryRepository.save(history);
                    });

            RewardAnswerQuizResDto.Data data =
                    RewardAnswerQuizResDto.Data.builder()
                            .isCorrect(true)
                            .explanation(findQuiz.getQuizExplanation())
                            .point(5)
                            .build();
            return RewardAnswerQuizResDto.builder().data(data).build();
        } else {
            // 틀렸을 때
            findStudent.ifPresent(
                    student -> {
                        RewardHistory history =
                                RewardHistory.builder()
                                        .rewardCategory(RewardHistoryCategoryEnumType.퀴즈)
                                        .student(student)
                                        .build();
                        rewardHistoryRepository.save(history);
                    });
            RewardAnswerQuizResDto.Data data =
                    RewardAnswerQuizResDto.Data.builder()
                            .isCorrect(false)
                            .explanation(findQuiz.getQuizExplanation())
                            .point(0)
                            .build();
            return RewardAnswerQuizResDto.builder().data(data).build();
        }
    }

    // 선물
    public RewardPresentResDto getPoints(Long studentIdx) {
        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
        LocalDate date = LocalDate.now(koreaZoneId);

        Long points = (long) (Math.random() * 10 + 10);

        if (checkPresentHistory(studentIdx, date)) {
            throw new PresentException();
        }

        Optional<Student> findStudent = studentRepository.findById(studentIdx);
        findStudent.ifPresent(
                student -> {
                    deptRepository.updatePointByDeptIdx(student.getDept().getDeptIdx(), points);
                    studentRepository.updatePointByStudentIdx(student.getStudentIdx(), points);

                    RewardHistory history =
                            RewardHistory.builder()
                                    .rewardCategory(RewardHistoryCategoryEnumType.선물)
                                    .student(student)
                                    .build();
                    rewardHistoryRepository.save(history);
                });

        return RewardPresentResDto.builder()
                .data(RewardPresentResDto.Data.builder().point(points).build())
                .build();
    }

    // 리워드 내 기여도
    public RewardReadResDto getMyContribution(Long studentIdx, Long deptIdx) {
        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
        LocalDate date = LocalDate.now(koreaZoneId);

        Long findMyDeptPoints = deptRepository.findDeptPointByDeptIdx(deptIdx);
        Long findMyPoints = studentRepository.findStudentPointByStudentIdx(studentIdx);

        return RewardReadResDto.builder()
                .data(
                        RewardReadResDto.Data.builder()
                                .deptPoint(findMyDeptPoints)
                                .myPoint(findMyPoints)
                                .hasParticipatedInQuiz(checkQuizHistory(studentIdx, date))
                                .hasParticipatedInPresent(checkPresentHistory(studentIdx, date))
                                .build())
                .build();
    }

    public RewardReadDeptRankResDto getDeptRank(int page) {
        List<Object[]> rankings = deptRepository.getRanking((page - 1) * 10);
        List<RewardReadDeptRankResDto.Data> data =
                rankings.stream()
                        .map(
                                obj -> {
                                    return RewardReadDeptRankResDto.Data.builder()
                                            .rankIdx((Long) obj[0])
                                            .deptIdx((Long) obj[1])
                                            .deptName((String) obj[2])
                                            .deptReward((Long) obj[3])
                                            .build();
                                })
                        .collect(Collectors.toList());

        return RewardReadDeptRankResDto.builder().data(data).build();
    }

    // 참여 내역 조회
    private boolean checkQuizHistory(Long studentIdx, LocalDate date) {
        List<RewardHistory> findRewardHistories =
                rewardHistoryRepository.findHistoryByDate(
                        studentIdx, date.atStartOfDay(), date.atTime(LocalTime.MAX));

        return findRewardHistories.stream()
                .anyMatch(
                        history -> history.getRewardCategory() == RewardHistoryCategoryEnumType.퀴즈);
    }

    private boolean checkPresentHistory(Long studentIdx, LocalDate date) {

        List<RewardHistory> findRewardHistories =
                rewardHistoryRepository.findHistoryByDate(
                        studentIdx, date.atStartOfDay(), date.atTime(LocalTime.MAX));
        System.out.println("~~~date " + date);
        System.out.println("~~~size " + findRewardHistories.size());
        //        System.out.println("~~~createdAt " + findRewardHistories.get(0).getCreatedAt());
        System.out.println("~~~");
        List<RewardHistory> testList = rewardHistoryRepository.testList(studentIdx);
        for (RewardHistory rewardHistory : testList) {
            System.out.println("~~~" + rewardHistory.getCreatedAt());
        }
        return findRewardHistories.stream()
                .anyMatch(
                        history -> history.getRewardCategory() == RewardHistoryCategoryEnumType.선물);
    }

    @Builder
    public RewardService(
            QuizRepository quizRepository,
            StudentRepository studentRepository,
            DeptRepository deptRepository,
            RewardHistoryRepository rewardHistoryRepository) {
        this.quizRepository = quizRepository;
        this.studentRepository = studentRepository;
        this.deptRepository = deptRepository;
        this.rewardHistoryRepository = rewardHistoryRepository;
    }
}
