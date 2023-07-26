package project.BaekjoonStatus.api.common.utils;

import org.mindrot.jbcrypt.BCrypt;
import project.BaekjoonStatus.shared.common.utils.PasswordEncryptor;

public class PasswordBcryptor implements PasswordEncryptor {
    @Override
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    @Override
    public boolean validatePassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
