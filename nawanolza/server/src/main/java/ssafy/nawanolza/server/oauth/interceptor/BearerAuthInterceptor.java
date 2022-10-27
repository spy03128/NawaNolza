package ssafy.nawanolza.server.oauth.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ssafy.nawanolza.server.domain.exception.TokenEmptyException;
import ssafy.nawanolza.server.oauth.jwt.AuthorizationExtractor;
import ssafy.nawanolza.server.oauth.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {
    private final AuthorizationExtractor authorizationExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IllegalAccessException {
        String token = authorizationExtractor.extract(request, "Bearer");

        if(token.isBlank()){
            throw new TokenEmptyException();
        }

        if(!jwtTokenProvider.validateToken(token)){
            throw new IllegalAccessException("유효하지 않은 토큰");
        }

        Long id = jwtTokenProvider.getSubject(token);
        request.setAttribute("id", id);
        return true;
    }
}
