package com.jinmlee.articleProject.config;

import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.enums.Role;
import com.jinmlee.articleProject.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        Role[] roleList = Role.values();

        StringBuilder hierarchy = new StringBuilder();
        for(int i = 0; i < roleList.length - 1; i++) {
            hierarchy
                    .append(roleList[i].getRoleName())
                    .append(" > ")
                    .append(roleList[i + 1].getRoleName())
                    .append("\n");
        }

        return RoleHierarchyImpl.fromHierarchy(hierarchy.toString());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/member/login", "/member/join", "/css/**", "/articleList", "/h2-console/**", "/api/members", "/swagger-ui.html/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/manager").hasRole("MANAGER")
                        .requestMatchers("/user").hasRole("USER")
                        .anyRequest().authenticated()
                );

        http
                .formLogin((auth) -> auth.loginPage("/member/login")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/api/members/login")
                        .defaultSuccessUrl("/articleList", true)
                        .permitAll()
                );

        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // H2 콘솔을 위한 설정
                );

        return http.build();
    }
}
