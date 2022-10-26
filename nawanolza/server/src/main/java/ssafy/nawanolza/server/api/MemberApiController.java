package ssafy.nawanolza.server.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.nawanolza.server.oauth.dto.KakaoProfile;
import ssafy.nawanolza.server.oauth.dto.OAuthToken;
import ssafy.nawanolza.server.domain.entity.Member;
import ssafy.nawanolza.server.domain.service.KakaoService;
import ssafy.nawanolza.server.domain.service.MemberService;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
public class MemberApiController {
    private final KakaoService kakaoService;
    private final MemberService memberService;

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<AuthorizationResponse> getCode(HttpServletResponse response, @RequestParam String code){

        OAuthToken oAuthToken = kakaoService.tokenRequest(code); // 토큰 가져오기
        KakaoProfile kakaoProfile = kakaoService.userInfoRequest(oAuthToken); // 유저정보 가져오기

        Member kakaoUser = Member.builder()
                .name(kakaoProfile.getKakao_account().getProfile().getNickname())
                .email(kakaoProfile.getKakao_account().getEmail())
                .image(kakaoProfile.getKakao_account().getProfile().getProfile_image_url())
                .build();

        return ResponseEntity.ok(AuthorizationResponse.of(memberService.join(kakaoUser)));
    }

    @Getter
    @AllArgsConstructor
    public static class AuthorizationResponse{
        private String Authorization;

        public static AuthorizationResponse of(String token) {
            return new AuthorizationResponse(token);
        }
    }
}
