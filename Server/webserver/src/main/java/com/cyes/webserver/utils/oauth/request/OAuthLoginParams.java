package com.cyes.webserver.utils.oauth.request;

import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
