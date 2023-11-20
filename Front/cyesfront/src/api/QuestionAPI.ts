import axios from "axios";

// axios 인스턴스를 생성합니다.
const questionApi = axios.create({
  baseURL: process.env.REACT_APP_SPRING_URI,
  headers: { "Content-Type": "application/json;charset=UTF-8" },
  timeout: 3000,
});

// 문제를 가져오는 새로운 함수를 정의합니다.
export const getProblems = async (
  category: string,
  type: string,
  page: number,
  size: number,
  sort: string
): Promise<any> => {
  try {
    const response = await questionApi.get("/api/problem", {
      params: {
        problemCategory: category,
        problemType: type,
        page: page,
        size: size,
        sort: sort,
      },
    });
    console.log("response", response);
    return response.data;
  } catch (err) {
    console.error("getProblems error: ", err);
    return null;
  }
};
