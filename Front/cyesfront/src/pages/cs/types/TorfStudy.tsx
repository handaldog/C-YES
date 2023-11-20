import React, { useState, useEffect } from "react";
import "./TorfStudy.css";
import RoundCornerBtn from "../../../components/RoundCornerBtn";
import "./Common.css";
import IconButton from "../../../components/button/IconButton";
import { useLocation, useNavigate } from "react-router-dom";

type Props = {};

interface QuestionContent {
  answer: string;
  choices: string[];
  description: string;
  id: string;
  problemOrder: number;
  question: string;
  type: string;
}
const TorfStudy = (props: Props) => {
  const location = useLocation();
  const navigate = useNavigate();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [submit, setSubmit] = useState<string>();
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

  const [modalMessage, setModalMessage] = useState<string | null>(null);

  const openModal = (message: string) => {
    setModalMessage(message);
  };

  const closeModal = () => {
    setModalMessage(null);
    setSubmit("");
  };

  useEffect(() => {
    if (submit === "TRUE") {
      if (questions[currentIndex]?.answer === "TRUE") {
        openModal("정답입니다!");
      } else {
        openModal("오답입니다!");
      }
    } else if (submit === "FALSE") {
      if (questions[currentIndex]?.answer === "FALSE") {
        openModal("정답입니다!");
      } else {
        openModal("오답입니다!");
      }
    }
  }, [submit, currentIndex, questions]);
  useEffect(() => {
    if (location.state && location.state.questions) {
      setQuestions(location.state.questions);
    }
  }, []);
  return (
    <div>
      <div className="cs-title-container">
        <div className="x-image-container">
          <IconButton onClick={goCsPage} iconUrl="/icon/close.png" />
        </div>
        <div className="title-text-container">T or F</div>
      </div>

      <div>
        <div className="card-tf">
          <div className="card-tf-content">
            {questions[currentIndex]?.question || "해당 문제가 없습니다."}
          </div>
        </div>
      </div>
      <div className="submit-button-content">
        <RoundCornerBtn
          onClick={() => setSubmit("TRUE")}
          bgcolor="#57FF5E"
          bghover="#39A63D"
          height="200px"
          width="100px"
          fontSize="40px"
        >
          T
        </RoundCornerBtn>
        <div className="box"></div>
        <RoundCornerBtn
          onClick={() => setSubmit("FALSE")}
          bgcolor="#FF2A2A"
          bghover="#A61B1B"
          height="200px"
          width="100px"
          fontSize="40px"
        >
          F
        </RoundCornerBtn>
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

export default TorfStudy;
