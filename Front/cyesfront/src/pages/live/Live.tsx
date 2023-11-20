import React, { useEffect, useState, useRef } from "react";
import BottomNav from "../../components/bottomnav/BottomNav";
import "./Live.css";
import CountdownTimer from "../../components/CountdownTimer";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { getMainQuizInfo } from "../../api/QuizAPI";
import { saveQuiz } from "../../redux/actions/QuizAction";
import { Quiz } from "../../redux/ReduxStateInterface";

type Props = {};

const Live = (props: Props) => {
  const defaultQuiz = {
    quizId: -1,
    quizTitle: "예정된 퀴즈 일정이 없습니다",
    quizStartDate: new Date().toISOString(),
  };

  const [mainQuiz, setMainQuiz] = useState<Quiz>(defaultQuiz);
  const [targetTime, setTargetTime] = useState({
    hour: new Date().getHours(),
    minute: new Date().getMinutes(),
  });
  const [joinable, setJoinable] = useState<boolean>(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const memberInfo = useSelector((state: any) => state.member);
  const intervalRef = useRef<NodeJS.Timer | null>(null);

  const getLiveInfo = async () => {
    const mainQuizInfo = await getMainQuizInfo();
    console.log("info", mainQuizInfo);

    if (mainQuizInfo == null || mainQuizInfo.quizId == -1) {
      setMainQuiz({ ...defaultQuiz, quizStartDate: new Date().toISOString() });
      return;
    }

    setMainQuiz(mainQuizInfo);
  };

  const enterRoom = () => {
    // 다른 페이지로 이동
    dispatch(saveQuiz(mainQuiz));
    // navigate("/quiz");
    const targetTime = new Date(mainQuiz.quizStartDate);
    const targetHour = targetTime.getHours();
    const targetMin = targetTime.getMinutes();
    navigate(`/quiz?targetHour=${targetHour}&targetMin=${targetMin}`, {
      state: { quizId: mainQuiz.quizId, quizTitle: mainQuiz.quizTitle },
    });
  };

  useEffect(() => {
    // mainQuiz가 변경될 때마다 targetTime 업데이트
    const quizStartDate = new Date(mainQuiz.quizStartDate);
    setTargetTime({
      hour: quizStartDate.getHours(),
      minute: quizStartDate.getMinutes(),
    });
  }, [mainQuiz]);

  useEffect(() => {
    // 첫 번째 정보 가져오기 시도.
    getLiveInfo();

    // 첫 번째 setTimeout을 설정합니다.
    const timeoutId = setTimeout(() => {
      getLiveInfo(); // 첫 번째 getLiveInfo 호출 후에 바로 정보를 가져옵니다.
      // 그 다음 매 분마다 정보를 가져오는 인터벌을 설정합니다.
      intervalRef.current = setInterval(getLiveInfo, 60000);
    }, (60 - new Date().getSeconds()) * 1000);

    // useEffect의 cleanup 함수입니다.
    return () => {
      clearTimeout(timeoutId); // setTimeout도 취소합니다.
      if (intervalRef.current) {
        clearInterval(intervalRef.current); // 현재 설정된 인터벌을 취소합니다.
      }
    };
  }, []);

  return (
    <div className="live-container">
      <div className="content">
        <div className="title-text">
          <img
            className="main-logo-img"
            src="/img/mainlogo.png"
            alt="logo img"
          />
        </div>
        <CountdownTimer
          targetHour={targetTime.hour}
          targetMin={targetTime.minute}
          quizTitle={mainQuiz.quizTitle}
          setJoinable={setJoinable}
        />
        <p style={{ fontSize: "26px" }}>{mainQuiz.quizTitle}</p>
        <RoundCornerBtn
          width="150px"
          height="45px"
          bgcolor={joinable ? "#FF5733" : "#868686"}
          bghover={joinable ? "#853828" : "#868686"}
          fontSize="16px"
          fontcolor="#FFFFFF"
          onClick={enterRoom}
          disabled={!joinable}
        >
          대기실 입장
        </RoundCornerBtn>
      </div>
      <BottomNav checkCS={false} checkLive={true} checkGroup={false} />
    </div>
  );
};

export default Live;
