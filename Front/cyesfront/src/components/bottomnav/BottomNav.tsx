import React, { useState } from "react";
import "./BottomNav.css";
import NavButton from "./NavButton"; // NavButton 컴포넌트를 import

interface BottomNavProps {
  onSelectionChange?: (selection: string) => void;
  checkCS: boolean;
  checkLive: boolean;
  checkGroup: boolean;
}

const BottomNav: React.FC<BottomNavProps> = ({
  onSelectionChange,
  checkCS,
  checkLive,
  checkGroup,
}) => {
  let initialSelection: string | null = null;
  if (checkCS) {
    initialSelection = "cs공부";
  } else if (checkLive) {
    initialSelection = "라이브";
  } else if (checkGroup) {
    initialSelection = "그룹";
  }

  const [selected, setSelected] = useState<string | null>(initialSelection);

  const handleClick = (item: string) => {
    setSelected(item);
    if (onSelectionChange) {
      onSelectionChange(item);
    }
  };

  return (
    <div className="bottom-nav-container">
      <NavButton
        isSelected={selected === "cs공부"}
        selectedImg="/img/select_pencil.png"
        defaultImg="/img/pencil.png"
        altText=""
        routePath="/cs"
        onClick={() => handleClick("cs공부")}
        label="cs공부"
      />
      <NavButton
        isSelected={selected === "라이브"}
        selectedImg="/img/select_live.png"
        defaultImg="/img/live.png"
        altText=""
        routePath="/live"
        onClick={() => handleClick("라이브")}
        label="라이브"
      />
      <NavButton
        isSelected={selected === "그룹"}
        selectedImg="/img/select_people.png"
        defaultImg="/img/people.png"
        altText=""
        routePath="/group"
        onClick={() => handleClick("그룹")}
        label="그룹"
      />
    </div>
  );
};

export default BottomNav;
