package project.BaekjoonStatus.api.cache;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CacheNode<T> {
    LocalDateTime expireAt;
    T data;

    public CacheNode(LocalDateTime expireAt, T data) {
        this.expireAt = expireAt;
        this.data = data;
    }
}
