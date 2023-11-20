package com.cyes.webserver.utils.oauth.response;

import com.cyes.webserver.utils.jwt.AuthTokens;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "로그인 Response")
@Getter
@AllArgsConstructor
public class LoginResponse {

    @Schema(description = "accessToken과 RefreshToken을 담은 객체")
    AuthTokens tokens;
    @Schema(description = "로그인 한 멤버 ID")
    Long memberId;
    @Schema(description = "로그인 한 멤버 닉네임")
    String memberNickname;
    @Schema(description = "로그인 한 멤버 포인트")
    Integer memberPoint;
}
