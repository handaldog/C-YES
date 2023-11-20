import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./GroupMain.css";
import BottomNav from "../../components/bottomnav/BottomNav";
import { getGroupQuiz, getGroupQuizUseTitle } from "../../api/Group";
import FloatingButton from "../../components/FloatingButton";
import { transCategory } from "../../components/TransCategory";

type Props = {};

interface QuizInfo {
  quizId: number;
  quizTitle: string;
  quizStartDate: string;
  category: string;
  type: string;
  problemCnt: number;
}

const GroupMain = (props: Props) => {
  const [quizzes, setQuizzes] = useState<QuizInfo[]>([]);
  const [searchQuiz, setSearchQuiz] = useState<QuizInfo[]>([]);
  const [searchTerm, setSearchTerm] = useState(""); // 검색어를 저장할 상태
  const navigate = useNavigate();

  const handleButtonClick = () => {
    navigate("/group/create");
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value); // 입력 필드 값 변경 시 상태 업데이트
  };

  const handleInputKeyDown = async (
    e: React.KeyboardEvent<HTMLInputElement>
  ) => {
    if (e.key === "Enter") {
      console.log("enter눌림", searchTerm);
      fetchSearchQuiz();
    }
  };

  const fetchQuizData = async () => {
    const data = await getGroupQuiz(); // API 호출
    console.log("allQuiz: ", data);
    if (data) {
      setQuizzes(data);
    }
  };

  const fetchSearchQuiz = async () => {
    const data = await getGroupQuizUseTitle(searchTerm); // API 호출
    console.log("searchData: ", data);
    if (data) {
      setSearchQuiz(data);
    }
  };

  const transrateTypeText = (text: string): string => {
    if (text === "SHORTANSWER") {
      return "단답형";
    } else if (text === "TRUEORFALSE") {
      return "O/X";
    } else if (text === "MULTIPLECHOICE") {
      return "객관식";
    } else return "";
  };

  const convertDataFormat = (quizDate: string): string => {
    const formattedDate = new Date(quizDate);

    // 'yy.MM.dd HH:mm' 형식으로 날짜와 시간을 변환
    return formattedDate.toLocaleString("ko-KR", {
      year: "2-digit",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
      hour12: false, // 24시간 형식
    });
  };

  const moveToGroupQuiz = (
    quizTitle: string,
    quizId: number,
    quizStartDate: string
  ) => {
    console.log("title: ", quizTitle);
    console.log("id: ", quizId);
    console.log("startTime: ", quizStartDate);

    const startDate = new Date(quizStartDate);
    const targetHour = startDate.getHours();
    const targetMin = startDate.getMinutes();

    navigate(`/quiz?targetHour=${targetHour}&targetMin=${targetMin}`, {
      state: { quizTitle, quizId },
    });
  };

  useEffect(() => {
    fetchQuizData();
    fetchSearchQuiz();
  }, []);

  return (
    <div>
      <div className="group-main-container">
        <div className="group-center">
          <div className="group-background">
            <div className="group-title-container">그룹퀴즈</div>
          </div>
        </div>

        <div className="group-description-container">
          <span>그룹을 결성해 퀴즈를 만들고{"\n"}</span>
          <span>방에 접속해 라이브 퀴즈를 풀어보아요!</span>
        </div>
        <div className="group-search-container">
          <img src="/icon/search.png" alt=""></img>
          <input
            placeholder="퀴즈 방 검색"
            value={searchTerm}
            onChange={handleInputChange}
            onKeyDown={handleInputKeyDown}
          ></input>
        </div>
        <div className="fast-quiz-container">
          <div className="quiz-text-container">빠른 퀴즈</div>
          <div className="fast-quiz-lineup-container">
            {quizzes.length === 0 && (
              <div
                className="fast-quiz-box"
                style={{
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <span>퀴즈가 없습니다!</span>
              </div>
            )}
            {quizzes.length > 0 && (
              <div
                className="fast-quiz-box"
                onClick={() =>
                  moveToGroupQuiz(
                    quizzes[0].quizTitle,
                    quizzes[0].quizId,
                    quizzes[0].quizStartDate
                  )
                }
              >
                <div className="quiz-box-title">{quizzes[0].quizTitle}</div>
                <div className="quiz-box-text">
                  유형: {transrateTypeText(quizzes[0].type)}
                </div>
                <div className="quiz-box-text">
                  문제수: {quizzes[0].problemCnt}
                </div>
                <div className="quiz-box-text">
                  {convertDataFormat(quizzes[0].quizStartDate)}
                </div>
                <div className="quiz-box-text-sharp">
                  # {transCategory(quizzes[0].category)}
                </div>
              </div>
            )}
            {quizzes.length > 1 && (
              <div
                className="fast-quiz-box"
                onClick={() =>
                  moveToGroupQuiz(
                    quizzes[1].quizTitle,
                    quizzes[1].quizId,
                    quizzes[1].quizStartDate
                  )
                }
              >
                <div className="quiz-box-title">{quizzes[1].quizTitle}</div>
                <div className="quiz-box-text">
                  유형: {transrateTypeText(quizzes[1].type)}
                </div>
                <div className="quiz-box-text">
                  문제수: {quizzes[1].problemCnt}
                </div>
                <div className="quiz-box-text">
                  {convertDataFormat(quizzes[1].quizStartDate)}
                </div>
                <div className="quiz-box-text-sharp">
                  # {transCategory(quizzes[1].category)}
                </div>
              </div>
            )}
          </div>
          <div className="group-result-container">
            <div className="quiz-text-container">검색된 퀴즈</div>
            <div className="result-quiz-box">
              {searchQuiz.length > 0 ? (
                searchQuiz.map((quiz, index) => (
                  <div
                    key={index}
                    className="each-result-quiz-box"
                    onClick={() =>
                      moveToGroupQuiz(
                        quiz.quizTitle,
                        quiz.quizId,
                        quiz.quizStartDate
                      )
                    }
                  >
                    <div className="search-box-title">
                      {quiz.quizTitle} (No. {quiz.quizId})
                    </div>
                    <div className="search-box-text">
                      {transrateTypeText(quiz.type)} / {quiz.problemCnt} 문제 /{" "}
                      {transCategory(quiz.category)}
                    </div>
                    <div className="search-box-text">
                      {convertDataFormat(quiz.quizStartDate)}
                    </div>
                  </div>
                ))
              ) : (
                <div className="no-quiz-message">검색된 퀴즈가 없습니다!</div>
              )}
            </div>
          </div>
        </div>
        <div>
          <FloatingButton onClick={handleButtonClick} />
        </div>
      </div>
      <div className="bottom-nav">
        <BottomNav checkCS={false} checkLive={false} checkGroup={true} />
      </div>
    </div>
  );
};

export default GroupMain;
