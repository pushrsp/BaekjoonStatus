package project.BaekjoonStatus.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {

    BAEKJOON_NOT_FOUND("2404", "해당 유저가 존재하지 않습니다.");

    private final String code;
    private final String message;

}
