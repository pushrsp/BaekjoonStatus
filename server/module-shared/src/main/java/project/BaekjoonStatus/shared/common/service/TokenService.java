package project.BaekjoonStatus.shared.common.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    String generate(String data, String secret, Long offset);
    String extract(HttpServletRequest request);
    String verify(String token, String secret);
}
