package com.cyes.webserver.domain.problem.entity;

import com.cyes.webserver.domain.problem.dto.request.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import com.mongodb.lang.Nullable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.Objects;

@Getter
@Document
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {
    //pk
    @Id
    private String id;

    @Field("problem_question")
    private String question;

    @Field("problem_choices")
    @Nullable
    private String[] choices;

    @Field("problem_answer")
    private String answer;

    @Field("problem_decription")
    private String description;

    //카테고리(네트워크, 운영체제 등등)
    //인덱싱으로 조회 성능 향상
    @Indexed
    @Field(name = "problem_category")
    private String category;

    //문제유형 객관식, 단답형 등등
    //인덱싱으로 조회 성능 향상
    @Indexed
    @Field(name = "problem_type")
    private String type;



    @Builder
    private Problem(String id, String question, @Nullable String[] choices, String answer, String description, ProblemCategory category, ProblemType type) {
        this.id = id;
        this.question = question;
        this.choices = choices;
        this.answer = answer;
        this.description = description;
        this.category = String.valueOf(category);
        this.type = String.valueOf(type);
    }

    public static Problem  createMultipleChoice(MultipleChoiceProblemSaveRequest multipleChoiceProblemSaveRequest){
        return Problem.builder()
                .question(multipleChoiceProblemSaveRequest.getQuestion())
                .choices(multipleChoiceProblemSaveRequest.getChoices())
                .answer(multipleChoiceProblemSaveRequest.getAnswer())
                .description(multipleChoiceProblemSaveRequest.getDescription())
                .category(multipleChoiceProblemSaveRequest.getProblemCategory())
                .type(ProblemType.MULTIPLECHOICE)
                .build();
    }

    public static Problem createShortAnswer(ShortAnswerProblemSaveRequest shortAnswerProblemSaveRequest){
        return Problem.builder()
                .question(shortAnswerProblemSaveRequest.getQuestion())
                .answer(shortAnswerProblemSaveRequest.getAnswer())
                .description(shortAnswerProblemSaveRequest.getDescription())
                .category(shortAnswerProblemSaveRequest.getProblemCategory())
                .type(ProblemType.SHORTANSWER)
                .build();
    }

    public static Problem createTrueOrFalse(TrueOrFalseProblemSaveRequest trueOrFalseProblemSaveRequest){
        return Problem.builder()
                .question(trueOrFalseProblemSaveRequest.getQuestion())
                .answer(trueOrFalseProblemSaveRequest.getAnswer())
                .category(trueOrFalseProblemSaveRequest.getProblemCategory())
                .description(trueOrFalseProblemSaveRequest.getDescription())
                .type(ProblemType.TRUEORFALSE)
                .build();
    }
    public ProblemResponse toProblemResponse(int problemOrder){
        return ProblemResponse.builder()
                .id(this.id)
                .question(this.question)
                .choices(this.choices)
                .answer(this.answer)
                .description(this.description)
                .problemOrder(problemOrder)
                .category(ProblemCategory.valueOf(this.category))
                .type(ProblemType.valueOf(this.type))
                .build();
    }

    public ProblemCategory getCategory(){
        return ProblemCategory.valueOf(this.category);
    }
    public ProblemType getType(){
        return ProblemType.valueOf(this.type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return Objects.equals(id, problem.id) && Objects.equals(question, problem.question) && Arrays.equals(choices, problem.choices) && Objects.equals(answer, problem.answer) && Objects.equals(description, problem.description) && Objects.equals(category, problem.category) && Objects.equals(type, problem.type);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, question, answer, description, category, type);
        result = 31 * result + Arrays.hashCode(choices);
        return result;
    }
}
