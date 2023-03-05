package project.BaekjoonStatus.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    SOLVED_AC_USER_NOT_FOUND("3404", "해당 유저가 존재하지 않습니다."),
    SOLVED_AC_PROBLEM_NOT_FOUND("3404", "해당 문제가 존재하지 않습니다"),
    BAEKJOON_NOT_FOUND("2404", "해당 유저가 존재하지 않습니다.");

    private final String code;
    private final String message;
}
