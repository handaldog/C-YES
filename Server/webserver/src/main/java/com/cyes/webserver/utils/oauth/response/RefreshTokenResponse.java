package com.cyes.webserver.utils.oauth.response;

import com.cyes.webserver.utils.jwt.AuthTokens;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "JWT Token Response")
@Getter
@AllArgsConstructor
public class RefreshTokenResponse {

    @Schema(description = "accessToken과 RefreshToken을 담은 객체")
    AuthTokens tokens;
    @Schema(description = "멤버 ID")
    Long memberId;
}
