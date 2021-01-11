package com.example.qurious.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler if authorization fails
 */
@Component
public class MyJwtExceptionHandler extends OncePerRequestFilter {

    /**
     * method to handle authentication errors
     *
     * @param request     req
     * @param response    res
     * @param filterChain next filter
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String, Object> data = new HashMap<>();
            data.put("timestamp", new Date());
            data.put("status", HttpStatus.FORBIDDEN.value());
            data.put("message", "Jwt Expired.");
            data.put("path", request.getRequestURL().toString());

            OutputStream out = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, data);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String, Object> data = new HashMap<>();
            data.put("timestamp", new Date());
            data.put("status", HttpStatus.FORBIDDEN.value());
            data.put("message", e.getMessage());
            data.put("path", request.getRequestURL().toString());

            OutputStream out = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, data);
            out.flush();
        }
    }

}
