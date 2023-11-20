package com.cyes.webserver.domain.member.service;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("Member의 NickName을 변경할 수 있다.")
    @Test
    void changeNickname(){
        //given
        Member member = Member.builder().memberEmail("email1@123.123").memberNickname("nickname1").memberAuthority(MemberAuthority.USER).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();
        memberRepository.save(member);
        String changeNickName = "nickname2";

        //when
        String nickname2 = memberService.changeNickname(member.getMemberId(), changeNickName);

        //then
        assertThat(nickname2).isEqualTo(changeNickName);
    }
}