package test.test_Internet.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import test.test_Internet.data.DeleteJsonService;
import test.test_Internet.login.UserRole;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private DeleteJsonService deleteJsonService;

    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/static/css/**", "/static/web/img/**", "/static/web/js/**", "/static/web/**", "/static/**", "/static/web/styles/**", "/profile", "/h2-console/**").permitAll()
                        .requestMatchers("/api/v1/**").hasRole(UserRole.USER.name())
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(logoutSuccessHandler()) // 로그아웃 성공 핸들러 추가
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(customAuthenticationSuccessHandler()) // 로그인 성공 핸들러 추가
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                );

        return http.build();
    }

    // 로그아웃 성공 핸들러 정의
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            // JSON 파일 삭제 로직
            deleteJsonService.deleteFileIfExists("src/main/resources/static/data/userInfo.json");
            deleteJsonService.deleteFileIfExists("src/main/resources/static/data/users.json");
            deleteJsonService.deleteFileIfExists("src/main/resources/static/data/screenTime.json");
            deleteJsonService.deleteFileIfExists("src/main/resources/static/data/someTime.json");
            deleteJsonService.deleteFileIfExists("src/main/resources/static/data/usageStatistics.json");
            deleteJsonService.deleteFileIfExists("src/main/resources/static/data/usageData.json");

            response.sendRedirect("/"); // 로그아웃 후 리다이렉트할 URL
        };
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}

