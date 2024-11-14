package com.techeer.backend.api.user.service;


import com.techeer.backend.api.user.converter.UserConverter;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.dto.request.SignUpRequest;
import com.techeer.backend.api.user.dto.request.UserTokenRequest;
import com.techeer.backend.api.user.dto.response.UserInfoResponse;
import com.techeer.backend.api.user.repository.UserRepository;
import com.techeer.backend.global.jwt.JwtToken;
import com.techeer.backend.global.jwt.service.JwtService;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void signup(SignUpRequest signUpReq) {
        User user = this.getLoginUser();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            user.updateUser(signUpReq);
            userRepository.save(user);
        }
    }

    public void logout() {
        User user = this.getLoginUser();
        user.onLogout();
        userRepository.save(user);
    }

    public User getLoginUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> loginUser = userRepository.findByEmail(userDetails.getUsername());
        return loginUser.get();
    }

    public JwtToken reissueToken(UserTokenRequest userTokenReq) {
        // Refresh Token 검증
        if (!jwtService.isTokenValid(userTokenReq.getRefreshToken())) {
            throw new RuntimeException("유효하지 않은 Refresh Token입니다.");
        }

        Object[] emailAndSocialType = jwtService.extractEmailAndSocialType(userTokenReq.getAccessToken());
        if (emailAndSocialType.length >= 1) {
            String email = (String) emailAndSocialType[0];

            User user = userRepository.findByEmail(email)
                    .orElseThrow();
            String refreshToken = user.getRefreshToken();

            // db에 리프레시 토큰 없을 경우(logout)
            if (refreshToken == null || !refreshToken.equals(userTokenReq.getRefreshToken())) {
                throw new RuntimeException("Refresh Token이 없습니다.");
            }

            String reissueAccessToken = jwtService.createAccessToken(email);
            String reIssueRefreshToken = jwtService.reIssueRefreshToken(user);

            return JwtToken.builder()
                    .accessToken(reissueAccessToken)
                    .refreshToken(reIssueRefreshToken)
                    .build();
        }

        throw new RuntimeException();
    }


    public UserInfoResponse getUserInfo() {

        User user = this.getLoginUser();
        return UserConverter.ofUserInfoResponse(user);
    }

}
