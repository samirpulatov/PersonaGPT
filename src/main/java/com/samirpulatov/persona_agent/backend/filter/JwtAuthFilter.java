package com.samirpulatov.persona_agent.backend.filter;

import com.samirpulatov.persona_agent.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public JwtAuthFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userEmail = null;

        // Check if the Authorization header is present and starts with "Bearer "
        if(authHeader !=null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Remove "Bearer " from the beginning
            userEmail = jwtService.getUserEmailFromToken(token);
        }

        // If the token is valid and the user is not yet authenticated, proceed with the filter chain
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.validateToken(token, userDetails)) {
                //This object gets stored in the security context and is used by Spring Security to authenticate the user
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                //add some custom data to the authentication token (not necessary but nice to have)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //set the authentication token in the security context so Spring Security knows the user is authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response); // Proceed with the filter chain
    }
}
