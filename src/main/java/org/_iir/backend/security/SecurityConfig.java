package org._iir.backend.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtFilter jwtFilter;
        private final CustomUserDetailsService userDetailsService;
        private final AuthEntryPointJwt unauthorizedHandler;

        // Roles
        private final static String ADMIN = "ADMIN";
        private final static String PRESTATAIRE = "PRESTATAIRE";
        private final static String DEMANDEUR = "DEMANDEUR";

        // Endpoints
        private final static String[] PUBLIC_ENDPOINTS = {
                        "/api/auth/**",
                        "/api/auth/registration/verify",
                        "/api/debug/**",
        };

        private final static String[] USERS_ENDPOINTS = {
                        "/api/users/**"
        };

        private final static String[] ACCOUNT_ENDPOINTS = {
                        "/api/account/**"
        };

        // private final static String[] OFFRE_ORDER_ENDPOINTS = {
        // "/api/order/offer/**"
        // };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                                .sessionManagement(
                                                sessionManagement -> sessionManagement
                                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                // Handle unauthorized requests
                http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

                // Authorizations
                http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                                .requestMatchers(USERS_ENDPOINTS).hasAuthority(ADMIN)
                                // .requestMatchers(OFFRE_ORDER_ENDPOINTS).authenticated()
                                // .hasAnyAuthority(ADMIN, DEMANDEUR)

                                // Proposition Endpoints
                                .requestMatchers(HttpMethod.POST, "/api/proposition").hasAuthority(PRESTATAIRE)
                                .requestMatchers(HttpMethod.DELETE, "/api/proposition/{id}").hasAuthority(PRESTATAIRE)
                                .requestMatchers(HttpMethod.GET, "/api/proposition/{id}").hasAuthority(PRESTATAIRE)

                                // Order Offre Endpoints
                                .requestMatchers(HttpMethod.POST, "/api/order/offer").hasAuthority(DEMANDEUR)
                                .requestMatchers(HttpMethod.GET, "/api/order/offer/{id}").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/api/order/offer").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/api/order/offer/list").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/api/order/offer/user")
                                .hasAuthority(DEMANDEUR)
                                // .hasAnyAuthority(PRESTATAIRE, DEMANDEUR)
                                .requestMatchers(HttpMethod.PATCH, "/api/order/offer/confirm/{id}")
                                .hasAuthority(PRESTATAIRE)
                                .requestMatchers(HttpMethod.PATCH, "/api/order/offer/cancel/{id}")
                                .hasAuthority(PRESTATAIRE)
                                .requestMatchers(HttpMethod.DELETE, "/api/order/offer/{id}").hasAuthority(ADMIN)

                                // Order Demande Endpoints
                                .requestMatchers(HttpMethod.POST, "/api/order/demande").hasAuthority(DEMANDEUR)
                                .requestMatchers(HttpMethod.GET, "/api/order/demande/{id}").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/api/order/demande").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/api/order/demande/list").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/api/order/demande/{id}").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.PATCH, "/api/order/demande/{id}/confirm")
                                .hasAuthority(PRESTATAIRE)
                                .requestMatchers(HttpMethod.PATCH, "/api/order/demande/{id}/cancel")
                                .hasAnyAuthority(PRESTATAIRE, DEMANDEUR)
                                .requestMatchers(ACCOUNT_ENDPOINTS).authenticated()
                                .anyRequest().authenticated());
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                return new CustomAuthProvider(userDetailsService, passwordEncoder());
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

                return config.getAuthenticationManager();
        }
}
