// 카카오 로그인 액션
export const kakaoLogin = () => {
    return {
        type: "OAUTH_KAKAO",
    };
};

// 네이버 로그인 액션
export const naverLogin = () => {
    return {
        type: "OAUTH_NAVER",
    };
};
