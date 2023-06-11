package project.BaekjoonStatus.api.argumentresolver;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import project.BaekjoonStatus.shared.common.utils.JWTProvider;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {
    private final String tokenSecret;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(Auth.class);
        boolean isSameType = String.class.isAssignableFrom(parameter.getParameterType());

        return hasAnnotation && isSameType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = JWTProvider.extractToken(request.getHeader("Authorization"));

        return JWTProvider.validateToken(token, tokenSecret);
    }
}
