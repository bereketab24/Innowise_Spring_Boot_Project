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

// Importing the FilterChain interface that allows us to pass control to the next filter or the endpoint in the chain.
import jakarta.servlet.FilterChain;

// Importing HttpServletRequest, which is the subclass of ServletRequest used specifically for handling HTTP requests (e.g., retrieving headers).
import jakarta.servlet.http.HttpServletRequest;

// Importing IOException, which is thrown when there are input/output errors during the request/response filtering process.
import java.io.IOException;

// Importing OncePerRequestFilter from Spring, which ensures that this filter is applied only once per request (a convenience class for this use case).
import org.springframework.web.filter.OncePerRequestFilter;

// Importing ServletException, which is thrown when there are issues with the servlet or filter processing.
import jakarta.servlet.ServletException;

// Importing NonNull annotation to indicate that a parameter, field, or method return value can never be null.
import org.springframework.lang.NonNull;

// Marking the class as a Spring component so Spring can manage it.
@Component
// Extending OncePerRequestFilter ensures our filter is applied once per
// request.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Injecting JwtUtil to utilize the JWT validation methods.
    private final JwtUtil jwtUtil;

    // Constructor to inject JwtUtil into this filter.
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil; // Assigning the injected JwtUtil instance to the local variable.
    }

    // The core method where we check for the token and validate it.
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull jakarta.servlet.http.HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Step 1: Extracting the JWT token from the Authorization header.
        // The Authorization header will look like: "Bearer <token>"
        String authorizationHeader = request.getHeader("Authorization");

        // Checking if the header is present and starts with "Bearer ".
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extracting the token (without "Bearer ")

            try {
                // Step 2: Validating the JWT token.
                // We call the validateToken method of JwtUtil, which will return the username
                // if the token is valid.
                String username = jwtUtil.validateToken(token);

                // Step 3: If the username is not null (i.e., token is valid), create the
                // authentication token.
                if (username != null) {
                    // Creating the UsernamePasswordAuthenticationToken with the username.
                    // In this case, we don't need to pass the password or roles as we're only
                    // verifying the user.
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, null);

                    // Setting the details of the request (can be used for auditing or logging).
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Step 4: Setting the authentication token in the Spring Security context.
                    // This tells Spring Security that the user is authenticated.
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        System.out.println("User is already authenticated");
                    }
                }
            } catch (Exception e) {
                // Step 5: If there's any error while validating the token, log the exception
                // (optional).
                System.out.println("JWT validation failed: " + e.getMessage());
            }
        }

        // Step 6: Continue processing the request chain. This is essential to allow the
        // request to proceed.
        // Without this line, the request would be stuck at this filter and never reach
        // the actual endpoint.
        filterChain.doFilter(request, response);
    }
}
