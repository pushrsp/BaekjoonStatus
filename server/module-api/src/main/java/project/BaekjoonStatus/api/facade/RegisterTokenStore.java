package project.BaekjoonStatus.api.facade;

import project.BaekjoonStatus.shared.util.DateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RegisterTokenStore {
    private final Map<String, RegisterToken> registerTokenStore = new HashMap<>();

    public synchronized String put(List<Long> problemIds) {
        String key = UUID.randomUUID().toString();
        RegisterToken token = RegisterToken.builder()
                .createdAt(DateProvider.getDate())
                .problemIds(problemIds)
                .build();

        registerTokenStore.put(key, token);

        return key;
    }

    public synchronized RegisterToken get(String key) {
        return registerTokenStore.get(key);
    }

    public synchronized void remove(String key) {
        registerTokenStore.remove(key);
    }

    public synchronized boolean exist(String key) {
        return registerTokenStore.containsKey(key);
    }
}
