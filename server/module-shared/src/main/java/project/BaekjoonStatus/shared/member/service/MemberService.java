package project.BaekjoonStatus.shared.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.BaekjoonStatus.shared.member.domain.Member;
import project.BaekjoonStatus.shared.member.infra.MemberRepository;
import project.BaekjoonStatus.shared.member.service.request.MemberCreateSharedServiceRequest;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository userRepository;

    @Transactional
    public Member save(MemberCreateSharedServiceRequest request) {
        return userRepository.save(request.toDomain());
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(String memberId) {
        return userRepository.findById(memberId);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Member> findAllByGreaterThanUserId(String memberId, int limit) {
        return userRepository.findAllGreaterThanMemberId(memberId, limit);
    }
}
