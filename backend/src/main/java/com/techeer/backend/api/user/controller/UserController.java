package com.techeer.backend.api.user.controller;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.dto.request.UserRegisterRequest;
import com.techeer.backend.api.user.service.UserService;
import com.techeer.backend.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

//    // todo String -> successResponse 로 변경
//    @Operation(summary = "회원가입")
//    @PostMapping(value= "/register")//, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> register(@RequestBody UserRegisterRequest req) {
//
//        if (userService.userExists(req.getEmail())) {
//            return new ResponseEntity<>("email already exists", HttpStatus.BAD_REQUEST); // 중복 검사
//        }
//
//        userService.register(req); // 사용자 등록
//        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED); // 등록 성공 응답
//    }
}
