package project.BaekjoonStatus.batch.job.userproblem;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import project.BaekjoonStatus.shared.user.domain.User;
import project.BaekjoonStatus.shared.user.service.UserService;

import java.util.ArrayList;

public class UserJpaPagingItemReader extends AbstractPagingItemReader<User> {
    private final UserService userService;

    private Long userId = 0L;

    public UserJpaPagingItemReader(UserService userService, int chunkSize) {
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

        results.addAll(userService.findAllByGreaterThanUserId(userId, getPageSize()));
        userId += getPageSize();
    }

    @Override
    protected void doJumpToPage(int itemIndex) {
    }
}
