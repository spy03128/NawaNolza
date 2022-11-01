package ssafy.nawanolza.server.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.nawanolza.server.domain.exception.MemberNotFountException;
import ssafy.nawanolza.server.oauth.jwt.JwtTokenProvider;
import ssafy.nawanolza.server.domain.repository.MemberRepository;
import ssafy.nawanolza.server.domain.entity.Member;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Member getLoginMember(Member member){
        if(!kakaoValidateDuplicateUser(member).isPresent()){
            memberRepository.save(member);
        }

        return memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new MemberNotFountException(member.getEmail()));
    }

    public String getAccessToken(Member member){
        return jwtTokenProvider.createToken(member.getId());
    }

    public Optional<Member> kakaoValidateDuplicateUser(Member member){
        return memberRepository.findByEmail(member.getEmail());
    }
}
