package com.cyes.webserver.utils.oauth.service;


import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import com.cyes.webserver.utils.jwt.AuthTokens;
import com.cyes.webserver.utils.jwt.AuthTokensGenerator;
import com.cyes.webserver.utils.oauth.request.OAuthLoginParams;
import com.cyes.webserver.utils.oauth.request.RegenTokenReq;
import com.cyes.webserver.utils.oauth.response.LoginResponse;
import com.cyes.webserver.utils.oauth.response.OAuthInfoResponse;
import com.cyes.webserver.utils.oauth.response.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.cyes.webserver.redis.KeyGenerator.REFRESH_TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final StringRedisTemplate stringRedisTemplate;

    public LoginResponse login(OAuthLoginParams params) {

        // API 서버로부터 멤버 정보를 받아옴
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        
        // 멤버 정보를 바탕으로 신규 멤버는 회원가입을, 기존 멤버는 로그인을 실행
        Member member = findOrCreateMember(oAuthInfoResponse);
        memberRepository.flush();

        // 로그인 한 멤버 ID를 바탕으로 accessToken과 refreshToken 생성
        AuthTokens token = authTokensGenerator.generate(member.getMemberId());

        // redis에 refreshToken 저장
//        member.setRefreshToken(token.getRefreshToken());


        return new LoginResponse(token, member.getMemberId(), member.getMemberNickname(), member.getMemberPoint());
    }

    public Long logout(Long memberId) {

        return memberId;
    }

    private Member findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByMemberEmail(oAuthInfoResponse
                        .getEmail())
                        .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Member newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .memberEmail(oAuthInfoResponse.getEmail())
                .memberNickname(oAuthInfoResponse.getNickname())
                .memberAuthority(MemberAuthority.USER)
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        memberRepository.save(member);

        return member;
    }

    public RefreshTokenResponse regenToken(RegenTokenReq regenTokenReq) {

        String refreshToken = stringRedisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX+regenTokenReq.getMemberId());

        if (authTokensGenerator.verifyToken(regenTokenReq.getRefreshToken()) &&
                regenTokenReq.getRefreshToken().equals(refreshToken)) {

            AuthTokens token = authTokensGenerator.generate(regenTokenReq.getMemberId());

            return new RefreshTokenResponse(token, regenTokenReq.getMemberId());

        } else {
            throw new CustomException(CustomExceptionList.REFRESH_TOKEN_ERROR);
        }
    }
}