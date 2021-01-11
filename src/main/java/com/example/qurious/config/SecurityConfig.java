package com.example.qurious.config;

import com.example.qurious.security.JwtFilter;
import com.example.qurious.security.MyJwtExceptionHandler;
import com.example.qurious.service.MyUserServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * This class overrides the default security configurations
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserServiceDetails userServiceDetails;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private MyJwtExceptionHandler jwtExceptionHandler;

    /**
     * The method disables cors and cross site request forgery
     * path starting with /api/v1/auth are allowed access without authentication
     * path starting with /api/v1/admin are only allowed with authentication and authorization
     * jwt filter is added which intercepts all the requests and checks if the token is valid or not
     *
     * @param http handler to modify the config
     * @throws Exception errors that occur which modifying the config
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**",
                        "/swagger-ui/index.html",
                        "/swagger-ui/*.js",
                        "/swagger-ui/*.css",
                        "/swagger-ui/*.json",
                        "/swagger-resources/configuration/**",
                        "/swagger-resources",
                        "/",
                        "/v2/api-docs").permitAll()
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/topic").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/topic/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionHandler, JwtFilter.class);
    }

    /**
     * Overriding the default userDetailsService to use DaoProvided userDetailsService with BCrypt as password encoder
     *
     * @param auth handler to modify the config
     * @throws Exception errors that occur which modifying the config
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetails).passwordEncoder(getPasswordEncoder());
    }

    /**
     * Create a bean of BCrypt encoder
     *
     * @return encoder
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Create a bean of authentication manager
     *
     * @return authentication manager
     * @throws Exception errors that occur which modifying the config
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
