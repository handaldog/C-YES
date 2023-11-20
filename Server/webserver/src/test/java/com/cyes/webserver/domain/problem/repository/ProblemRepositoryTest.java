package com.cyes.webserver.domain.problem.repository;


import com.cyes.webserver.domain.problem.dto.request.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.entity.*;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.InstanceOfAssertFactories.type;


//@SpringBootTest
@DataMongoTest
class ProblemRepositoryTest {
    @Autowired
    private ProblemRepository problemRepository;




    @AfterEach
    void tearDown() {
        problemRepository.deleteAll();
    }
    @Test
    @DisplayName("문제를 DB에 저장한다.")
    void save() {
        // given
        String description = "description";
        String answer = "answer";
        String[] choices = {"choice1", "choice2", "choice3", "choice4"};
        String question = "question";
        ProblemCategory category = ProblemCategory.DB;

        MultipleChoiceProblemSaveRequest request = MultipleChoiceProblemSaveRequest.builder().question(question).choices(choices).answer(answer).description(description).problemCategory(category).build();
        Problem multipleChoice = Problem.createMultipleChoice(request);

        // when
        Problem problem = problemRepository.save(multipleChoice);

        // then
        assertThat(problem.getId()).isNotNull();
        assertThat(problemRepository.findAll().get(0))
                .extracting("question", "choices", "answer", "description", "category", "type")
                .contains(question, choices, answer, description, category, ProblemType.MULTIPLECHOICE);
    }
    @Test
    @DisplayName("카테고리, Pabeable를 조건으로 DB에서 조회한다.")
    void findProblemByCategory() {
        // given
        insertProblemContent();
        Pageable pageable1 = PageRequest.of(0, 3); // page는 0-based index입니다.
        Pageable pageable2 = PageRequest.of(1, 3); // page는 0-based index입니다.
        Pageable pageable3 = PageRequest.of(2, 3); // page는 0-based index입니다.
        Pageable pageable4 = PageRequest.of(3, 3); // page는 0-based index입니다.
        // when
        Page<Problem> problemByCategory1 = problemRepository.findProblemByCategory(ProblemCategory.DB, pageable1);
        Page<Problem> problemByCategory2 = problemRepository.findProblemByCategory(ProblemCategory.DB, pageable2);
        Page<Problem> problemByCategory3 = problemRepository.findProblemByCategory(ProblemCategory.DB, pageable3);
        Page<Problem> problemByCategory4 = problemRepository.findProblemByCategory(ProblemCategory.DB, pageable4);
        // then
        assertThat(problemByCategory1.getContent()).hasSize(3)
                .extracting(problem -> problem.getQuestion()
                        ,problem -> problem.getAnswer()
                        ,Problem::getCategory
                        ,Problem::getType)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question1", "answer1", ProblemCategory.DB, ProblemType.MULTIPLECHOICE),
                        Tuple.tuple("question2", "answer2", ProblemCategory.DB, ProblemType.MULTIPLECHOICE),
                        Tuple.tuple("question3", "answer3", ProblemCategory.DB, ProblemType.MULTIPLECHOICE)
                );

    }

    @Test
    @DisplayName("타입, Pabeable를 조건으로 DB에서 조회한다.")
    void findProblemByType() {
        // given
        insertProblemContent();
        Pageable pageable1 = PageRequest.of(0, 3); // page는 0-based index입니다.
        Pageable pageable2 = PageRequest.of(1, 3); // page는 0-based index입니다.
        Pageable pageable3 = PageRequest.of(2, 3); // page는 0-based index입니다.
        Pageable pageable4 = PageRequest.of(3, 3); // page는 0-based index입니다.
        // when
        Page<Problem> problemByCategory1 = problemRepository.findProblemByType(ProblemType.SHORTANSWER, pageable1);
        Page<Problem> problemByCategory2 = problemRepository.findProblemByType(ProblemType.SHORTANSWER, pageable2);
        Page<Problem> problemByCategory3 = problemRepository.findProblemByType(ProblemType.SHORTANSWER, pageable3);
        Page<Problem> problemByCategory4 = problemRepository.findProblemByType(ProblemType.SHORTANSWER, pageable4);


        // then
        assertThat(problemByCategory1.getContent()).hasSize(3)
                .extracting(
                        problem->problem.getQuestion()
                        ,problem->problem.getAnswer()
                        ,Problem::getCategory
                        ,Problem::getType
                )
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question21", "answer21", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question22", "answer22", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question23", "answer23", ProblemCategory.NETWORK, ProblemType.SHORTANSWER)
                );
        assertThat(problemByCategory2.getContent()).hasSize(3)
                .extracting(
                        problem -> problem.getQuestion()
                        , problem -> problem.getAnswer()
                        , Problem::getCategory
                        , Problem::getType
                )
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question24", "answer24", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question25", "answer25", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question26", "answer26", ProblemCategory.NETWORK, ProblemType.SHORTANSWER)
                );
        assertThat(problemByCategory3.getContent()).hasSize(3)
                .extracting(
                        problem -> problem.getQuestion()
                        , problem -> problem.getAnswer()
                        , Problem::getCategory
                        , Problem::getType
                )
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question27", "answer27", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question28", "answer28", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question29", "answer29", ProblemCategory.NETWORK, ProblemType.SHORTANSWER)
                );
        assertThat(problemByCategory4.getContent()).hasSize(1)
                .extracting(
                        problem -> problem.getQuestion()
                        , problem -> problem.getAnswer()
                        , Problem::getCategory
                        , Problem::getType
                )
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question30", "answer30", ProblemCategory.NETWORK, ProblemType.SHORTANSWER)
                );

    }

    @Test
    @DisplayName("카테고리, 타입, Pabeable를 조건으로 DB에서 조회한다.")
    void findProblemByCategoryAndType() {
        // given
        insertProblemContent();
        Pageable pageable1 = PageRequest.of(0, 3); // page는 0-based index입니다.
        Pageable pageable2 = PageRequest.of(0, 3); // page는 0-based index입니다.
        Pageable pageable3 = PageRequest.of(0, 3); // page는 0-based index입니다.
        // when
        Page<Problem> problemByCategory1 = problemRepository.findProblemByCategoryAndType(ProblemCategory.DB,ProblemType.MULTIPLECHOICE, pageable1);
        Page<Problem> problemByCategory2 = problemRepository.findProblemByCategoryAndType(ProblemCategory.OS,ProblemType.TRUEORFALSE, pageable2);
        Page<Problem> problemByCategory3 = problemRepository.findProblemByCategoryAndType(ProblemCategory.NETWORK,ProblemType.SHORTANSWER, pageable3);


        // then
        assertThat(problemByCategory1.getContent()).hasSize(3)
                .extracting(problem -> problem.getQuestion()
                        ,problem -> problem.getAnswer()
                        ,Problem::getCategory
                        ,Problem::getType)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question1", "answer1", ProblemCategory.DB, ProblemType.MULTIPLECHOICE),
                        Tuple.tuple("question2", "answer2", ProblemCategory.DB, ProblemType.MULTIPLECHOICE),
                        Tuple.tuple("question3", "answer3", ProblemCategory.DB, ProblemType.MULTIPLECHOICE)
                );
        assertThat(problemByCategory2.getContent()).hasSize(3)
                .extracting(problem -> problem.getQuestion()
                        , problem -> problem.getAnswer()
                        , Problem::getCategory
                        , Problem::getType)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question11", "answer11", ProblemCategory.OS, ProblemType.TRUEORFALSE),
                        Tuple.tuple("question12", "answer12", ProblemCategory.OS, ProblemType.TRUEORFALSE),
                        Tuple.tuple("question13", "answer13", ProblemCategory.OS, ProblemType.TRUEORFALSE)
                );

        assertThat(problemByCategory3.getContent()).hasSize(3)
                .extracting(problem -> problem.getQuestion()
                        , problem -> problem.getAnswer()
                        , Problem::getCategory
                        , Problem::getType)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question21", "answer21", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question22", "answer22", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question23", "answer23", ProblemCategory.NETWORK, ProblemType.SHORTANSWER)
                );
    }
    @Test
    @DisplayName("pageable을 이용해서 모든 문제를 조회할 수 있다.")
    void findAll() {
        // given
        insertProblemContent();
        PageRequest pageRequest1 = PageRequest.of(0, 2);
        // when
        Page<Problem> problemPage = problemRepository.findAll(pageRequest1);
        // then
        assertThat(problemPage.getContent()).hasSize(2);
    }

    @Test
    @DisplayName("category, type, size로 문제를 Random으로 조회할 수 있다.")
    void findProblemByCategoryAndTypeRandom() {
        // given
        insertProblemContentRandom();

        ProblemCategory category1= ProblemCategory.DB;
        ProblemCategory category2= ProblemCategory.OS;
        ProblemType type1 = ProblemType.MULTIPLECHOICE;
        ProblemType type2 = ProblemType.TRUEORFALSE;
        int size = 10;

        // when
        Set<Problem> set1 = new HashSet<>(problemRepository.findProblemByCategoryAndTypeRandom(category1, type1, size));
        Set<Problem> set2 = new HashSet<>(problemRepository.findProblemByCategoryAndTypeRandom(category2, type2, size));

        // then
        assertThat(set1).hasSize(10).extracting("category", "type")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(category1, type1)
                        ,Tuple.tuple(category1, type1)
                        ,Tuple.tuple(category1, type1)
                        ,Tuple.tuple(category1, type1)
                        ,Tuple.tuple(category1, type1)
                        ,Tuple.tuple(category1, type1)
                        ,Tuple.tuple(category1, type1)
                        ,Tuple.tuple(category1, type1)
                        ,Tuple.tuple(category1, type1)
                        ,Tuple.tuple(category1, type1)
                );
        assertThat(set2).hasSize(10);
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
    private void insertProblemContentRandom(){
        for (int i = 1; i <=20 ; i++) {
            MultipleChoiceProblemSaveRequest multipleChoice = MultipleChoiceProblemSaveRequest.builder()
                    .question("question" + i)
                    .answer("answer" + i)
                    .choices(new String[]{"choice1", "choice2", "choice3", "choice4"})
                    .description("description"+i)
                    .problemCategory(ProblemCategory.DB)
                    .build();

            problemRepository.save(Problem.createMultipleChoice(multipleChoice));

        }
        for (int i = 21; i <= 40; i++) {
            TrueOrFalseProblemSaveRequest trueOrFalseProblemSaveRequest = TrueOrFalseProblemSaveRequest.builder()
                    .question("question" + i)
                    .answer("answer" + i)
                    .description("description" + i)
                    .problemCategory(ProblemCategory.OS)
                    .build();

            problemRepository.save(Problem.createTrueOrFalse(trueOrFalseProblemSaveRequest));

        }
        for (int i = 41; i <= 60; i++) {
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