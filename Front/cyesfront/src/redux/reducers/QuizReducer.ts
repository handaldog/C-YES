import { Quiz } from "../ReduxStateInterface";

interface QuizState {
    quiz: Quiz;
}

const initialQuizState: QuizState = {
    quiz: {
        quizId: -1,
        quizTitle: "",
        quizStartDate: new Date().toISOString(),
    },
};

const quizReducer = (state = initialQuizState, action: any) => {
    switch (action.type) {
        case "QUIZ_SAVE":
            return {
                ...state,
                quiz: { ...action.payload },
            };

        default:
            return state;
    }
};

export default quizReducer;
