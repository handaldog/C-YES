import React from "react";
import TextTimer from "../../components/TextTimer";
import "./Quiz.css";
import { useLocation } from "react-router-dom";

const NotifyBox = () => {
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);

    // URL 매개변수에서 정보 추출
    const targetHourString = searchParams.get("targetHour");
    const targetHour = targetHourString ? parseInt(targetHourString, 10) : 0;

    const targetMinString = searchParams.get("targetMin");
    const targetMin = targetMinString ? parseInt(targetMinString, 10) : 0;

    return (
        <div>
            <div className="bottom_notice">🧩퀴즈가 곧 시작합니다!🧩</div>
            <div>
                <TextTimer targetHour={targetHour} targetMin={targetMin} />
            </div>
            <div className="notice">
                <br />
                <br />
                📌각 문제는 20초 동안 풀 수있는 스피드 퀴즈입니다
                <br />
                📌각 문제가 끝난후, 정답을 확인 할 수 있습니다
                <br />
                📌동점일 경우 제출한 순서대로 순위가 산정됩니다
                <br />
                📌모든 정답은 한글로 제출해주세요
                <br />
            </div>
        </div>
    );
};

export default NotifyBox;
