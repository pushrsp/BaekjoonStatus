package project.BaekjoonStatus.shared.common.exception;

public class InvalidIdFormatException extends RuntimeException {
    public InvalidIdFormatException() {
        super("id 형식이 올바르지 않습니다.");
    }

    public InvalidIdFormatException(String message) {
        super(message);
    }
}
