package project.BaekjoonStatus.api.auth.service.token;

import project.BaekjoonStatus.shared.common.service.DateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RegisterTokenStore {
    private final Map<String, RegisterToken> registerTokenStore = new HashMap<>();

    public synchronized String put(List<String> problemIds, DateService dateService) {
        String key = UUID.randomUUID().toString();
        RegisterToken token = RegisterToken.builder()
                .createdAt(dateService.getDate())
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
