package project.BaekjoonStatus.api.template.cache;

import project.BaekjoonStatus.api.cache.CacheCallback;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MapCacheTemplate<K, V> {
    private final Map<LocalDateTime, Map<K, V>> cache = new HashMap<>();

    public synchronized V get(LocalDateTime key, K id, CacheCallback<V> callback) {
        if(cache.containsKey(key) && cache.get(key).containsKey(id))
            return cache.get(key).get(id);

        V data = callback.execute();
        if(cache.containsKey(key) && !cache.get(key).containsKey(id)) {
            cache.get(key).put(id, data);
            return data;
        }

        cache.entrySet().removeIf(entry -> entry.getKey().isBefore(key));
        cache.put(key, new HashMap<>());
        cache.get(key).put(id, data);

        return data;
    }
}
