package com.example.qurious.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Json based web token filter to check for each request
 */
@Data
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${request.token.header}")
    private String tokenHeader;

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    /**
     * Check if jwt exists or not if yes then authenticate the request
     *
     * @param httpServletRequest  request
     * @param httpServletResponse response
     * @param filterChain         filter
     * @throws ServletException servlet related exceptions
     * @throws IOException      Input output related exceptions
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(httpServletRequest);
        if (Objects.nonNull(token)) {
            String userName = jwtProvider.validateTokenAndGetUserName(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    /**
     * Get token from request header
     *
     * @param req request
     * @return token in the header
     */
    public String getToken(HttpServletRequest req) {
        String token = req.getHeader(tokenHeader);
        if (Objects.nonNull(token)) {
            return token.replace("Bearer ", "").trim();
        }
        return null;
    }

}
