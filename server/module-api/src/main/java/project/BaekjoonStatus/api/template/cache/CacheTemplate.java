package project.BaekjoonStatus.api.template.cache;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CacheTemplate<V> {
    private final Map<LocalDateTime, V> cache = new HashMap<>();

    public synchronized V get(LocalDateTime key, CacheCallback<V> callback) {
        if(cache.containsKey(key))
            return cache.get(key);

        cache.entrySet().removeIf(entry -> entry.getKey().isBefore(key));

        return cache.put(key, callback.execute());
    }
}
