import axios from "axios";
import setAxiosInterceptor from "./ApiConfig";

const groupQuizApi = axios.create({
  baseURL: process.env.REACT_APP_SPRING_URI,
  headers: { "content-type": "application/json" },
  timeout: 3000,
});

interface Question {
  question: string;
  answer: string;
  choices: string[];
  category: string;
  type: string;
}

interface QuestionContent {
  memberId: number;
  quizTitle: string;
  startDateTime: string;
  problemByUserList: Question[];
}

  
export const createGroupQuiz = async (Quiz : QuestionContent | null) => {
    const data = await groupQuizApi
        .post(`/api/quiz/create/user`,Quiz)
        .then((resp) => {
            return resp.data;
        })
        .catch((err) => {
            console.log("api에러 !! : ",err); 
            return null;
        });
        return data;
      }