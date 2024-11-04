package com.techeer.backend.api.user.controller;
import com.techeer.backend.api.user.dto.request.SignUpRequest;
import com.techeer.backend.api.user.dto.request.UserTokenRequest;
import com.techeer.backend.api.user.service.UserService;
import com.techeer.backend.global.common.response.CommonResponse;
import com.techeer.backend.global.jwt.JwtToken;
import com.techeer.backend.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "구글 로그인")
    @PostMapping("/auth/google")
    public CommonResponse<String> login(){

        // 구글 소셜 로그인 로직 시작

        // 1. 프론트엔드에서 구글 로그인 URL로 리디렉션

        // 2. 구글 로그인 성공 후 authorization code를 받아서 서버로 전달

        // 3. 서버에서 authorization code를 통해 access token 발급

        // 4. 발급받은 access token을 사용해 사용자 정보를 가져옴

        // 예시 응답 (실제 사용 시에는 토큰 또는 사용자 정보를 반환)
        String accessToken = "google_access_token_example";

        return null;
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
