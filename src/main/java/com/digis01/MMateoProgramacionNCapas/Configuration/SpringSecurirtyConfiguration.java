package com.digis01.MMateoProgramacionNCapas.Configuration;

import com.digis01.MMateoProgramacionNCapas.Service.UserDetailsJPAService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurirtyConfiguration {

    private UserDetailsJPAService userDetailsJPAService;

    public SpringSecurirtyConfiguration(UserDetailsJPAService userDetailsJPAService) {
        this.userDetailsJPAService = userDetailsJPAService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/login").permitAll()
                .requestMatchers("/UsuarioIndex/**")
                .hasAnyRole("ADMIN", "USER", "STUDENT", "TEACHER")
                .anyRequest().authenticated())
                .formLogin(form -> form
                    .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .successHandler(authenticationSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .userDetailsService(userDetailsJPAService);

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return  new AuthenticationHandler();
    }
    
}
