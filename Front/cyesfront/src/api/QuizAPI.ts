import axios from "axios";
import setAxiosInterceptor from "./ApiConfig";

const quizApi = axios.create({
    baseURL: process.env.REACT_APP_SPRING_URI,
    headers: { "content-type": "application/json" },
    timeout: 3000,
});

export const getMainQuizInfo = async () => {
    const data = await quizApi
        .get(`/api/quiz/live/info`)
        .then((resp) => {
            return resp.data;
        })
        .catch((err) => {
            console.log(err); 
            return null;
        });

    return data;
};
