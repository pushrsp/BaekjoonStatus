package project.BaekjoonStatus.shared.common.utils;

public interface PasswordEncryptor {
    String hashPassword(String plainPassword);
    boolean validatePassword(String plainPassword, String hashedPassword);
}
