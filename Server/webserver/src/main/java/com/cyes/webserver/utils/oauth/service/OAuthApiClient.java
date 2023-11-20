package com.cyes.webserver.utils.oauth.service;


import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import com.cyes.webserver.utils.oauth.request.OAuthLoginParams;
import com.cyes.webserver.utils.oauth.response.OAuthInfoResponse;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
