package project.BaekjoonStatus.shared.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.BaekjoonStatus.shared.member.domain.Member;
import project.BaekjoonStatus.shared.member.infra.MemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository userRepository;

    @Transactional
    public Member save(Member user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        if(!isValidUsername(username)) {
            return Optional.empty();
        }

        return userRepository.findByUsername(username);
    }

    private boolean isValidUsername(String username) {
        return username != null && !username.isBlank() && !hasSpecialCharacter(username);
    }

    private boolean hasSpecialCharacter(String username) {
        String specialCharactersPattern = "[^a-zA-Z0-9]";

        Pattern pattern = Pattern.compile(specialCharactersPattern);
        Matcher matcher = pattern.matcher(username);

        return matcher.find();
    }

    @Transactional(readOnly = true)
    public List<Member> findAllByGreaterThanUserId(Long userId, int limit) {
        return userRepository.findAllByGreaterThanUserId(userId, limit);
    }
}
