import React, { useState, useEffect } from "react";
import "./SelectStudy.css";
import "./Common.css";
import IconButton from "../../../components/button/IconButton";
import { useNavigate, useLocation } from "react-router-dom";
import RoundCornerBtn from "../../../components/RoundCornerBtn";

type SelectStudyProps = {};

interface QuestionContent {
  answer: string;
  choices: string[];
  description: string;
  id: string;
  problemOrder: number;
  question: string;
  type: string;
}

const SelectStudy = (props: SelectStudyProps) => {
  const navigate = useNavigate();
  const location = useLocation();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [questions, setQuestions] = useState<QuestionContent[]>([]);
  const [modalMessage, setModalMessage] = useState<string | null>(null);

  const goCsPage = () => {
    navigate("/cs");
  };

  const prevStudy = () => {
    if (currentIndex > 0) {
      setCurrentIndex((prevIndex) => prevIndex - 1);
    }
  };

  const nextStudy = () => {
    if (currentIndex < questions.length - 1) {
      setCurrentIndex((prevIndex) => prevIndex + 1);
    }
  };

  const compareAnswer = (selectNum: number) => {
    if (
      questions[currentIndex].answer ===
      questions[currentIndex].choices[selectNum]
    ) {
      openModal("정답입니다!");
    } else {
      openModal("오답입니다!");
    }
  };

  const openModal = (message: string) => {
    setModalMessage(message);
  };

  const closeModal = () => {
    setModalMessage(null);
  };

  const nextTen = () => {
    if (questions.length - currentIndex > 10) {
      setCurrentIndex((prevIndex) => prevIndex + 10);
    } else {
      setCurrentIndex(
        (prevIndex) => prevIndex + (questions.length - currentIndex) - 1
      );
    }
  };

  const prevTen = () => {
    if (currentIndex > 10) {
      setCurrentIndex((prevIndex) => prevIndex - 10);
    } else {
      setCurrentIndex((prevIndex) => prevIndex - currentIndex);
    }
  };

  useEffect(() => {
    if (location.state && location.state.questions) {
      console.log("setQuestions: ", location.state.questions);
      setQuestions(location.state.questions);
    }
  }, [location]);

  return (
    <div>
      <div className="cs-title-container">
        <div className="x-image-container">
          <IconButton onClick={goCsPage} iconUrl="/icon/close.png" />
        </div>
        <div className="title-text-container">객관식</div>
      </div>

      <div className="question-container">
        <div className="question-content">
          {questions[currentIndex]?.question || "해당 문제가 없습니다."}
        </div>
      </div>

      <div className="answer-container">
        <div className="answer-four">
          <div
            className="answer-box answer-box-one"
            onClick={() => compareAnswer(0)}
          >
            {questions[currentIndex]?.choices[0] || "X"}
          </div>
          <div
            className="answer-box answer-box-two"
            onClick={() => compareAnswer(1)}
          >
            {questions[currentIndex]?.choices[1] || "X"}
          </div>
        </div>
        <div className="answer-four">
          <div
            className="answer-box answer-box-three"
            onClick={() => compareAnswer(2)}
          >
            {questions[currentIndex]?.choices[2] || "X"}
          </div>
          <div
            className="answer-box answer-box-four"
            onClick={() => compareAnswer(3)}
          >
            {questions[currentIndex]?.choices[3] || "X"}
          </div>
        </div>
      </div>

      <div className="bottom-next">
        <IconButton onClick={prevTen} iconUrl="/icon/left-ten.png" />
        <IconButton onClick={prevStudy} iconUrl="/icon/left-arrow.png" />
        {currentIndex + 1}/{questions.length}
        <IconButton onClick={nextStudy} iconUrl="/icon/right-arrow.png" />
        <IconButton onClick={nextTen} iconUrl="/icon/right-ten.png" />
      </div>
      {modalMessage && (
        <div className="modal-tf">
          <div className="modal-content-tf">
            <p>{modalMessage}</p>
            <RoundCornerBtn onClick={closeModal} fontSize="15px">
              확인
            </RoundCornerBtn>
          </div>
        </div>
      )}
    </div>
  );
};
export default SelectStudy;
