import React, { useState } from "react";
import BottomNav from "../../components/bottomnav/BottomNav";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import "./ComputerScience.css";
import SelectTitleModal from "../../components/modal/SelectTitleModal";
type Props = {};

const ComputerScience = (props: Props) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [studyType, setStudyType] = useState("");

  // 모달 열기
  const openModal = (type: string) => {
    console.log(type);
    setStudyType(type); // 선택된 스터디 타입을 상태 변수에 저장
    setIsModalOpen(true);
  };

  // 문제 시작하기
  const close = () => setIsModalOpen(false);

  return (
    <div className="cs-container">
      <div className="cs-content">
        <div className="cs-background">
          <div className="cs-text-container">cs 학습</div>
        </div>

        <div className="select-title-container">
          <SelectTitleModal
            isOpen={isModalOpen}
            close={close}
            type={studyType}
          ></SelectTitleModal>
        </div>
        {/* 카드학습 => type = card */}
        <div className="button-container">
          <div className="button-element">
            <RoundCornerBtn
              width="300px"
              height="100px"
              fontSize="30px"
              fontcolor="black"
              bgcolor="white"
              bghover="grey"
              customshadow="2px 2px 4px rgba(0, 0, 0, 0.2)"
              onClick={() => openModal("card")}
            >
              <img
                className=""
                src="/icon/card.png"
                alt=""
                style={{ width: "45px", height: "55px", marginRight: "20px" }}
              />
              카드학습
            </RoundCornerBtn>
          </div>
          {/* 객관식 => type = select */}
          <div className="button-element">
            <RoundCornerBtn
              width="300px"
              height="100px"
              fontSize="30px"
              fontcolor="black"
              bgcolor="white"
              bghover="grey"
              customshadow="2px 2px 4px rgba(0, 0, 0, 0.2)"
              onClick={() => openModal("select")}
            >
              <img
                className=""
                src="/icon/select.png"
                alt=""
                style={{ width: "45px", height: "45px", marginRight: "20px" }}
              />
              객관식
            </RoundCornerBtn>
          </div>
          {/* T or F => type = torf*/}
          <div className="button-element">
            {" "}
            <RoundCornerBtn
              width="300px"
              height="100px"
              fontSize="30px"
              fontcolor="black"
              bgcolor="white"
              bghover="grey"
              customshadow="2px 2px 4px rgba(0, 0, 0, 0.2)"
              onClick={() => openModal("torf")}
            >
              <img
                className=""
                src="/icon/torf.png"
                alt=""
                style={{ width: "45px", height: "45px", marginRight: "20px" }}
              />
              T or F
            </RoundCornerBtn>
          </div>
        </div>
      </div>
      <BottomNav checkCS={true} checkLive={false} checkGroup={false} />
    </div>
  );
};

export default ComputerScience;
