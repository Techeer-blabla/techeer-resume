package com.techeer.backend.global.oauth;

import com.techeer.backend.api.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    // OAuth2User에서 반환하는 사용자 정보는 Map
    // 따라서 값 하나하나를 변환해야 한다.
//    public static OAuthAttributes of(String registrationId,
//                                     String userNameAttributeName,
//                                     Map<String, Object> attributes) {
//
//        return ofGoogle(userNameAttributeName, attributes);
//    }

    // 구글 생성자
    public static OAuthAttributes ofGoogle(String usernameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(usernameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    // User 엔티티 생성
    public User toEntity(OAuth2UserInfo oauth2UserInfo) {
        return User.builder()
                .email(oauth2UserInfo.getEmail())
                .build();
    }


}
