package com.techeer.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.backend.api.user.domain.Role;
import com.techeer.backend.api.user.domain.SocialType;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.dto.request.SignUpRequest;
import com.techeer.backend.api.user.repository.UserRepository;
import com.techeer.backend.global.jwt.JwtToken;
import com.techeer.backend.global.success.SuccessStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    private final String email = "test@gmail.com";
    private final String username = "testuser";
    private final String refreshToken = "refreshToken";
    private final Role role = Role.TECHEER;
    private final SocialType socialType = SocialType.GOOGLE;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // 테스트용 유저 데이터 삽입
        User user = new User(email, refreshToken, role, username, socialType);
        userRepository.save(user);
    }

    @Test
    @DisplayName("유저 정보 조회 테스트")
    @WithMockUser(username = username)
    void getUserInfo() throws Exception {
        mockMvc.perform(get("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SuccessStatus.USER_FETCH_OK.getCode()))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @DisplayName("추가정보 입력 테스트")
    void signupUser() throws Exception {
        SignUpRequest request = new SignUpRequest("testuser", Role.TECHEER);

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SuccessStatus.USER_ADDITIONAL_INFO_OK.getCode()));
    }

    @Test
    @DisplayName("로그아웃 테스트")
    void logoutUser() throws Exception {
        mockMvc.perform(post("/api/v1/logout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SuccessStatus.USER_LOGOUT_OK.getCode()));
    }

    @Test
    @DisplayName("액세스 토큰 재발급 테스트")
    void reGenerateAccessToken() throws Exception {
        JwtToken mockToken = new JwtToken("newAccessToken", "newRefreshToken");

        mockMvc.perform(post("/api/v1/reissue")
                        .header("Access-Token", "oldAccessToken")
                        .header("Refresh-Token", "oldRefreshToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Token", mockToken.getAccessToken()))
                .andExpect(header().string("Refresh-Token", mockToken.getRefreshToken()))
                .andExpect(jsonPath("$.status").value(SuccessStatus.TOKEN_REISSUE_OK.getCode()));
    }
}