import React, { useState, useEffect } from "react";
import "./CardStudy.css";
import "./Common.css";
import IconButton from "../../../components/button/IconButton";
import { useLocation, useNavigate } from "react-router-dom";

interface QuestionContent {
  answer: string;
  choices: string[];
  description: string;
  id: string;
  problemOrder: number;
  question: string;
  type: string;
}

type Props = {};

const CardStudy = (props: Props) => {
  const location = useLocation();
  const navigate = useNavigate();

  const [currentIndex, setCurrentIndex] = useState(0);
  const [questions, setQuestions] = useState<QuestionContent[]>([]);

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
      console.log("가져온 리스트:  ", location.state.questions.id);
      console.log("가져온 리스트 갯수:  ", location.state.questions.length);
      setQuestions(location.state.questions);
    }
  }, []);

  return (
    <div>
      <div className="cs-title-container">
        <div className="x-image-container">
          <IconButton onClick={goCsPage} iconUrl="/icon/close.png" />
        </div>
        <div className="title-text-container">카드 학습</div>
      </div>

      <div className="flip">
        <div className="card">
          <div className="card-front">
            {questions[currentIndex]?.answer || "해당 문제가 없습니다."}
          </div>
          <div className="card-back">
            {/* {questions[currentIndex]?.description} */}
            {questions[currentIndex]?.description || "해당 문제가 없습니다."}
          </div>
        </div>
      </div>

      <div className="bottom-next">
        <IconButton onClick={prevTen} iconUrl="/icon/left-ten.png" />
        <IconButton onClick={prevStudy} iconUrl="/icon/left-arrow.png" />
        {questions[currentIndex]
          ? `${currentIndex + 1}/${questions.length}`
          : "0/0"}
        <IconButton onClick={nextStudy} iconUrl="/icon/right-arrow.png" />
        <IconButton onClick={nextTen} iconUrl="/icon/right-ten.png" />
      </div>
    </div>
  );
};
export default CardStudy;
