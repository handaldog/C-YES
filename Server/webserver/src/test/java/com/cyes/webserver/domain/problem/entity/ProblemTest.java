package com.cyes.webserver.domain.problem.entity;

import com.cyes.webserver.domain.problem.dto.request.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProblemTest {
     @Test
     @DisplayName("객관식 문제를 생성할 수 있다.")
     void createMultipleChoice() {
         // given
         String question = "질문";
         String[] choices = {"보기1", "보기2", "보기3"};
         String answer = "답";
         String description = "상세설명";
         ProblemCategory category = ProblemCategory.DB;

         MultipleChoiceProblemSaveRequest mul = MultipleChoiceProblemSaveRequest.builder().question(question).choices(choices).answer(answer).description(description).problemCategory(category).build();

         // when
         Problem multipleChoice = Problem.createMultipleChoice(mul);

         // then
         assertThat(multipleChoice).extracting("question", "choices", "answer", "description", "category", "type")
                 .contains(question, choices, answer, description, category, ProblemType.MULTIPLECHOICE);

     }
     @Test
     @DisplayName("단답형 문제를 생성할 수 있다.")
     void createShortAnswer() {
         // given
         String question = "질문";
         String answer = "답";
         String description = "상세설명";
         ProblemCategory category = ProblemCategory.DB;
         ShortAnswerProblemSaveRequest shortAnswerProblemSaveRequest = ShortAnswerProblemSaveRequest.builder().question(question).answer(answer).description(description).problemCategory(category).build();
         // when
         Problem shortAnswer = Problem.createShortAnswer(shortAnswerProblemSaveRequest);

         // then
         assertThat(shortAnswer).extracting("question", "answer", "description", "category", "type")
                 .contains(question,  answer, description, category, ProblemType.SHORTANSWER);
     }
     @Test
     @DisplayName("오엑스 문제를 생성할 수 있다.")
     void createTrueOrFalse() {
         // given
         String question = "질문";
         String answer = "답";
         String description = "상세설명";
         ProblemCategory category = ProblemCategory.DB;
         TrueOrFalseProblemSaveRequest trueOrFalseProblemSaveRequest = TrueOrFalseProblemSaveRequest.builder().question(question).answer(answer).description(description).problemCategory(category).build();
         // when
         Problem trueOrFalse = Problem.createTrueOrFalse(trueOrFalseProblemSaveRequest);

         // then
         assertThat(trueOrFalse).extracting("question", "answer", "description", "category", "type")
                 .contains(question,  answer, description, category, ProblemType.TRUEORFALSE);
     }
     @Test
     @DisplayName("Document를 Response Dto로 변환한다.")
     void toProblemResponse() {
         // given
         String id = "id";
         String question = "question";
         String[] choices = {"보기1", "보기2", "보기3"};
         String answer = "answer";
         String description = "description";
         ProblemCategory problemCategory = ProblemCategory.DB;
         ProblemType problemType = ProblemType.TRUEORFALSE;

         Problem problemWithOutChoices = Problem.builder().id(id).question(question).answer(answer).description(description).category(problemCategory).type(problemType).build();
         Problem problemWithChoices = Problem.builder().id(id).question(question).choices(choices).answer(answer).description(description).category(problemCategory).type(problemType).build();

         // when
         ProblemResponse problemResponseWithChoices = problemWithChoices.toProblemResponse(0);
         ProblemResponse problemResponseWithOutChoices = problemWithOutChoices.toProblemResponse(0);

         // then
         assertThat(problemResponseWithChoices).extracting("choices").isNotNull();
         assertThat(problemResponseWithOutChoices).extracting("choices").isNull();

         assertThat(problemResponseWithChoices).extracting("id", "question", "answer", "description", "category", "type")
                 .contains(id, question, answer, description, problemCategory, problemType);
         assertThat(problemResponseWithOutChoices).extracting("id", "question", "answer", "description", "category", "type")
                 .contains(id, question, answer, description, problemCategory, problemType);

     }
}