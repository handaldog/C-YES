import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import IconButton from "../../components/button/IconButton";
import Checkbox from "../../components/Checkbox";
import "../group/GroupQuizDetail.css";
import BottomNav from "../../components/bottomnav/BottomNav";
import { createGroupQuiz } from "../../api/QuizCreate";
import { connect } from "react-redux";
import { useSelector } from "react-redux";
type Props = {};
type QuizInfo = {
  quizName: string;
  quizType: string;
  quizSubject: string;
  quizNumber: number;
  quizStartTime: string;
};

interface QuestionContent {
  answer: string;
  choices: string[];
  category: string;
  question: string;
  type: string;
}

const GroupQuizDetail = (props: Props) => {
  const location = useLocation();
  const quizInfo = location.state as QuizInfo;
  const [currentIndex, setCurrentIndex] = useState(0);
  const [questions, setQuestions] = useState<QuestionContent[]>([]);
  const [currentQuestion, setCurrentQuestion] = useState<string>("");
  const [currentAnswer, setCurrentAnswer] = useState<string>("");
  const [currentIdx, setCurrentIdx] = useState<string>("");
  const [submitted, setSubmitted] = useState<boolean>(false);
  const [currentChoices, setCurrentChoices] = useState<string[]>([]);

  const navigate = useNavigate();
  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentQuestion(event.target.value);
  };

  const handleShortAnswerChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setCurrentAnswer(event.target.value);
  };

  const handleChioceAnswerChange = (index: number) => {
    setCurrentAnswer(currentChoices[index]);
    setCurrentIdx(index.toString());
  };

  const handleChoiceChange = (index: number, value: string) => {
    // 객관식 퀴즈의 경우 선택지를 배열에 저장
    const newChoices = [...currentChoices];
    newChoices[index] = value;
    setCurrentChoices(newChoices);

    //console.log(newChoices);
  };

  const handleTFChange = (value: string) => {
    setCurrentAnswer(value);
  };
  function determineQuestionType(quizType: string): string {
    switch (quizType) {
      case "객관식":
        return "MULTIPLECHOICE";
      case "TF 퀴즈":
        return "TRUEORFALSE";
      case "단답형":
        return "SHORTANSWER";
      default:
        return "UNKNOWN";
    }
  }

  const memberId = useSelector((state: any) => state.member.member.memberId);

  const handleNextButtonClick = () => {
    if (currentQuestion == "") {
      alert("문제를 작성하세요.");
    } else if (currentAnswer == "") {
      alert("정답을 작성하세요.");
    }

    if (quizInfo.quizType === "객관식") {
    }
    // 현재 입력한 문제와 정답을 배열에 추가
    const newQuestions = [
      ...questions,
      {
        answer: currentAnswer,
        choices: currentChoices, // 선택지가 있다면 해당 부분을 업데이트
        category: quizInfo.quizSubject, // 문제 설명이 있다면 해당 부분을 업데이트
        question: currentQuestion,
        type: determineQuestionType(quizInfo.quizType),
      },
    ];

    setCurrentIndex(currentIndex + 1);
    setQuestions(newQuestions);
    //setCurrentChoices(currentChoices);

    // console.log("보낼 퀴즈 list: ", questions);

    setCurrentQuestion("");
    setCurrentAnswer("");
    setCurrentChoices([]);

    setSubmitted(false);
  };

  const handleSubmitButtonClick = async () => {
    setSubmitted(true);

    const newQuestions = [
      ...questions,
      {
        answer: currentAnswer,
        choices: currentChoices, // 선택지가 있다면 해당 부분을 업데이트
        category: quizInfo.quizSubject, // 문제 설명이 있다면 해당 부분을 업데이트
        question: currentQuestion,
        type: determineQuestionType(quizInfo.quizType),
      },
    ];

    setQuestions(newQuestions);

    console.log("최종 퀴즈 list: ", newQuestions);

    const resultQuestions = {
      memberId: memberId,

      quizTitle: quizInfo.quizName,
      startDateTime: quizInfo.quizStartTime,
      problemByUserList: newQuestions,
    };

    console.log("보내기:", resultQuestions);
    const result = await createGroupQuiz(resultQuestions);

    console.log("결과: ", result);

    if (result) {
      console.log("퀴즈가 성공적으로 생성되었습니다.", result);
      navigate("/group");
    } else {
      console.log("퀴즈 생성에 실패했습니다.");
      alert("퀴즈 생성 실패");
      navigate("/group");
    }
  };

  useEffect(() => {
    console.log(quizInfo);
    console.log("store에 저장된 내 id: ", memberId);
  }, []);
  useEffect(() => {
    console.log(questions);
  }, [questions]);

  return (
    <div className="live-container">
      <div className="content">
        <div className="group-content">
          <div className="group-title">문제 생성</div>
        </div>

        <div>
          <div>
            <div className="question-detail-content">
              주제 : {quizInfo.quizSubject}
            </div>
            <div>문제</div>
            <div className="question-detail-content">
              <div>
                <input
                  className="tf-textarea"
                  value={currentQuestion}
                  onChange={handleTitleChange}
                />
              </div>
            </div>
            {quizInfo.quizType === "TF 퀴즈" && (
              <div className="dropdown-content">
                <div>정답</div>
                <div className="checkbox-content">
                  <Checkbox
                    label="T"
                    checked={currentAnswer.includes("T")}
                    onChange={() => handleTFChange("TRUE")}
                  />
                  <Checkbox
                    label="F"
                    checked={currentAnswer.includes("F")}
                    onChange={() => handleTFChange("FALSE")}
                  />
                </div>
              </div>
            )}
            {quizInfo.quizType === "객관식" && (
              <div>
                {[0, 1, 2, 3].map((index) => (
                  <div
                    key={index}
                    className="detail-select-content"
                    style={{
                      margin: "5px", // 선택지 사이의 간격을 조정
                    }}
                  >
                    <div style={{ display: "flex", alignItems: "center" }}>
                      <label>
                        <input
                          type="checkbox"
                          checked={currentIdx.includes(index.toString())}
                          onChange={() => handleChioceAnswerChange(index)}
                        />
                        <span>{`선택지 ${index + 1}`}</span>
                      </label>
                    </div>
                    <div>
                      <input
                        className="select-textarea"
                        value={currentChoices[index] || ""}
                        onChange={(e) =>
                          handleChoiceChange(index, e.target.value)
                        }
                      />
                    </div>
                  </div>
                ))}
              </div>
            )}

            {quizInfo.quizType === "단답형" && (
              <div className="dropdown-content">
                <div>정답</div>
                <div>
                  <input
                    className="tf-textarea"
                    value={currentAnswer}
                    onChange={handleShortAnswerChange}
                  />
                </div>
              </div>
            )}
          </div>
          <div className="bottom-next">
            {currentIndex + 1}/{quizInfo.quizNumber}
            {currentIndex === quizInfo.quizNumber - 1 && !submitted ? (
              <button onClick={handleSubmitButtonClick}>제출</button>
            ) : (
              <IconButton
                iconUrl="/icon/right-arrow.png"
                onClick={handleNextButtonClick}
              />
            )}
          </div>
        </div>
      </div>
      <BottomNav checkCS={false} checkLive={false} checkGroup={true} />
    </div>
  );
};

export default GroupQuizDetail;
