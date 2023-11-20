import { AxiosInstance } from "axios";

const setAxiosInterceptor = (httpRequest: AxiosInstance) => {
    // TODO : Request Interceptor 설정
    // 토큰 확인, 탑재 및 리프레쉬 요청
    httpRequest.interceptors.request.use();
    //TODO : Response Interceptor 설정
    // 에러 처리 등
    httpRequest.interceptors.response.use();
};

// async function refreshToken() {}

// 예전 코드
// async function refreshToken() {
//     console.log("토큰 재발급 요청")
//     const refreshToken = window.localStorage.getItem("refreshToken");
//     const userId = window.localStorage.getItem("userId");

//     await loginApie
//       .post("/api/oauth/regen",{
//         userId,
//         refreshToken,
//       })
//       .then((res) => {
//         console.log("토큰 재발급됨!!")
//         const tokens = res.data.data.tokens;
//         window.localStorage.setItem("accessToken", tokens.accessToken);
//         window.localStorage.setItem("refreshToken", tokens.refreshToken);
//         const userId = res.data.data.userId;
//         window.localStorage.setItem("userId", userId);
//       })
//       .catch((err) => {
//         console.log("재발급 실패!!!")
//         console.log(err);
//       });
// }

export default setAxiosInterceptor;
