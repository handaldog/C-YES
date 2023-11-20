package com.cyes.webserver.domain.member.entity;

import com.cyes.webserver.common.entity.BaseEntity;
import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate //update 할때 실제 값이 변경됨 컬럼으로만 update 쿼리를 만듬
@Entity //JPA Entity 임을 명시
@Getter //Lombok 어노테이션으로 getter
@Setter //Lombok 어노테이션으로 setter
@ToString
@Table(name = "member") //테이블 관련 설정 어노테이션
public class Member extends BaseEntity {// 생성일,수정일,삭제일 상속

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 자동 생성
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_email", nullable = false, unique = true)
    private String memberEmail;

    @Column(name = "member_nickname", nullable = false)
    private String memberNickname;

    @Column(name = "member_authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberAuthority memberAuthority;

    @Column(name = "member_point", nullable = true)
    @ColumnDefault("0")
    private Integer memberPoint;

    @Column(name = "oauth_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @Column(name = "nickname_initialized", nullable = true)
    @ColumnDefault("0")
    private Boolean nicknameInitialized;

}


