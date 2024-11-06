package com.techeer.backend.api.user.controller;

import com.techeer.backend.api.user.dto.request.SignUpRequest;
import com.techeer.backend.api.user.dto.request.UserTokenRequest;
import com.techeer.backend.api.user.dto.response.UserInfoResponse;
import com.techeer.backend.api.user.service.UserService;
import com.techeer.backend.global.common.response.CommonResponse;
import com.techeer.backend.global.jwt.JwtToken;
import com.techeer.backend.global.success.SuccessResponse;
import com.techeer.backend.global.success.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Operation(summary = "유저 정보")
    @GetMapping("/user")
    public CommonResponse<UserInfoResponse> getUserInfo() {
        UserInfoResponse result = userService.getUserInfo();

        return CommonResponse.of(SuccessStatus.USER_FETCH_OK, result);
    }

    @Operation(summary = "추가정보 입력")
    @PostMapping("/users")
    public ResponseEntity<SuccessResponse> signupUser(@RequestBody @Valid SignUpRequest req) {
        userService.signup(req);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse> logoutUser() {
        userService.logout();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "액세스 토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<String> reGenerateAccessToken(@RequestBody @Valid UserTokenRequest userTokenReq) {
        JwtToken newToken = userService.reissueToken(userTokenReq);
        return ResponseEntity.ok(newToken.toString());
    }

}
