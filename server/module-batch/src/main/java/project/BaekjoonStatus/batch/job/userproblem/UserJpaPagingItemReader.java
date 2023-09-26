package project.BaekjoonStatus.batch.job.userproblem;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import project.BaekjoonStatus.shared.member.domain.Member;
import project.BaekjoonStatus.shared.member.service.MemberService;

import java.util.ArrayList;

public class UserJpaPagingItemReader extends AbstractPagingItemReader<Member> {
    private final MemberService userService;

    private String memberId = "0";

    public UserJpaPagingItemReader(MemberService userService, int chunkSize) {
        this.userService = userService;
        setPageSize(chunkSize);
    }

    @Override
    protected void doReadPage() {
        if(results == null) {
            results = new ArrayList<>();
        } else {
            results.clear();
        }

        results.addAll(userService.findAllByGreaterThanUserId(memberId, getPageSize()));
        memberId = String.valueOf(Long.parseLong(memberId) + getPageSize());
    }

    @Override
    protected void doJumpToPage(int itemIndex) {
    }
}
