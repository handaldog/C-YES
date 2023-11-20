package com.cyes.webserver.utils.oauth.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegenTokenReq {

    Long memberId;
    String refreshToken;

}