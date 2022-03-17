package com.jocoos.spring.config.security;

import com.jocoos.spring.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    String HEADER_STRING = "Authorization";
    String TOKEN_TYPE = "Bearer ";

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_TYPE)) {
            authToken = header.replace(TOKEN_TYPE, "");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                log.error("an error during getting username form token");
            } catch (ExpiredJwtException e) {
                log.warn("the token is expired and not valid anymore", e);
            } catch(SignatureException e){
                log.error("Authentication Failed. Username or Password not valid.");
            }
        }

        UserDetails UserFound = customUserDetailsService.loadUserByUsername(username);
        // 인증된 사용자 정보(principal), 패스워드(cridential), principal 권한 검증
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(UserFound.getUsername(), null, null);

        // 성공하면 토큰 검증 (ThreadLocal? SecurityContext? ) Todo : 0317 학습하기
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);

    }
}
