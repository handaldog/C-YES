package com.cyes.webserver.domain.member.repository;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("email로 member를 조회할 수 있다.")
    void findByMemberEmail() {
        // given
        saveMember();
        // when
        Optional<Member> email1 = memberRepository.findByMemberEmail("email1@123.123");
        Optional<Member> email2 = memberRepository.findByMemberEmail("email2@123.123");

        // then
        assertThat(email1).isNotEmpty();
        assertThat(email1.get()).extracting(
                member -> member.getMemberNickname()
                , member -> member.getMemberEmail()
        ).contains("nickname1", "email1@123.123");

        assertThat(email2).isNotEmpty();
        assertThat(email2.get()).extracting(
                member -> member.getMemberNickname()
                , member -> member.getMemberEmail()
        ).contains("nickname1", "email2@123.123");
    }
    @Test
    @DisplayName("memberid 와 nickname으로 멤버를 조회할 수 있다.")
    void findByMemberIdAndMemberNickname() {
        // given
        Member member1 = Member.builder().memberEmail("email1@123.123").memberNickname("nickname1").memberAuthority(MemberAuthority.USER).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();
        Member member2 = Member.builder().memberEmail("email2@123.123").memberNickname("nickname1").memberAuthority(MemberAuthority.USER).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();

        memberRepository.save(member1);
        memberRepository.save(member2);


        // when
        Optional<Member> byMemberIdAndMemberNickname = memberRepository.findByMemberIdAndMemberNickname(member1.getMemberId(), member1.getMemberNickname());

        // then
        assertThat(byMemberIdAndMemberNickname).isNotEmpty();
        assertThat(byMemberIdAndMemberNickname.get())
                .extracting(member -> member.getMemberEmail())
                .isEqualTo("email1@123.123");
    }
    private List<Member> saveMember(){
        Member member1 = Member.builder().memberEmail("email1@123.123").memberNickname("nickname1").memberAuthority(MemberAuthority.USER).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();
        Member member2 = Member.builder().memberEmail("email2@123.123").memberNickname("nickname1").memberAuthority(MemberAuthority.USER).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        return List.of(member1, member2);
    }

}