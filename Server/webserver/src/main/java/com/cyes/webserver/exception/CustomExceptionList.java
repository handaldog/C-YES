package com.cyes.webserver.exception;

import com.cyes.webserver.domain.stompSocket.dto.SubmitRedis;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum CustomExceptionList {

    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E001", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "서버 에러 발생."),
    REFRESH_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "E003", "토큰이 일치하지 않습니다."),
    ACCESS_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "E004", "토큰이 일치하지 않습니다."),
    MEMBER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"E005", "회원 정보를 찾을 수 없습니다."),
    QUIZ_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"E006","퀴즈 정보를 찾을 수 없습니다."),
    MESSAGE_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"E007", "메세지가 존재하지 않습니다."),
    CATEGORY_OR_TYPE_MUST_REQUIRED(HttpStatus.BAD_REQUEST,"E007", "카테고리, 타입중 적어도 하나가 필요합니다."),
    ALREADY_SUBMIT(HttpStatus.BAD_REQUEST, "E008", "이미 정답을 제출했습니다."),
    PROBLEM_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"E009","PROBLEM을 찾을 수 없습니다."),

    SCHEDULE_CREATE_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E010", "스케줄링 작업 테스크를 생성하지 못했습니다."),
    SCHEDULE_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "E011", "스케줄링 작업 테스크를 찾을 수 없습니다."),
    SCHEDULE_START_TIME_TOO_EARLY_ERROR(HttpStatus.TOO_EARLY, "E012", "스케줄링 시작 시간이 너무 이릅니다."),
    REDIS_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "E013", "제출된 답안 정보가 없습니다."),
    SUBMIT_TIME_ERROR(HttpStatus.BAD_REQUEST, "E014", "답안 제출시간은 문제 출제시간보다 같거나 빠를 수 없습니다."),
    BINDING_ERROR(HttpStatus.BAD_REQUEST, "E015", "요청에 정확한 값을 입력해야 합니다."),
    QUIZPROBLEM_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, "E016", "퀴즈에 문제가 존재하지 않습니다."),
    ADMIN_DOES_NOT_CREATE_GROUP_QUIZ(HttpStatus.BAD_REQUEST, "E017", "관리자는 그룹퀴즈를 만들 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private String message;

    CustomExceptionList(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    CustomExceptionList(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
