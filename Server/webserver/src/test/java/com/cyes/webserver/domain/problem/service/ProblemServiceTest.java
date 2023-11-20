package com.cyes.webserver.domain.problem.service;

import com.cyes.webserver.domain.problem.dto.request.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import com.cyes.webserver.domain.problem.repository.ProblemRepository;
import com.cyes.webserver.exception.CustomException;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

@SpringBootTest
class ProblemServiceTest {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        problemRepository.deleteAll();
    }

    @Test
    @DisplayName("객관식 문제를 저장할 수 있다.")
    void saveMultipleChoice() {
        // given
        String question = "question";
        String answer = "answer";
        String[] choices = {"choice1", "choice2", "choice3", "choice4"};
        String description = "description";
        ProblemCategory category = ProblemCategory.DB;
        MultipleChoiceProblemSaveRequest request = MultipleChoiceProblemSaveRequest.builder().question(question).choices(choices).answer(answer).description(description).problemCategory(category).build();

        // when
        ProblemResponse problemResponse = problemService.saveMultipleChoice(request);

        // then
        assertThat(problemResponse).extracting("id").isNotNull();
        assertThat(problemResponse).extracting("question", "choices", "problemOrder", "answer", "description", "category", "type")
                .contains(question, choices, 0, answer, description, category, ProblemType.MULTIPLECHOICE);
    }

    @Test
    @DisplayName("단답형 문제를 저장할 수 있다.")
    void saveShortAnswer() {
        // given
        String question = "question";
        String answer = "answer";
        String description = "description";
        ProblemCategory category = ProblemCategory.DB;
        ShortAnswerProblemSaveRequest request = ShortAnswerProblemSaveRequest.builder().question(question).answer(answer).description(description).problemCategory(category).build();

        // when
        ProblemResponse problemResponse = problemService.saveShortAnswer(request);
        // then
        assertThat(problemResponse).extracting("id").isNotNull();
        assertThat(problemResponse).extracting("question", "problemOrder", "answer", "description", "category", "type")
                .contains(question, 0, answer, description, category, ProblemType.SHORTANSWER);
    }
    @Test
    @DisplayName("참거짓 문제를 저장할 수 있다.")
    void saveTrueOrFalse() {
        // given
        String question = "question";
        String answer = "answer";
        String description = "description";
        ProblemCategory category = ProblemCategory.DB;
        TrueOrFalseProblemSaveRequest request = TrueOrFalseProblemSaveRequest.builder().question(question).answer(answer).description(description).problemCategory(category).build();

        // when
        ProblemResponse problemResponse = problemService.saveTrueOrFalse(request);
        // then
        assertThat(problemResponse).extracting("id").isNotNull();
        assertThat(problemResponse).extracting("question", "problemOrder", "answer", "description", "category", "type")
                .contains(question, 0, answer, description, category, ProblemType.TRUEORFALSE);
    }
    @Test
    @DisplayName("모든 문제를 페이지로 조회한다.")
    void findAllProblem() {
        // given
        insertProblemContent();
        PageRequest pageRequest1 = PageRequest.of(0, 2);
        PageRequest pageRequest2 = PageRequest.of(1, 2);
        PageRequest pageRequest3 = PageRequest.of(2, 2);
        PageRequest pageRequest4 = PageRequest.of(3, 2);
        PageRequest pageRequest5 = PageRequest.of(4, 2);

        // when
        Page<ProblemResponse> allProblem1 = problemService.findAllProblem(pageRequest1);
        Page<ProblemResponse> allProblem2 = problemService.findAllProblem(pageRequest2);
        Page<ProblemResponse> allProblem3 = problemService.findAllProblem(pageRequest3);
        Page<ProblemResponse> allProblem4 = problemService.findAllProblem(pageRequest4);
        Page<ProblemResponse> allProblem5 = problemService.findAllProblem(pageRequest5);

        // then
        assertThat(allProblem1.getContent()).hasSize(2);
        assertThat(allProblem2.getContent()).hasSize(2);
        assertThat(allProblem3.getContent()).hasSize(2);
        assertThat(allProblem4.getContent()).hasSize(2);
        assertThat(allProblem5.getContent()).hasSize(2);
    }
    @Test
    @DisplayName("category, type, pageable을 이용해서 문제를 조회할 수 있다.")
    void findProblemByCategoryOrType() {
        // given
        insertProblemContent();
        ProblemCategory problemCategory = ProblemCategory.OS;
        ProblemType problemType = ProblemType.TRUEORFALSE;
        PageRequest pageRequest1 = PageRequest.of(0, 2);
        // when
        Page<ProblemResponse> problemByCategoryOrType = problemService.findProblemByCategoryOrType(problemCategory, problemType, pageRequest1);
        // then
        assertThat(problemByCategoryOrType.getContent()).hasSize(2);
        assertThat(problemByCategoryOrType.getContent()).extracting(
                problem -> problem.getQuestion()
                , problem -> problem.getAnswer()
                , problem -> problem.getProblemOrder()
                , problem -> problem.getDescription()
                , problem -> problem.getCategory()
                , problem -> problem.getType())
                .containsExactlyInAnyOrder(
                Tuple.tuple("question11", "answer11", 1, "description11", ProblemCategory.OS, ProblemType.TRUEORFALSE)
                ,Tuple.tuple("question12", "answer12", 2, "description12", ProblemCategory.OS, ProblemType.TRUEORFALSE));
    }
    @Test
    @DisplayName("category로 문제를 조회한다.")
    void findProblemByCategory() {
        // given
        insertProblemContent();
        ProblemCategory problemCategory = ProblemCategory.OS;
        PageRequest pageRequest1 = PageRequest.of(0, 2);
        // when
        Page<ProblemResponse> problemByCategoryOrType = problemService.findProblemByCategoryOrType(problemCategory, null, pageRequest1);
        // then
        assertThat(problemByCategoryOrType.getContent()).hasSize(2);
        assertThat(problemByCategoryOrType.getContent()).extracting(
                        problem -> problem.getQuestion()
                        , problem -> problem.getAnswer()
                        , problem -> problem.getProblemOrder()
                        , problem -> problem.getDescription()
                        , problem -> problem.getCategory()
                        , problem -> problem.getType())
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question11", "answer11", 1, "description11", ProblemCategory.OS, ProblemType.TRUEORFALSE)
                        ,Tuple.tuple("question12", "answer12", 2, "description12", ProblemCategory.OS, ProblemType.TRUEORFALSE));
    }
    @Test
    @DisplayName("type으로 문제를 조회한다.")
    void findProblemByType() {
        // given
        insertProblemContent();
        ProblemType problemType = ProblemType.TRUEORFALSE;
        PageRequest pageRequest1 = PageRequest.of(0, 2);
        // when
        Page<ProblemResponse> problemByCategoryOrType = problemService.findProblemByCategoryOrType(null, problemType, pageRequest1);
        // then
        assertThat(problemByCategoryOrType.getContent()).hasSize(2);
        assertThat(problemByCategoryOrType.getContent()).extracting(
                        problem -> problem.getQuestion()
                        , problem -> problem.getAnswer()
                        , problem -> problem.getProblemOrder()
                        , problem -> problem.getDescription()
                        , problem -> problem.getCategory()
                        , problem -> problem.getType())
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question11", "answer11", 1, "description11", ProblemCategory.OS, ProblemType.TRUEORFALSE)
                        ,Tuple.tuple("question12", "answer12", 2, "description12", ProblemCategory.OS, ProblemType.TRUEORFALSE));
    }
    @Test
    @DisplayName("선택 조건이 없다면 예외가 발생한다.")
    void findProblemByCategoryOrTypeWithOutOption() {
        // given// when// then
        assertThatThrownBy(() -> problemService.findProblemByCategoryOrType(null, null, null))
                .isInstanceOf(CustomException.class)
                .hasMessage("카테고리, 타입중 적어도 하나가 필요합니다.");
    }
    @Test
    @DisplayName("문제 Id List로 문제를 조회할 수 있다.")
    void findAllProblemByQuiz() {
        // given
        MultipleChoiceProblemSaveRequest multipleChoice1 = MultipleChoiceProblemSaveRequest.builder().question("question1").answer("answer1").choices(new String[]{"choice1", "choice2", "choice3", "choice4"}).description("description1").problemCategory(ProblemCategory.DB).build();
        Problem problem1 = problemRepository.save(Problem.createMultipleChoice(multipleChoice1));
        MultipleChoiceProblemSaveRequest multipleChoice2 = MultipleChoiceProblemSaveRequest.builder().question("question2").answer("answer2").choices(new String[]{"choice1", "choice2", "choice3", "choice4"}).description("description2").problemCategory(ProblemCategory.DB).build();
        Problem problem2 = problemRepository.save(Problem.createMultipleChoice(multipleChoice2));
        MultipleChoiceProblemSaveRequest multipleChoice3 = MultipleChoiceProblemSaveRequest.builder().question("question3").answer("answer3").choices(new String[]{"choice1", "choice2", "choice3", "choice4"}).description("description3").problemCategory(ProblemCategory.DB).build();
        Problem problem3 = problemRepository.save(Problem.createMultipleChoice(multipleChoice3));


        List<String> idList = List.of(problem1.getId(),problem2.getId(),problem3.getId());
        // when
        List<ProblemResponse> allProblemByQuiz = problemService.findAllProblemByQuiz(idList);

        // then

        assertThat(allProblemByQuiz).hasSize(3)
                .extracting("id", "question", "choices", "answer", "category", "type")
                .containsExactly(
                        Tuple.tuple(problem1.getId(), problem1.getQuestion(), problem1.getChoices(), problem1.getAnswer(), problem1.getCategory(), problem1.getType())
                        , Tuple.tuple(problem2.getId(), problem2.getQuestion(), problem2.getChoices(), problem2.getAnswer(), problem2.getCategory(), problem2.getType())
                        , Tuple.tuple(problem3.getId(), problem3.getQuestion(), problem3.getChoices(), problem3.getAnswer(), problem3.getCategory(), problem3.getType())
                );

    }
    @Test
    @DisplayName("problemId로 문제를 삭제한다.")
    void deleteProblem() {
        // given
        MultipleChoiceProblemSaveRequest multipleChoice1 = MultipleChoiceProblemSaveRequest.builder().question("question1").answer("answer1").choices(new String[]{"choice1", "choice2", "choice3", "choice4"}).description("description1").problemCategory(ProblemCategory.DB).build();
        Problem problem1 = problemRepository.save(Problem.createMultipleChoice(multipleChoice1));
        MultipleChoiceProblemSaveRequest multipleChoice2 = MultipleChoiceProblemSaveRequest.builder().question("question1").answer("answer1").choices(new String[]{"choice1", "choice2", "choice3", "choice4"}).description("description1").problemCategory(ProblemCategory.DB).build();
        Problem problem2 = problemRepository.save(Problem.createMultipleChoice(multipleChoice2));

        // when
        problemService.deleteProblem(problem1.getId());
        problemService.deleteProblem(problem2.getId());
        // then
        assertThat(problemRepository.findAll()).hasSize(0);
    }

    private void insertProblemContent(){
        for (int i = 1; i <=10 ; i++) {
            MultipleChoiceProblemSaveRequest multipleChoice = MultipleChoiceProblemSaveRequest.builder()
                    .question("question" + i)
                    .answer("answer" + i)
                    .choices(new String[]{"choice1", "choice2", "choice3", "choice4"})
                    .description("description"+i)
                    .problemCategory(ProblemCategory.DB)
                    .build();

            problemRepository.save(Problem.createMultipleChoice(multipleChoice));

        }
        for (int i = 11; i <= 20; i++) {
            TrueOrFalseProblemSaveRequest trueOrFalseProblemSaveRequest = TrueOrFalseProblemSaveRequest.builder()
                    .question("question" + i)
                    .answer("answer" + i)
                    .description("description" + i)
                    .problemCategory(ProblemCategory.OS)
                    .build();

            problemRepository.save(Problem.createTrueOrFalse(trueOrFalseProblemSaveRequest));

        }
        for (int i = 21; i <= 30; i++) {
            ShortAnswerProblemSaveRequest shortAnswerProblemSaveRequest = ShortAnswerProblemSaveRequest.builder()
                    .question("question" + i)
                    .answer("answer" + i)
                    .description("description" + i)
                    .problemCategory(ProblemCategory.NETWORK)
                    .build();

            problemRepository.save(Problem.createShortAnswer(shortAnswerProblemSaveRequest));

        }
    }
}