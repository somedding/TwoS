package test.test_Internet.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import test.test_Internet.login.UserRole;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF(Cross-Site Request Forgery) 보호를 비활성화
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/static/css/**", "/static/web/img/**", "/static/web/js/**", "/static/web/**", "/static/**", "/static/web/styles/**", "/profile", "/h2-console/**").permitAll() // 모두 접근 가능
                        .requestMatchers("/api/v1/**").hasRole(UserRole.USER.name()) // "/api/v1/**" 패턴의 URL은 USER 권한을 가진 사용자만 접근 가능
                        .anyRequest().authenticated() // 나머지 모든 요청은 인증된 사용자만 접근 가능
                ) // 요청 URL에 따른 권한을 설정
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
                // 로그아웃 시 리다이렉트될 URL을 설정 + 세션 무효화 설정
                .oauth2Login(oauth2Login -> oauth2Login
                        .defaultSuccessUrl("/sweetodo/todo/todoMain") // OAuth 2 로그인 설정 진입점
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService) // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정
                        )
                )
                // X-Frame-Options 설정 추가
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // 동일 출처에서만 프레임으로 로드 가능
                );

        return http.build();
    }
}
