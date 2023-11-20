import React from "react";

const LoadingModal: React.FC = () => (
  <div className="rank-loading">
    <div className="loading-background">
      <div className="end-topic">🥁 퀴즈 종료</div>
    </div>

    <div className="loading-text">순위 산정 중</div>
    <img src="/img/loading.gif" alt="로딩 중" width={60}></img>
  </div>
);

export default LoadingModal;
