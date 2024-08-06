package test.test_Internet.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 사용자 정보 가져오기 (예: OAuth2User)
        String email = getEmailFromAuthentication(authentication);

        // 이메일을 포함하여 리다이렉트 URL 생성
        String redirectUrl = "/sweetodo/todo/todoMain?email=" + email;

        response.sendRedirect(redirectUrl);
    }

    private String getEmailFromAuthentication(Authentication authentication) {
        // 인증 객체에서 이메일을 추출하는 로직 (예: OAuth2User 사용)
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        return oauth2User.getAttribute("email"); // 이메일 키는 제공되는 정보에 따라 다를 수 있습니다.
    }
}
