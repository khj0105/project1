package com.example.project1.config;

import com.example.project1.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
 * === WebSecurityConfig ===
 * : Spring Security 설정을 담당하는 클래스.
 * - JWT 인증 필터를 등록하고, URL별 접근 권한을 설정합니다.
 */
@Configuration // 이 클래스가 스프링의 설정 정보를 담고 있음을 명시
@EnableWebSecurity // 스프링 시큐리티의 웹 보안 기능을 활성화
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성 (생성자 주입 방식)

public class WebSecurityConfig {

    // JWT 인증 로직을 담고 있는 커스텀 필터를 생성자 주입 방식으로 주입받음
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /*
     * === passwordEncoder ===
     * : 비밀번호를 안전하게 암호화하기 위한 PasswordEncoder를 빈(Bean)으로 등록.
     * - BCrypt 해싱 알고리즘을 사용합니다.
     */
    // 추가
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * === authenticationManager ===
     * : 사용자의 인증(로그인) 요청을 처리하는 AuthenticationManager를 빈(Bean)으로 등록.
     * - Spring Security 5.7 이상부터 권장되는 방식으로, AuthenticationConfiguration을 통해 가져옵니다.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /*
     * === corsFilter ===
     * : CORS(Cross-Origin Resource Sharing) 정책을 설정하는 빈(Bean).
     * - 다른 도메인(e.g., 프론트엔드 서버)에서의 요청을 허용하기 위해 필요합니다.
     */
    @Bean
    public CorsConfigurationSource corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 요청에 자격 증명(쿠키 등) 포함 허용
        config.addAllowedOriginPattern("*"); // 모든 출처(도메인)에서의 요청 허용
        config.addAllowedHeader("*"); // 모든 HTTP 헤더 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메서드(GET, POST 등) 허용


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 URL 경로("/**")에 대해 위에서 정의한 CORS 정책을 적용
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /*
     * === filterChain ===
     * : Spring Security의 핵심으로, 보안 필터 체인을 설정.
     * - HTTP 요청에 대한 보안 규칙(인증/인가)을 정의합니다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF(Cross-Site Request Forgery) 보호 기능을 비활성화.
                // (stateless한 JWT 방식에서는 사용하지 않는 것이 일반적)
                .csrf(AbstractHttpConfigurer::disable)
                // 위에서 정의한 corsFilter 빈을 사용하여 CORS 정책을 적용
                .cors(cors -> cors.configurationSource(corsFilter()))
                // 각 URL 경로별로 접근 권한을 설정
                .authorizeHttpRequests(auth -> auth
                        // 전체 권한 (인증 X)
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/common/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                        // 단독 권한
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/teacher/**").hasRole("TEACHER")
                        .requestMatchers("/api/v1/student/**").hasRole("STUDENT")
                        // 중복 권한
                        .requestMatchers("/api/v1/manage/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/api/v1/classroom/**").hasAnyRole("TEACHER", "STUDENT")
                        .anyRequest().authenticated()
                )
                // Spring Security의 기본 로그인 폼 사용을 비활성화 (API 서버이므로)
                .formLogin(AbstractHttpConfigurer::disable)
                // 로그아웃 기능을 설정. "/api/v1/auth/logout"으로 요청 시 로그아웃 처리
                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout").permitAll())
                // 가장 중요한 부분: 직접 만든 jwtAuthenticationFilter를 기본 인증 필터(UsernamePasswordAuthenticationFilter)보다 먼저 실행하도록 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}