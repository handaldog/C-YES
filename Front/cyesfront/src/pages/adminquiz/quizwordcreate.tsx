import React, { useState } from "react";
import "./quizwordcreate.css";
import axios from "axios"; // Axios 라이브러리 import

const QuizWordCrate = () => {
  const [inputValue, setInputValue] = useState("");

  const noCheckProblemWord = () => {
    // Axios를 사용하여 API 호출 수행
    axios
      .get(`https://cyes.site/api/adminproblem/create/${inputValue}`)
      .then((response) => {
        console.log("API 응답 데이터: ", response.data);
        // 원하는 작업 수행
      })
      .catch((error) => {
        console.error("API 호출 중 오류 발생: ", error);
      });
  };

  return (
    <div className="login-container">
      <div className="adminquizcreate">
        관리자님 단어를 입력해 주세요.
        <br></br>
        <br></br>
        <input
          type="text"
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          placeholder="단어 입력하세요."
          className="input-field"
        />
        <br></br>
        <button
          onClick={noCheckProblemWord}
          className="no-check-problem-word-button"
        >
          단어 보내기
        </button>
      </div>
    </div>
  );
};

export default QuizWordCrate;
