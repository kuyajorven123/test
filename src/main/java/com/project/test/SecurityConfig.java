package com.project.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.authorizeHttpRequests(registry -> {
            registry.requestMatchers("/admin/**").hasRole("ADMIN");
            registry.requestMatchers("/employee/**").hasRole("EMPLOYEE");
            registry.anyRequest().authenticated();
        })
        .formLogin(form -> form
            // .loginPage("/login") //for login page
            .permitAll())
        
        .logout(logout -> logout.permitAll())
        .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails adminUser = User.builder()
            .username("test")
            .password(passwordEncoder().encode("test"))
            .roles("ADMIN","EMPLOYEE")
            .build();
        UserDetails employeeUser = User.builder()
            .username("employ")
            .password(passwordEncoder().encode("employ"))
            .roles("EMPLOYEE")
            .build();
        return new InMemoryUserDetailsManager(adminUser , employeeUser);
    }
}
