package kh.edu.cstad.mbapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthProvider;
    }

    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        //TODO
        //All request must be authenticated
        http.authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.GET, "api/v1/customers/**")
                .hasAnyRole("STAFF", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "api/v1/customers/**")
                .hasAnyRole( "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "api/v1/customers/**")
                .hasAnyRole( "ADMIN")
                .requestMatchers(HttpMethod.GET, "api/v1/customers/**")
                .hasAnyRole( "ADMIN", "STAFF","CUSTOMER")
                .requestMatchers("api/v1/accounts/**")
                .hasRole("USER")
                .anyRequest().authenticated()
        );

        //Disable From Login Default
        http.formLogin(form-> form.disable());
        http.csrf(token ->
                token.disable());

        //Set Security mechanism
        //Basic Authentication (username & password)
        http.httpBasic(Customizer.withDefaults());

        //Set session to Stateless
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

}
