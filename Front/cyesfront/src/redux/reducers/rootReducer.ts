// rootReducer.js
import { combineReducers } from '@reduxjs/toolkit';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // localStorage 사용

import oAuthReducer from './OAuthReducer';
import memberReducer from './MemberReducer';
import quizReducer from './QuizReducer';

// combineReducers를 사용하여 여러 리듀서를 하나로 통합
const rootReducer = combineReducers({
    oauth: oAuthReducer,
    member: memberReducer,
    quiz: quizReducer
});

// Redux Persist 설정
const persistConfig = {
    key: 'cyes',
    storage,
    whitelist: ['member'] // 지속적으로 저장할 리듀서 이름 목록
};

// persistedReducer 생성
const persistedReducer = persistReducer(persistConfig, rootReducer);

export default persistedReducer;
