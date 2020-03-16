package ru.otus.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/**").hasAnyRole("USER", "EDITOR", "ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .successForwardUrl("/")
                    .permitAll()
                .and()
                    .rememberMe()
                    .key("salt")
                    .tokenValiditySeconds(60 * 60 * 24)
                .and()
                    .logout()
                    .clearAuthentication(true)
                    .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("password").roles("ADMIN")
                .and()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("editor").password("password").roles("EDITOR");
    }
}
