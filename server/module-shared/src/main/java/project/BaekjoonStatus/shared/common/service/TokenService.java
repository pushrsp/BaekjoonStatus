package project.BaekjoonStatus.shared.common.service;

public interface TokenService {
    String generate(String data, String secret, Long offset);
    String extract(String authorization);
    String verify(String token, String secret);
}
