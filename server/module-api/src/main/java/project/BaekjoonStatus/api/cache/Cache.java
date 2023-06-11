package project.BaekjoonStatus.api.cache;

import project.BaekjoonStatus.shared.common.utils.DateProvider;

import java.util.HashMap;
import java.util.Map;

public class Cache<T> {
    private final Map<String, CacheNode<T>> cache = new HashMap<>();

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    public T put(String key, T data) {
        cache.put(key, new CacheNode<>(DateProvider.getNextCacheKey(), data));

        return data;
    }

    public T get(String key, CacheCallback<T> callback) {
        if(!containsKey(key))
            return put(key, callback.execute());

        CacheNode<T> cacheNode = cache.get(key);
        if(cacheNode.getExpireAt().isAfter(DateProvider.getDateTime()))
            return cacheNode.getData();

        cacheNode.setData(callback.execute());
        cacheNode.setExpireAt(DateProvider.getNextCacheKey());
        return cacheNode.getData();
    }
}
