package web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Для разработки. В продакшене лучше включить!
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll() // Разрешить доступ без аутентификации
                .anyRequest().authenticated() // Остальные запросы требуют аутентификации
                .and()
                .formLogin()
                .loginPage("/login") // Указание кастомной страницы логина
                .defaultSuccessUrl("/", true) // Куда перенаправлять после успешного входа
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // После выхода
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1) // Разрешить одну активную сессию
                .maxSessionsPreventsLogin(false); // При повторной попытке входа завершать старую сессию
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}