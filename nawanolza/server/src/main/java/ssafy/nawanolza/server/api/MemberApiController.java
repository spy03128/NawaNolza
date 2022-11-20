package ssafy.nawanolza.server.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.nawanolza.server.domain.entity.Member;
import ssafy.nawanolza.server.domain.service.KakaoService;
import ssafy.nawanolza.server.domain.service.MemberService;
import ssafy.nawanolza.server.oauth.dto.KakaoProfile;
import ssafy.nawanolza.server.oauth.dto.OAuthToken;

@RestController
@AllArgsConstructor
public class MemberApiController {
    private final KakaoService kakaoService;
    private final MemberService memberService;

    @PostMapping("/auth/kakao/callback")
    public ResponseEntity<LoginResponse> login(@RequestBody OAuthToken oAuthToken){
        KakaoProfile kakaoProfile = kakaoService.userInfoRequest(oAuthToken); // 유저정보 가져오기

        Member kakaoUser = Member.builder()
                .name(kakaoProfile.getKakao_account().getProfile().getNickname())
                .email(kakaoProfile.getKakao_account().getEmail())
                .image(kakaoProfile.getKakao_account().getProfile().getProfile_image_url())
                .build();
        
        return ResponseEntity.ok(LoginResponse.of(memberService.getAccessToken(kakaoUser), memberService.getLoginMember(kakaoUser)));
    }

    @Getter
    @AllArgsConstructor
    public static class LoginResponse{
        private String Authorization;
        private Member member;

        public static LoginResponse of(String token, Member member) {
            return new LoginResponse(token, member);
        }
    }
}
