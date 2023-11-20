import axios from "axios";

// axios 인스턴스를 생성합니다.
const groupApi = axios.create({
  baseURL: process.env.REACT_APP_SPRING_URI,
  headers: { "Content-Type": "application/json;charset=UTF-8" },
  timeout: 3000,
});

// 문제를 가져오는 새로운 함수를 정의합니다.
export const getGroupQuiz = async (): Promise<any> => {
  try {
    const response = await groupApi.get("/api/quiz/group/info");
    console.log("all group response", response);
    return response.data;
  } catch (err) {
    console.error("getGroupQuiz error: ", err);
    return null;
  }
};

// 특정 타이틀에 해당하는 그룹 퀴즈 정보를 검색하는 함수 (POST 요청으로 변경)
export const getGroupQuizUseTitle = async (keyword: string): Promise<any> => {
  try {
    // POST 요청으로 변경하고, 요청 본문에 keyword를 포함시킴
    const response = await groupApi.post("/api/quiz/group/info/searchByTitle", {
      keyword,
    });
    console.log("keyword response", response);
    return response.data;
  } catch (err) {
    console.error("getTitleGroupQuiz error: ", err);
    return null;
  }
};
