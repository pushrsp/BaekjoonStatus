package project.BaekjoonStatus.shared.common.template;

import java.util.List;
import java.util.function.Function;

public class ListDividerTemplate<T> {
    private final int offset;
    private final List<T> data;
    private int totalCount;

    public ListDividerTemplate(int offset, List<T> data) {
        this.offset = offset;
        this.data = data;
        setTotalCount();
    }

    private void setTotalCount() {
        this.totalCount = data.size() / offset;
        if(data.size() % offset != 0)
            this.totalCount++;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void execute(Function<List<T>, Void> callback) {
        int startIndex = 0;
        while (startIndex < data.size()) {
            List<T> sub = data.subList(startIndex, Math.min(startIndex + offset, data.size()));
            startIndex += offset;
            callback.apply(sub);
        }
    }
}
