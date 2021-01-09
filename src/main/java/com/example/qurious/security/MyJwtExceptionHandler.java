package com.example.qurious.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Entry point to authentication
 */
@Component
public class MyJwtExceptionHandler implements AuthenticationEntryPoint {

    /**
     * method to handle authentication errors
     *
     * @param httpServletRequest  req
     * @param httpServletResponse response
     * @param e                   authentication error
     * @throws IOException input output exceptions
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
    }

}
