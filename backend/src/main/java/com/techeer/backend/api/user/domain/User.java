package com.techeer.backend.api.user.domain;


import com.techeer.backend.api.user.dto.request.UserRegisterRequest;
import com.techeer.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @Column(name = "password")
    private String password;

    @Column(name = "refresh_token")
    private String refreshToken;

//    @Column(name = "profile_image")
//    private String profileImage;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "role")
//    private Role role;

    private User(UserRegisterRequest req, String encodePW){
        this.email = req.getEmail();
        this.username = req.getUsername();
        this.password = encodePW;
        this.refreshToken = null;
    }

    public static User from(UserRegisterRequest req, String encodePW){
        return new User(req, encodePW);
    }
}