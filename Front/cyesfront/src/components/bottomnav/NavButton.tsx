import React from "react";
import { useNavigate } from "react-router-dom";
import "./NavButton.css";

interface NavButtonProps {
  isSelected: boolean;
  selectedImg: string;
  defaultImg: string;
  altText: string;
  routePath: string;
  onClick: () => void;
  label: string;
}

const NavButton: React.FC<NavButtonProps> = ({
  isSelected,
  selectedImg,
  defaultImg,
  altText,
  routePath,
  onClick,
  label,
}) => {
  const navigate = useNavigate();

  return (
    <button
      onClick={() => {
        onClick();
        navigate(routePath);
      }}
      className={`nav-button ${isSelected ? "selected" : ""}`}
    >
      <img src={isSelected ? selectedImg : defaultImg} alt={altText} />
      <span>{label}</span>
    </button>
  );
};

export default NavButton;
