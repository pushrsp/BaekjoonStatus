package project.BaekjoonStatus.shared.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;
import project.BaekjoonStatus.shared.util.BcryptProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    public User create(String username, String baekjoonUsername, String password) {
        //TODO: validation
        return new User(username, baekjoonUsername, password);
    }

    public User validate(Optional<User> user, String hashedPassword) {
        if(user.isEmpty())
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);

        User savedUser = user.get();
        if(!BcryptProvider.validatePassword(hashedPassword, savedUser.getPassword()))
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);

        return savedUser;
    }
}
