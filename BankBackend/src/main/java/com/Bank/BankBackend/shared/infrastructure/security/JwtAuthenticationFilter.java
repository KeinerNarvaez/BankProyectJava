package com.Bank.BankBackend.shared.infrastructure.security;

import com.Bank.BankBackend.modules.user.application.port.out.TokenGeneratorPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX        = "Bearer ";

    private final TokenGeneratorPort tokenGenerator;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/actuator/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);

        if (StringUtils.hasText(token)
                && tokenGenerator.isTokenValid(token)) {

            tokenGenerator.extractUserId(token).ifPresent(userId -> {

                String role =
                        tokenGenerator.extractRole(token)
                                .orElse("");

                SecurityUser securityUser =
                        new SecurityUser(userId, role);

                log.info(
                        "JWT -> userId={}, role={}, authorities={}",
                        userId,
                        role,
                        securityUser.getAuthorities()
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                securityUser,
                                null,
                                securityUser.getAuthorities()
                        );

                log.info(
                        "AUTHENTICATION -> authenticated={}, authorities={}",
                        authentication.isAuthenticated(),
                        authentication.getAuthorities()
                );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                log.info(
                        "SECURITY CONTEXT -> authentication={}",
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                );
            });
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }

}