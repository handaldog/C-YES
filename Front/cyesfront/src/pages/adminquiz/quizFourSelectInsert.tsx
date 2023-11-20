import React, { useState } from "react";
import "./quizwordcreate.css";
import axios from "axios"; // Axios 라이브러리 import
const QuizWordCrate = () => {
  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");
  const [category, setCategory] = useState("");
  const [choice1, setChoice1] = useState("");
  const [choice2, setChoice2] = useState("");
  const [choice3, setChoice3] = useState("");
  const [choice4, setChoice4] = useState("");
  const [description, setDescription] = useState("");

  const multipleChoiceProblemSaveRequest = {
    question: question,
    answer: answer,
    choices: [choice1, choice2, choice3, choice4],
    problemCategory: category,
    description: description,
  };

  const noCheckProblemWord = () => {
    // Axios를 사용하여 API 호출 수행
    axios
      .post(
        `https://cyes.site/api/adminproblem/yes-four-select-insert`,
        multipleChoiceProblemSaveRequest
      )
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
      <div className="fourquiz">
        4지선다 생성
        <br></br>
        <br></br>
        <textarea
          // type="text"
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="문제를 입력하세요."
          className="text-field"
          rows={8}
          cols={40}
        />
        <br></br>
        <textarea
          // type="text"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          placeholder="설명을 입력하세요."
          className="text-field"
          rows={8}
          cols={40}
        />
        <br></br>
        <input
          type="text"
          value={answer}
          onChange={(e) => setAnswer(e.target.value)}
          placeholder="정답1을 입력하세요."
          className="input-field"
        />
        <br></br>
        <input
          type="text"
          value={choice4}
          onChange={(e) => setChoice4(e.target.value)}
          placeholder="정답2을 입력하세요."
          className="input-field"
        />
        <br></br>
        <input
          type="text"
          value={choice1}
          onChange={(e) => setChoice1(e.target.value)}
          placeholder="오답1을 입력하세요."
          className="input-field"
        />
        <br></br>
        <input
          type="text"
          value={choice2}
          onChange={(e) => setChoice2(e.target.value)}
          placeholder="오답2을 입력하세요."
          className="input-field"
        />
        <br></br>
        <input
          type="text"
          value={choice3}
          onChange={(e) => setChoice3(e.target.value)}
          placeholder="오답3을 입력하세요."
          className="input-field"
        />
        <br></br>
        <input
          type="text"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          placeholder="카테고리를 입력하세요."
          className="input-field"
        />
        <br></br>
        <button
          onClick={noCheckProblemWord}
          className="no-check-problem-word-button"
        >
          문제 만들기
        </button>
      </div>
    </div>
  );
};

export default QuizWordCrate;
