import axios from "axios";
import setAxiosInterceptor from "./ApiConfig";

const loginApi = axios.create({
    baseURL: process.env.REACT_APP_SPRING_URI,
    headers: { "content-type": "application/json" },
    timeout: 3000,
});

// TODO: API 요청 시 토큰 넣어서 보내야함

export const requestKakaoLoginInfo = async (
    authorizationCode: string | null
) => {
    const data = await loginApi
        .post(`/api/oauth/login/kakao`, {
            authorizationCode,
        })
        .then((resp) => {
            return resp.data;
        })
        .catch((err) => {
            console.log(err);
        });

    return data;
};

// export const exchangeCodeForToken = async (code: string) => {
//     const response = await axios.get(`${BASE_URL}/oauth/token?code=${code}`);
//     return response.headers.authorization;
// };

// export const fetchUserProfile = async (token: string) => {
//     const response = await axios.get(`${BASE_URL}/user/mypage`, {
//         headers: {
//             Authorization: token,
//             request: token,
//         },
//     });
//     return response.data;
// };
