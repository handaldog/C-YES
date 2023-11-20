package com.cyes.webserver.utils.oauth.response;


import com.cyes.webserver.utils.oauth.enums.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}
