package com.techeer.backend.global.jwt;

import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.repository.UserRepository;
import com.techeer.backend.global.error.ErrorStatus;
import com.techeer.backend.global.error.exception.BusinessException;
import com.techeer.backend.global.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        Optional<String> accessToken = jwtService.extractAccessTokenFromCookie(request);

        if (accessToken.isPresent()) {

            // Access Token 유효성 검사
            if (jwtService.isTokenValid(accessToken.get())) {
                Object[] emailAndSocialType = jwtService.extractEmailAndSocialType(accessToken.get());

                // todo 구글/github에 따라서 email로 할지 name으로 할지 결정
                if (Arrays.stream(emailAndSocialType).isParallel()) {
                    String email = (String) emailAndSocialType[0];
                    log.info("이메일이 추출되었습니다: {}", email);

                    // 사용자 정보 조회
                    userRepository.findByEmail(email).ifPresent(user -> {
                        log.info("사용자 정보가 발견되었습니다: {}", user);
                        saveAuthentication(user);
                        log.info("사용자 인증 정보가 저장되었습니다: {}", user);
                    });
                }

            } else {
                log.warn("이메일이 존재 하지 않습니다.");
                throw new BusinessException(ErrorStatus.INVALID_ACCESS_TOKEN);
            }
        }
        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(User user) {
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password("Google")
                .roles(user.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

