package project.BaekjoonStatus.shared.util;

import org.jsoup.Connection;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public abstract class MyCrawling {
    protected Connection conn;

    protected void setConn(Connection conn) {
        this.conn = conn;
    }

    public abstract List<Long> get();
}
