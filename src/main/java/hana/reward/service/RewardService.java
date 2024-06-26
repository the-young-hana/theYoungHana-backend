package hana.reward.service;

import hana.college.domain.DeptRepository;
import hana.common.annotation.TypeInfo;
import hana.member.domain.Student;
import hana.member.domain.StudentRepository;
import hana.reward.domain.Quiz;
import hana.reward.domain.QuizRepository;
import hana.reward.dto.RewardAnswerQuizReqDto;
import hana.reward.dto.RewardAnswerQuizResDto;
import hana.reward.dto.RewardQuestionQuizResDto;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
                    student -> deptRepository.updatePointByDeptIdx(student.getDept().getDeptIdx()));

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
