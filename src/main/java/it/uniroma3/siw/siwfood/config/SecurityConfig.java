package it.uniroma3.siw.siwfood.config;

import it.uniroma3.siw.siwfood.service.SiwAuthenticationProvider;
import it.uniroma3.siw.siwfood.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserService userService;


    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) //per postman, disabilitare se uso angular !!!
                .authorizeHttpRequests(req->req
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        //.requestMatchers("/admin/**").hasAnyAuthority("admin")
                        //.requestMatchers("/staff/**").hasAnyAuthority("admin", "staff")
                        .anyRequest().authenticated())
                .userDetailsService(userService).build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new SiwAuthenticationProvider(userService);
    }

}
