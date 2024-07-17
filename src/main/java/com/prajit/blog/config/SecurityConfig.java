package com.prajit.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.prajit.blog.security.JWTAuthenticationFilter;
import com.prajit.blog.security.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SecurityConfig {
	
	public static final String[]PUBLIC_URLS= {"/auth/login","/v3/api-docs","/swagger-resources/**","/swagger-ui/**","/webjars/**"};
	
	@Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JWTAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	 http.csrf(csrf -> csrf.disable())
         .cors(cor -> cor.disable())
         .authorizeHttpRequests(auth ->
                 auth
                 .requestMatchers("/home/**").authenticated()
                 .requestMatchers(PUBLIC_URLS).permitAll()
                 .requestMatchers(HttpMethod.GET).permitAll()
                 .anyRequest().authenticated()
         )
         .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    	 http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    	 return http.build();
    }
    
    
}
