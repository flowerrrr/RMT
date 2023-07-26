package de.flower.rmt.config;

import de.flower.rmt.service.security.ApplicationSecurityListener;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailService;

    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Autowired
    @SneakyThrows
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/wicket/resource/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // Use expressions
                .authorizeRequests()
                // Define security access paths
                .antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')")
                .antMatchers(
                        "/login/**",
                        "/about",
                        "/changelog",
                        "/error/**",
                        // needed to get ajax-submit in password-forgotten-page to work
                        "/wicket/page/**"
                ).permitAll()
                .anyRequest().authenticated()
                // Remember me configuration
                .and()
                .rememberMe()
                .key("remember-me-key-v-1")
                .tokenValiditySeconds(31536000) // expires after 1 year
                // Login configuration
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=1")
                // Logout configuration
                .and()
                .logout()
                .logoutSuccessUrl("/")
                // Exception handling configuration
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error/403");
    }

    @Bean
    public ApplicationSecurityListener applicationSecurityListener() {
        return new ApplicationSecurityListener();
    }


}