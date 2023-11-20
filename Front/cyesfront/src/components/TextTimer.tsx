import React, { useState, useEffect } from "react";

type TextTimerProps = {
  targetHour: number; // 시간
  targetMin: number; // 분
};

const TextTimer: React.FC<TextTimerProps> = ({ targetHour, targetMin }) => {
  const [remainingTime, setRemainingTime] = useState<number | 0>(0);

  useEffect(() => {
    const updateRemainingTime = () => {
      const koreanTime = new Date().toLocaleString("en-US", {
        timeZone: "Asia/Seoul",
      });
      const now = new Date(koreanTime);
      const todayTime = new Date(now);
      todayTime.setHours(targetHour, targetMin, 0, 0);

      const diff = (todayTime.getTime() - now.getTime()) / 1000;

      setRemainingTime(diff); // 5시부터 6시 사이
      //   if (setJoinable) setJoinable(true);
    };

    updateRemainingTime();
    const intervalId = setInterval(updateRemainingTime, 1000);

    return () => clearInterval(intervalId);
  }, [targetHour, targetMin]);

  return (
    <div style={{ textAlign: "center" }}>
      <div>
        {remainingTime >= 0
          ? `${Math.floor(remainingTime / 3600)
              .toString()
              .padStart(2, "0")} : ${Math.floor((remainingTime % 3600) / 60)
              .toString()
              .padStart(2, "0")} : ${Math.floor(remainingTime % 60)
              .toString()
              .padStart(2, "0")}`
          : "00 : 00 : 00"}
      </div>
    </div>
  );
};

export default TextTimer;
