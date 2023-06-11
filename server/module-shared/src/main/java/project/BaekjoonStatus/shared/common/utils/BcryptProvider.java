package project.BaekjoonStatus.shared.common.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptProvider {
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean validatePassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
