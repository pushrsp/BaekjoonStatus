package project.BaekjoonStatus.shared.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    SUCCESS("0000", null),
    MY_SERVER_DTO_BAD_REQUEST("0400", null),
    MY_SERVER_DUPLICATE("0400","다른 아이디를 이용해주세요."),
    MY_SERVER_LOGIN_BAD_REQUEST("0400", "아이디 또는 비밀번호가 일치하지 않습니다."),
    MY_SERVER_TOKEN_EXPIRED("0401", "토큰이 만료되었습니다."),
    MY_SERVER_NOT_VALID_TOKEN("0400", "올바르지 않은 토큰 형식입니다."),
    MY_SERVER_UNAUTHORIZED("0401", "접근할 수 없는 페이지입니다."),
    MY_SERVER_UNKNOWN_HOST("0400", "존재하지 않은 URL 입니다."),

    BAEKJOON_NOT_FOUND("2404", "해당 유저가 존재하지 않습니다."),
    BAEKJOON_SERVER_ERROR("2999", "백준 서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요."),

    SOLVED_AC_SERVER_ERROR("3500","solved.ac 서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요."),
    SOLVED_AC_USER_NOT_FOUND("3404", "해당 유저가 존재하지 않습니다."),
    SOLVED_AC_PROBLEM_NOT_FOUND("3404", "해당 문제가 존재하지 않습니다."),
    SOLVED_AC_PROBLEMS_NOT_FOUND("3404","해당 문제목록이 존제하지 않습니다."),

    UNKNOWN_EXCEPTION("9999","서버에 문제가 생겼습니다. 관리자에게 문의 해주세요.");

    private final String code;
    private final String message;
}
