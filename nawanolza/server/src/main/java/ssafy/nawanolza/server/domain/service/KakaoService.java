package ssafy.nawanolza.server.domain.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ssafy.nawanolza.server.domain.entity.dto.member.KakaoProfile;
import ssafy.nawanolza.server.domain.entity.dto.member.OAuthToken;

@Service
public class KakaoService {

    public OAuthToken tokenRequest(String code) {

        //POST방식으로 데이터 요청
        RestTemplate restTemplate = new RestTemplate();

        //HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "79a60227e8d4d465f7b303d472b3cd99");
        body.add("redirect_uri", "http://localhost:8080/api/auth/kakao/callback");
        body.add("code", code);

        //HttpHeader와 HttpBody 담기기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers); // params : body

        return restTemplate.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, OAuthToken.class).getBody();
    }

    public KakaoProfile userInfoRequest(OAuthToken oAuthToken) {

        ///유저정보 요청
        RestTemplate restTemplate = new RestTemplate();

        //HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody 담기기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        return restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, KakaoProfile.class).getBody();
    }
}
