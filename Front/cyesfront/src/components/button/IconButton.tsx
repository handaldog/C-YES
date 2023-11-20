import React from "react";

type Props = {
  onClick?: () => void;
  iconUrl?: string; // 이미지 아이콘을 표시할 이미지 URL 프로퍼티 추가
};

const IconButton: React.FC<Props> = ({ onClick, iconUrl }) => {
  return (
    <button
      onClick={onClick}
      style={{
        background: "none",
        border: "none",
        cursor: "pointer",
        padding: 0,
        display: "flex",
        //alignItems: "center",
        justifyContent: "flex-start", // 왼쪽 정렬으로 변경
        outline: "none", // 포커스 시 테두리 제거

        // 위치를 설정할 수 있는 스타일을 추가
        //position: "absolute",
      }}
    >
      {iconUrl && (
        <img
          src={iconUrl}
          alt="Icon"
          style={{
            width: "24px",
            height: "24px",
          }}
        />
      )}
    </button>
  );
};

export default IconButton;
