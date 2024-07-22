package com.israr.israr_zaslavskaya_inga_voting_app.config;

import com.israr.israr_zaslavskaya_inga_voting_app.security.CustomVoterDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private CustomVoterDetailsService customVoterDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/candidateInfo").permitAll()
                                .requestMatchers(request -> {
                                    return request.getMethod().equals(HttpMethod.DELETE.toString())
                                            && request.getServletPath().matches("/search-candidates/\\d+");
                                }).permitAll()
//                        .requestMatchers("/confirmInfo").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers("/election1").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/election2").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/review").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/main-menu").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers("/submitted").permitAll()
//                        .requestMatchers("/menu-admin").hasRole("ADMIN")
//                        .requestMatchers("/register-candidate").hasRole("ADMIN")
//                        .requestMatchers("register-candidate-image").hasRole("ADMIN")
//                        .requestMatchers("search-election").hasRole("ADMIN")
//                        .requestMatchers("/set-election").hasRole("ADMIN")
//                        .requestMatchers("/viewResults").hasRole("ADMIN")
//                        .requestMatchers("/update-election").hasRole("ADMIN")
//                        .requestMatchers("/search-candidates").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/main-menu")
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customVoterDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
