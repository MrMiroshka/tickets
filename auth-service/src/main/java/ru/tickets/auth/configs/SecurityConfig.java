package ru.tickets.auth.configs;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;


import org.springframework.web.bind.annotation.PostMapping;
import ru.tickets.auth.services.UserService;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> {
                    try {
                        authz
                                .and()
                                .csrf().disable()
                                .cors().disable()
                                .authorizeHttpRequests()
                                .anyRequest().permitAll()
                        .and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()

                        .headers().frameOptions().disable()
                        .and()
                        .exceptionHandling()
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){ //кладем в UserDetailsService всех юзеров из базы
        return new InMemoryUserDetailsManager(userService.findAllUsersDetails()); //
    }

    @Bean
    public AuthenticationManager authenticationManager(List<StandardAuthenticationProvider> authenticationProviders) {
        List<AuthenticationProvider> providers = new ArrayList<>(authenticationProviders);
        return new ProviderManager(providers);
    }

}
