package com.cyes.webserver.domain.member.repository;

import com.cyes.webserver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberEmail(String email);

    Optional<Member> findByMemberIdAndMemberNickname(Long memberId, String nickname);
}
