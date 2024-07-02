package hana.reward.service;

import hana.college.domain.DeptRepository;
import hana.common.annotation.TypeInfo;
import hana.member.domain.Student;
import hana.member.domain.StudentRepository;
import hana.reward.domain.Quiz;
import hana.reward.domain.QuizRepository;
import hana.reward.dto.*;
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

    public RewardQuestionQuizResDto getRandomQuiz() {
        List<Quiz> getAllQuiz = quizRepository.findAll();
        Long count = quizRepository.count();

        System.out.println(count);

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

    public RewardAnswerQuizResDto isCollect(
            Long studentIdx, RewardAnswerQuizReqDto rewardAnswerQuizReqDto) {
        Quiz findQuiz =
                quizRepository
                        .findById(rewardAnswerQuizReqDto.getQuizIdx())
                        .orElseThrow(IllegalArgumentException::new);

        if (rewardAnswerQuizReqDto.getAnswer() == findQuiz.getQuizAnswer()) {
            // 정답일 때
            // 학과 포인트 증가
            Optional<Student> findStudent = studentRepository.findById(studentIdx);
            findStudent.ifPresent(
                    student ->
                            deptRepository.updatePointByDeptIdx(
                                    student.getDept().getDeptIdx(), 5L));

            RewardAnswerQuizResDto.Data data =
                    RewardAnswerQuizResDto.Data.builder()
                            .isCorrect(true)
                            .explanation(findQuiz.getQuizExplanation())
                            .point(5)
                            .build();
            return RewardAnswerQuizResDto.builder().data(data).build();
        } else {
            // 틀렸을 때
            RewardAnswerQuizResDto.Data data =
                    RewardAnswerQuizResDto.Data.builder()
                            .isCorrect(false)
                            .explanation(findQuiz.getQuizExplanation())
                            .point(0)
                            .build();
            return RewardAnswerQuizResDto.builder().data(data).build();
        }
    }

    public RewardPresentResDto getPoints(Long studentIdx) {
        Long points = (long) (Math.random() * 10 + 10);

        Optional<Student> findStudent = studentRepository.findById(studentIdx);
        findStudent.ifPresent(
                student ->
                        deptRepository.updatePointByDeptIdx(
                                student.getDept().getDeptIdx(), points));

        return RewardPresentResDto.builder()
                .data(RewardPresentResDto.Data.builder().point(points).build())
                .build();
    }

    public RewardReadResDto getMyContribution(Long studentIdx, Long deptIdx) {
        Long findMyDeptPoints = deptRepository.findDeptPointByDeptIdx(deptIdx);
        Long findMyPoints = studentRepository.findStudentPointByStudentIdx(studentIdx);
        return RewardReadResDto.builder()
                .data(
                        RewardReadResDto.Data.builder()
                                .deptPoint(findMyDeptPoints)
                                .myPoint(findMyPoints)
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

    @Builder
    public RewardService(
            QuizRepository quizRepository,
            StudentRepository studentRepository,
            DeptRepository deptRepository) {
        this.quizRepository = quizRepository;
        this.studentRepository = studentRepository;
        this.deptRepository = deptRepository;
    }
}
