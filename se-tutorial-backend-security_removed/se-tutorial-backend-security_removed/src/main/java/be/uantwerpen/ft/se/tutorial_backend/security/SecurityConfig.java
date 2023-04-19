package be.uantwerpen.ft.se.tutorial_backend.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration //
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter filter;
    @Autowired
    private SEUserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
                    cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(List.of("*"));
                    return cors;
                }).and()
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/auth/**").permitAll();
                    requests.requestMatchers("/swagger-ui/**").permitAll();
                    requests.requestMatchers("/v3/api-docs/**").permitAll();
                    requests.requestMatchers("/error").anonymous(); // To allow error messages to be shown
                    requests.anyRequest().authenticated();
                })
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                ).and()
                .userDetailsService(userDetailsService)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() { // This is used to encode passwords
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
//@Configuration: This annotation indicates that this is a configuration class.
//@EnableWebSecurity: This annotation enables web security in the application.
//@EnableMethodSecurity: This annotation enables method security in the application.
//@Autowired: This annotation marks the filter and userDetailsService fields for dependency injection.
//@Bean: This annotation marks the filterchain, passwordEncoder, and authenticationManager methods for creating beans.
//In the filterchain method, we are configuring the security filter chain for HTTP requests. Here are the details:
//
//We disable CSRF protection because we are using JWT for authentication.
//We configure CORS to allow requests from specific origins.
//We configure which requests should be authorized and which should be anonymous.
//We configure the authentication entry point to return an "Unauthorized" response if authentication fails.
//We configure the session management to be stateless because we are using JWT for authentication.
//We add the JWTFilter to the filter chain before the UsernamePasswordAuthenticationFilter.
//In the passwordEncoder method, we create a BCryptPasswordEncoder object for password encoding.
//
//In the authenticationManager method, we get the AuthenticationManager object from Spring Security's AuthenticationConfiguration.