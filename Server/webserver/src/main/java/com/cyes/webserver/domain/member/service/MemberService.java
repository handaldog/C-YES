package com.cyes.webserver.domain.member.service;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public String changeNickname(Long memberId, String changeNickname) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CustomExceptionList.MEMBER_NOT_FOUND_ERROR));

        member.setMemberNickname(changeNickname);

        return member.getMemberNickname();
    }


}
