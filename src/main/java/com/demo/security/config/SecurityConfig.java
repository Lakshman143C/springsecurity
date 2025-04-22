package com.demo.security.config;

import com.demo.security.filter.JWTAuthFilter;
import com.demo.security.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired private JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth->{
                            auth
                                    .requestMatchers(
                                            "/auth/authenticate",
                                            "/api/v1/user/register",
                                            "/demo/hey"
                                    ).permitAll()
                                    //.requestMatchers("/api/v1/user/register","/api/v1/user/login").permitAll();
                                    .anyRequest().authenticated();

                        }
                );
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailService();
    }

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }
}
