import React from "react";
import "./FloatingButton.css"; // CSS 파일 임포트

type FloatingButtonProps = {
  onClick: () => void; // 클릭 이벤트 타입
};

const FloatingButton: React.FC<FloatingButtonProps> = ({ onClick }) => {
  return (
    <button className="floating-button" onClick={onClick}>
      <span>+</span>
    </button>
  );
};

export default FloatingButton;
