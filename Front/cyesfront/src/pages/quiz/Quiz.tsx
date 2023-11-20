import React, { useState, useEffect, useRef } from "react";
import "./Quiz.css";
import { useSelector } from "react-redux";
import ProgressBar from "../../components/ProgressBar";
import ChatComponent from "../../components/chat/ChatComponent";
import ModalContainer from "../../components/modal/ModalContainer";
import {
  AnswerMessage,
  ChatMessage,
  MemberScore,
  ProblemMessage,
} from "../../api/websocket/MessageInterface";
import NotifyBox from "./NotifyBox";
import SubmitShort from "./SubmitShort";
import QuizWebSocket from "../../api/websocket/QuizWebSocket";
import SubmitSelection from "./SubmitSelection";
import SubmitOX from "./SubmitOX";
import AnswerResultComponent from "./AnswerResultComponent";
import { useLocation } from "react-router-dom";

const Quiz: React.FC = () => {
  const location = useLocation();
  // 웹소켓
  const [socket, setSocket] = useState<QuizWebSocket>();

  // redux 에서 가져오기
  const quizId = location.state.quizId;
  const memberState = useSelector((state: any) => state.member.member);
  const memberId = memberState.memberId;
  const memberNickname = memberState.memberNickname;

  const [isQuizStarted, setIsQuizStarted] = useState(false);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [isTextareaEnabled, setIsTextareaEnabled] = useState(true);

  const [progress, setProgress] = useState(0);

  const [currentSubmit, setCurrentSubmit] = useState("");

  // 채팅 리스트
  const [chatList, setChatList] = useState<ChatMessage[]>([]);

  // 문제리스트와 현재 문제 state
  const [problems, setProblems] = useState<ProblemMessage[]>([]);
  const [problem, setProblem] = useState<ProblemMessage>({
    quizId: -1,
    type: "PROBLEM",
    question: "문제를 기다리는 중",
    order: 0,
    answerLength: "",
    selections: [],
    problemType: "",
  });

  //정답리스트와 현재 정답  state
  const [answers, setAnswers] = useState<Array<string>>([]);
  const [submits, setSubmits] = useState<Array<string>>([]);
  const [currentAnswer, setCurrentAnswer] = useState<string>("");
  const [currentAnswerMessage, setCurrentAnswerMessage] =
    useState<AnswerMessage>();
  const [currentAnswerLength, setCurrentAnswerLength] = useState<string>("");

  //결과 state
  const [resultList, setResultList] = useState<MemberScore[]>([]);
  const [myScore, setMyScore] = useState<number>(0); // 맞은 개수

  //모달
  const [showModal, setShowModal] = useState(false);
  const [modalType, setModalType] = useState("moment");

  // componentdidmount
  useEffect(() => {
    const quizSocket = new QuizWebSocket(quizId);

    quizSocket.setMessageHandler(messageHandler);

    quizSocket.connect();

    setSocket(quizSocket);

    return () => {
      if (quizSocket.client.connected && !isQuizStarted) {
        quizSocket.sendLeave();
      }

      quizSocket.disconnect();
    };
  }, []);

  // 메세지 받았을 시 컨트롤 함수
  const messageHandler = (message: any) => {
    switch (message.type) {
      case "CHAT":
        setChatList((prev) => [...prev, message]);
        return;

      case "START":
        setIsQuizStarted(true);
        return;

      case "PROBLEM":
        console.log("문제 받은 시각 : ", new Date());
        setProblem(message);
        setProblems((prevProblems) => [...prevProblems, message]);
        setCurrentAnswerLength(message.answerLength);

        setIsTextareaEnabled(true);
        setIsSubmitted(false);
        setCurrentSubmit("");
        setCurrentAnswer("");
        setProgress(0);
        return;

      case "ANSWER":
        console.log("정답 받은 시각 : ", new Date());
        setCurrentAnswer(message.answer);
        setCurrentAnswerMessage(message);
        setIsTextareaEnabled(false);
        setIsSubmitted(true);
        return;

      case "END":
        // 모든 제출 정답에 대해 총 점수 계산해서 점수를 state 에 저장
        // 계산만 해놓고 기다리기 모달 띄우기
        openModal("result");
        return;

      case "RESULT":
        // 결과를 받아와서 띄우기
        // 내 총점도 띄우기
        // 모든 처리 완료 하면
        setResultList(message.gradingResultPresentResponseList);
        return;

      default:
      // 이건 와서는 안됨. 에러 처리
    }
  };

  // timer effect
  useEffect(() => {
    if (isQuizStarted && problem.quizId !== -1 && progress < 100) {
      const remainingTime = 20000 - progress * 100; // 20초를 ms로 계산
      const timeInterval = remainingTime / 200; // 0.1초 간격으로 나누기
      setTimeout(() => {
        setProgress((prev) => prev + 0.5);
      }, timeInterval);
    }
  }, [problem, progress]);

  // 정답 제출
  useEffect(() => {
    if (isSubmitted && isTextareaEnabled) {
      socket?.sendSubmit({
        quizId,
        type: "SUBMIT",
        memberId,
        problemOrder: problem.order,
        submitContent: currentSubmit,
      });
      setIsTextareaEnabled(false); //textarea 비활성화
    }
  }, [isSubmitted]);

  // 정답 입력
  useEffect(() => {
    if (currentAnswer) {
      setAnswers((prevAnswers) => [...prevAnswers, currentAnswer]);
      setSubmits((prevSubmits) => [...prevSubmits, currentSubmit]);
    }
  }, [currentAnswer]);

  //
  useEffect(() => {
    // 모달창이 켜질때면 지금까지 제출했던 답과 answer을 비교하여 Score에 저장
    if (showModal) {
      let score: number = 0;
      console.log(answers);
      console.log(submits);
      answers.forEach((answer, idx) => {
        score += answer === submits[idx] ? 1 : 0;
        console.log("score = ", score);
      });
      setMyScore(score);
    }
  }, [showModal]);

  useEffect(() => {
    if (resultList) {
      socket?.disconnect();
    }
  }, [resultList]);

  const handleTextareaChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    setCurrentSubmit(event.target.value);
  };

  const openModal = (modalType: string) => {
    setModalType(modalType);
    setShowModal(true);
  };

  return (
    <div className="container">
      <img className="live-logo-img" src="/img/live_logo.png" alt="" />
      <div className="head">SSA피드 퀴즈</div>

      {/* ProgressBar 위치 변경 */}
      {isQuizStarted && currentAnswer === "" && (
        <ProgressBar progress={progress} />
      )}

      <div className="form">
        <div className="form-group">
          {/* Problem Container */}
          <div className="quiz-container">
            <div className="quiz">
              {isQuizStarted ? (
                <div className="quiz-content">{problem?.question}</div>
              ) : (
                <ChatComponent
                  quizId={quizId}
                  memberId={memberId}
                  memberNickname={memberNickname}
                  chatList={chatList}
                  socketSend={(message: ChatMessage) => {
                    socket?.sendChat(message);
                  }}
                />
              )}
            </div>
          </div>
          {/* Problem Container End*/}

          {/* Under Box */}
          {isQuizStarted &&
            currentAnswer === "" &&
            problem.problemType === "SHORTANSWER" && (
              <SubmitShort
                answer={currentAnswer}
                answerLength={currentAnswerLength}
                textareaValue={currentSubmit}
                isTextareaEnabled={isTextareaEnabled}
                isSubmitted={isSubmitted}
                onSubmit={setIsSubmitted}
                onTextAreaChanged={handleTextareaChange}
              />
            )}
          {isQuizStarted &&
            currentAnswer === "" &&
            problem.problemType == "MULTIPLECHOICE" && (
              <SubmitSelection
                answer={currentAnswer}
                isSubmitted={isSubmitted}
                selectionList={problem.selections}
                setIsSubmitted={setIsSubmitted}
                setSubmit={setCurrentSubmit}
              />
            )}
          {isQuizStarted &&
            currentAnswer === "" &&
            problem.problemType === "TRUEORFALSE" && (
              <SubmitOX
                isSubmitted={isSubmitted}
                setIsSubmitted={setIsSubmitted}
                setSubmit={setCurrentSubmit}
              />
            )}

          {/* {isQuizStarted && currentAnswer === "" && (
            <ProgressBar progress={progress} />
          )} */}

          {isQuizStarted && currentAnswer !== "" && (
            <AnswerResultComponent
              answer={currentAnswer}
              submit={currentSubmit}
              correctNumber={currentAnswerMessage?.correctNumber}
              totalNumber={currentAnswerMessage?.totalNumber}
            />
          )}

          {!isQuizStarted && <NotifyBox />}
          {/* Under Box End */}
        </div>
      </div>

      <ModalContainer
        showModal={showModal}
        resultList={resultList}
        myScore={myScore}
        totalProblemLength={problems.length}
        modalType={modalType}
      />
    </div>
  );
};

export default Quiz;
