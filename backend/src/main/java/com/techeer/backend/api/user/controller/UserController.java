package com.techeer.backend.api.user.controller;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.dto.request.SignUpRequest;
import com.techeer.backend.api.user.dto.request.UserRegisterRequest;
import com.techeer.backend.api.user.dto.request.UserTokenRequest;
import com.techeer.backend.api.user.service.UserService;
import com.techeer.backend.global.jwt.JwtToken;
import com.techeer.backend.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Operation(summary = "추가정보 입력")
    @PostMapping("/users")
    public ResponseEntity<SuccessResponse> signupUser(@RequestBody @Valid SignUpRequest signUpReq) {
        userService.signup(signUpReq);
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
