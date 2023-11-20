import React from "react";
import RoundCornerBtn from "../RoundCornerBtn"; // 경로는 실제 위치에 따라 달라질 수 있습니다.
import "./RankingModal.css";

type RankingModalProps = {
  memberList: any[]; // Member 타입은 실제 정의에 맞게 수정해야 합니다.
  myScore?: number;
  totalProblemLength?: number;
  onNavigate: () => void;
};

const RankingModal: React.FC<RankingModalProps> = ({
  memberList,
  myScore,
  totalProblemLength,
  onNavigate,
}) => (
  <div className="rank-loading">
    <div className="loading-background">
      <div className="loading-topic">🏆 오늘의 랭킹</div>
    </div>

    <div className="rank-content">
      <div>
        {memberList.map((member, index) => (
          <div key={index}>
            {index + 1}위: {member.nickName}
          </div>
        ))}
      </div>
      내 점수 : {myScore} / {totalProblemLength}
    </div>

    <RoundCornerBtn type="submit" onClick={onNavigate} bghover="black">
      메인
    </RoundCornerBtn>
  </div>
);

export default RankingModal;
