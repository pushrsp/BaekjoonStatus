package project.BaekjoonStatus.api.common.service;

import org.mindrot.jbcrypt.BCrypt;
import project.BaekjoonStatus.shared.common.service.PasswordService;

public class BcryptService implements PasswordService {
    @Override
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    @Override
    public boolean validatePassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
