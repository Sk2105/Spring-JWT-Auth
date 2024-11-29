package com.sk.jwtauth.security;

import com.sk.jwtauth.services.UserServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtEntryPoint extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserServices userServices;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = request.getHeader("Authorization");
            if(jwt == null || !jwt.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = jwt.substring(7);

            if(token.isEmpty()) {
                throw new Exception("Token is empty");
            }

            System.out.println(jwtUtils.validateToken(token));

            if(jwtUtils.validateToken(token).isEmpty()) {
                throw new Exception("Invalid token");
            }

            String email = jwtUtils.validateToken(token);
            UserDetails user = userServices.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, user.getPassword(), user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            throw new ServletException(e);
        }

    }
}