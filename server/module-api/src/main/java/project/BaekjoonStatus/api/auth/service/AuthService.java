package project.BaekjoonStatus.api.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

import project.BaekjoonStatus.api.auth.service.request.MemberCreateServiceRequest;
import project.BaekjoonStatus.api.auth.service.request.MemberLoginServiceRequest;
import project.BaekjoonStatus.api.auth.service.token.RegisterTokenStore;
import project.BaekjoonStatus.api.concurrent.annotation.CustomAsync;

import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.common.service.PasswordService;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.baekjoon.service.BaekjoonService;
import project.BaekjoonStatus.shared.problem.service.request.ProblemCreateSharedServiceRequest;
import project.BaekjoonStatus.shared.solvedac.domain.SolvedAcProblem;
import project.BaekjoonStatus.shared.solvedac.service.SolvedAcService;
import project.BaekjoonStatus.shared.problem.service.ProblemService;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistory;
import project.BaekjoonStatus.shared.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.tag.domain.Tag;
import project.BaekjoonStatus.shared.tag.service.TagService;
import project.BaekjoonStatus.shared.member.domain.Member;
import project.BaekjoonStatus.shared.member.service.MemberService;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;
import project.BaekjoonStatus.shared.tag.service.request.TagCreateSharedServiceRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RegisterTokenStore registerTokenStore = new RegisterTokenStore();

    private final BaekjoonService baekjoonService;
    private final SolvedAcService solvedAcService;

    private final ProblemService problemService;
    private final TagService tagService;
    private final MemberService memberService;
    private final SolvedHistoryService solvedHistoryService;

    private final PasswordService passwordService;
    private final DateService dateService;

    public Member getById(String memberId) {
        return memberService.findById(memberId)
                .orElseThrow(() -> new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST));
    }

    public List<String> getByBaekjoonUsername(String baekjoonUsername) {
        // TODO: validation
        // @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "아이디는 특수문자를 제외한 2~20자리여야 합니다.")
        return baekjoonService.getProblemIdsByUsername(baekjoonUsername);
    }

    public String getRegisterToken(List<String> problemIds) {
        return registerTokenStore.put(problemIds, dateService);
    }

    @CustomAsync(maxTry = 10, offset = 2, delay = 180000)
    public void createProblems(List<String> problemIds, LocalDateTime createdTime) {
        List<String> notSavedIds = problemService.findAllByNotExistedIds(problemIds);
        if(notSavedIds.isEmpty()) {
            return;
        }

        List<SolvedAcProblem> solvedAcProblems = solvedAcService.findByIds(notSavedIds);
        problemService.saveAll(toProblemCreateSharedServiceRequests(solvedAcProblems, createdTime));
        tagService.saveAll(toTagCreateSharedServiceRequests(solvedAcProblems, createdTime));
    }

    private List<ProblemCreateSharedServiceRequest> toProblemCreateSharedServiceRequests(List<SolvedAcProblem> solvedAcProblems, LocalDateTime createdTime) {
        return SolvedAcProblem.toProblemList(solvedAcProblems, createdTime)
                .stream()
                .map(ProblemCreateSharedServiceRequest::from)
                .collect(Collectors.toList());
    }

    private List<TagCreateSharedServiceRequest> toTagCreateSharedServiceRequests(List<SolvedAcProblem> solvedAcProblems, LocalDateTime createdTime) {
         return SolvedAcProblem.toTagList(solvedAcProblems, createdTime)
                 .stream()
                 .map(TagCreateSharedServiceRequest::from)
                 .collect(Collectors.toList());
    }

    @Recover
    private void recover(MyException e, List<Long> ids, int index) {
        //FIXME
        log.info("createProblems index: {}", index);
    }

    @CustomAsync(maxTry = 10, offset = 2, delay = 200000)
    public void createSolvedHistories(Member member, List<String> problemIds) {
        List<Problem> problems = problemService.findAllByIdsIn(problemIds);
        if(problems.size() != problemIds.size()) {
            throw new MyException(CodeEnum.SOLVED_AC_SERVER_ERROR);
        }

        solvedHistoryService.saveAll(SolvedHistory.from(member, problems, true, dateService));
    }

    @Recover
    public void recover(MyException e, Member user, List<Long> problemIds) {
        //FIXME
        log.info("createSolvedHistories userId: {}", user.getId());
    }

    public Member createUser(MemberCreateServiceRequest request) {
        duplicateUsername(request.getUsername());

        return memberService.save(request.toRequest(true, passwordService));
    }

    public List<String> getProblemIds(String registerToken) {
        return registerTokenStore.get(registerToken).getProblemIds();
    }

    public Member login(MemberLoginServiceRequest request) {
        Member findUser = memberService.findByUsername(request.getUsername())
                .orElseThrow(() -> new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST));

        findUser.login(request.getUsername(), request.getPassword(), passwordService);

        return findUser;
    }

    public void verifyRegisterToken(String registerToken) {
        if(!registerTokenStore.exist(registerToken)) {
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);
        }
    }

    private void duplicateUsername(String username) {
        memberService.findByUsername(username).ifPresent((u) -> {
                    throw new MyException(CodeEnum.MY_SERVER_DUPLICATE);
                });
    }
}
