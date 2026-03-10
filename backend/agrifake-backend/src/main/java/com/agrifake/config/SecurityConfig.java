package com.agrifake.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      // ===== CSRF 有効 =====
      .csrf(csrf -> csrf
          .ignoringRequestMatchers(
              "/api/recruitApplication", // ユーザー側採用応募フォーム
              "/api/contactForm"  // ユーザー側採用応募フォーム
          ) 
      )
      // ===== URLごとの認可 =====
      .authorizeHttpRequests(auth -> auth
          // ===== 一般公開 =====
          .requestMatchers(
              "/api/recruitApplication",
              "/api/contact",
              "/admin/login",
              "/api/admin/login",
              "/uploads/**"   // アップロード画像公開）
          ).permitAll()

          // ===== 管理者専用 =====
          .requestMatchers("/admin/**", "/api/admin/**")
          .hasRole("ADMIN")

          .anyRequest().permitAll()
      )
      
      // ===== ログイン設定 =====
      .formLogin(form -> form
          .loginPage("/admin/login") // 自作ログイン画面
          .loginProcessingUrl("/api/admin/login") // 認証処理
          .defaultSuccessUrl("/admin/gallery", true)
          .failureUrl("/admin/login?error")
          .permitAll()
      )
      
      // ===== ログアウト =====
      .logout(logout -> logout
          .logoutUrl("/admin/logout")
          .logoutSuccessUrl("/admin/login")
          .invalidateHttpSession(true)
          .deleteCookies("JSESSIONID")
      )
      
      .cors(cors -> {});
    

    return http.build();
  }
  
   @Bean
   public UserDetailsService userDetailsService() {
       UserDetails admin = User.builder()
           .username("admin") // 開発用ユーザーネーム
           .password("{noop}password") // 開発用パスワード
           .roles("ADMIN")
           .build();
  
       return new InMemoryUserDetailsManager(admin);
   }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500"));
    configuration.setAllowedMethods(List.of("GET", "POST"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}