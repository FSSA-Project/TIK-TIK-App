package com.fssa.todo.config;

<<<<<<< HEAD
import com.fssa.todo.jwtutil.jwtService;
=======
>>>>>>> d6f374cc3024ef6050749c8b4c81bbc13c2eaf7f
import com.fssa.todo.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
<<<<<<< HEAD
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
=======
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
>>>>>>> d6f374cc3024ef6050749c8b4c81bbc13c2eaf7f
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

<<<<<<< HEAD
=======
import com.fssa.todo.jwtutil.jwtService;

>>>>>>> d6f374cc3024ef6050749c8b4c81bbc13c2eaf7f
/**
 * This is for filter chain that is
 * maintain the bearer token check all
 * the apis for auth
 */
<<<<<<< HEAD

@Component
=======
>>>>>>> d6f374cc3024ef6050749c8b4c81bbc13c2eaf7f
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private jwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

<<<<<<< HEAD
        // if auth header will be null or not code with bearer

=======
>>>>>>> d6f374cc3024ef6050749c8b4c81bbc13c2eaf7f
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtService.extractEmail(token);

        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(email);

            if (jwtService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}
