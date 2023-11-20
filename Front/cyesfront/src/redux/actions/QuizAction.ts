import { Quiz } from "../ReduxStateInterface";

export const saveQuiz = (quiz: Quiz) => {
    return {
        type: "QUIZ_SAVE",
        payload: {
            ...quiz,
        },
    };
};
