package com.fssa.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class have all api's authorization
 * and auth for the users
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * {@link AuthenticationProvider} is connect to the DB
     * and check the user with their name and return the object
     *
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        // This will interact with the database spring inbuild security
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService); // Set the userdetails like an object
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // set the rounds as 12

        return provider;
    }


    /**
     * This is for filter chain and maintain the
     * security for the api's
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(Customizer -> Customizer.disable()) // Disable the csrf token for browser
                .authorizeHttpRequests(Request -> Request
                        .requestMatchers("/api/v1/user/register", "/api/v1/user/login")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(Session -> Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // This means i dont want to maintain  the session

        return httpSecurity.build();
    }


    /**
     * This method is for authenticateManager for
     * check the auth for JWT and return the user
     * obj
     *
     * @param config
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Method for return the userDetails Service
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        // Create a sample user for sign in the browser like hardcord
//        UserDetails userDetails = User
//                .withDefaultPasswordEncoder()
//                .username("dinesh")
//                .password("d@123")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(userDetails); // Return the userDetails object for auth check
//    }


}
