package com.example.SimpleCRUDAPI.filter;


// Importing the JwtUtil class to validate the JWT token using the method validateToken() from the JwtUtil utility class.
import com.example.SimpleCRUDAPI.util.JwtUtil;

// Importing Spring Security’s SecurityContextHolder to manage the security context (i.e., the authentication details).
import org.springframework.security.core.context.SecurityContextHolder; 

// Importing UsernamePasswordAuthenticationToken, which represents the authentication token in Spring Security (containing user details).
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

// Importing WebAuthenticationDetailsSource to populate additional details in the authentication token, such as the client's IP address or session info.
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

// Marking the class as a Spring-managed component to allow Spring to automatically detect and manage it.
import org.springframework.stereotype.Component;

// Importing the required classes for servlet-based filtering mechanisms.
import jakarta.servlet.Filter;

// Importing the FilterChain interface that allows us to pass control to the next filter or the endpoint in the chain.
import jakarta.servlet.FilterChain;

// Importing FilterConfig, which is used to configure the filter (optional).
import jakarta.servlet.FilterConfig; 

// Importing ServletRequest to represent the HTTP request that is passed to the filter.
import jakarta.servlet.ServletRequest; 

// Importing ServletResponse to represent the HTTP response that is passed to the filter.
import jakarta.servlet.ServletResponse;

// Importing HttpServletRequest, which is the subclass of ServletRequest used specifically for handling HTTP requests (e.g., retrieving headers).
import jakarta.servlet.http.HttpServletRequest;  

// Importing IOException, which is thrown when there are input/output errors during the request/response filtering process.
import java.io.IOException; 

// Importing OncePerRequestFilter from Spring, which ensures that this filter is applied only once per request (a convenience class for this use case).
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthenticationFilter {
    
}
