package com.techeer.backend.api.user.controller;

import com.techeer.backend.api.user.dto.request.LoginRequest;
import com.techeer.backend.api.user.dto.request.UserRegisterRequest;
import com.techeer.backend.api.user.dto.response.LoginResponse;
import com.techeer.backend.api.user.service.JwtTokenUtil;
import com.techeer.backend.api.user.service.UserService;
import com.techeer.backend.global.success.SuccessCode;
import com.techeer.backend.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    // todo String -> successResponse 로 변경
    @Operation(summary = "회원가입")
    @PostMapping(value= "/register")//, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest req) {

        if (userService.userExists(req.getEmail())) {
            return new ResponseEntity<>("email already exists", HttpStatus.BAD_REQUEST); // 중복 검사
        }

        userService.register(req); // 사용자 등록
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED); // 등록 성공 응답
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse> login(@RequestBody LoginRequest req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );

            String accessToken = jwtTokenUtil.generateAccessToken(req.getEmail());
            String refreshToken = jwtTokenUtil.generateRefreshToken(req.getEmail());
            SuccessResponse response = SuccessResponse.of(SuccessCode.SUCCESS, new LoginResponse(accessToken,refreshToken));
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            // 인증 실패 시 예외 처리
            if (e instanceof BadCredentialsException) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 잘못되었습니다.");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "로그인 중 오류가 발생했습니다.");
            }
        }
    }


}
