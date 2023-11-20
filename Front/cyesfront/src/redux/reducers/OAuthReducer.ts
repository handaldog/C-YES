// OAuth state
interface OauthState {
    oAuthType: number;
}

// OAuth state 초기값
const initialOauthState: OauthState = {
    oAuthType: 0,
};

// OAuth 리듀서
const oAuthReducer = (state = initialOauthState, action: any) => {
    switch (action.type) {
        case "OAUTH_KAKAO":
            return { ...state, oAuthType: 0 };

        case "OAUTH_NAVER":
            return { ...state, oAuthType: 1 };

        default:
            return state;
    }
};

export default oAuthReducer;
