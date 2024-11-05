package sql.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder); // Указываем энкодер для паролей
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/register").permitAll() // Разрешаем доступ к регистрации для всех
                .antMatchers("/user/home").permitAll() // Разрешаем доступ к /home для всех
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                .and()
                .formLogin()
                .and()
                .logout().permitAll();
    }
}