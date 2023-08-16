package project.BaekjoonStatus.shared.common.service;

public interface PasswordService {
    String hashPassword(String plainPassword);
    boolean validatePassword(String plainPassword, String hashedPassword);
}
