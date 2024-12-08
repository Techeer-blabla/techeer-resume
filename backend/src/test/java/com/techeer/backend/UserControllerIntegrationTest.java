package com.techeer.backend;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.backend.api.user.domain.Role;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.dto.request.SignUpRequest;
import com.techeer.backend.api.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("유저 정보 조회 테스트")
    @WithMockUser(username = "test@example.com", roles = {"REGULAR"})
    void getUserInfoTest() throws Exception {
        // given
        User mockUser = User.builder()
                .email("test@example.com")
                .username("Test User")
                .build();
        when(userService.getLoginUser()).thenReturn(mockUser);
        // when & then
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 추가 정보 입력 테스트")
    @WithMockUser(username = "test@example.com", roles = {"REGULAR"})
    void signupUserTest() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest("name", Role.REGULAR);

        String content = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 로그아웃 테스트")
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    void logoutUserTest() throws Exception {
        // when & then
        mockMvc.perform(post("/api/v1/logout"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("액세스 토큰 재발급 테스트")
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    void reGenerateAccessTokenTest() throws Exception {
        // given
        String accessToken = "dummyAccessToken";
        String refreshToken = "dummyRefreshToken";

        // when & then
        mockMvc.perform(post("/api/v1/reissue")
                        .header("Access-Token", accessToken)
                        .header("Refresh-Token", refreshToken))
                .andExpect(status().isOk());
    }
}
