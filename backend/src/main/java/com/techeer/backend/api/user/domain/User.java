package com.techeer.backend.api.user.domain;


import com.techeer.backend.api.user.dto.request.SignUpRequest;
import com.techeer.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Table(name = "User")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "refresh_token")
    private String refreshToken;

//    @Column(name = "profile_image")
//    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Builder
    public User(String email, String refreshToken, Role role) {
        this.email = email;
        this.username = null;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public void updateUser(SignUpRequest req){
        this.username = req.getUsername();
        this.role = req.getRole();
    }

    public void onLogout() {
        this.refreshToken = null;
    }

    public void updateRefreshToken(String updateRefreshToken) { this.refreshToken = updateRefreshToken; }
}