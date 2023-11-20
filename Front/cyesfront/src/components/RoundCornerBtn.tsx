import React from "react";
import styled from "styled-components";

type Props = {
  children?: React.ReactNode;
  width?: string;
  height?: string;
  bgcolor?: string;
  bghover?: string;
  fontSize?: string;
  fontcolor?: string;
  type?: "button" | "submit" | "reset";
  onClick?: () => void;
  disabled?: boolean;
  customshadow?: string;
  opacity?: number;
};

const StyledButton = styled.button<Props>`
  width: ${(props) => props.width || "100px"};
  height: ${(props) => props.height || "40px"};
  background-color: ${(props) => props.bgcolor || "#31a0eb"};
  &:hover {
    background-color: ${(props) => props.bghover || "#32237d"};
  }
  font-size: ${(props) => props.fontSize || "12px"};
  color: ${(props) => props.fontcolor || "#ffffff"};
  border-radius: 18px;
  border: none;
  font-family: "Dovemayo_gothic";
  box-shadow: ${(props) =>
    props.customshadow || "0 4px 6px rgba(0, 0, 0, 0.1)"};
  opacity: ${(props) => props.opacity || 1};
  cursor: pointer; /* 버튼에 마우스 오버 시 커서 변경 */
  transition: background-color 0.3s; /* 부드러운 색상 변화를 위한 전환 효과 */
  &:disabled {
    background-color: #ccc; /* 비활성화 상태의 버튼 색상 */
    cursor: default;
    opacity: 0.5;
  }
`;

const RoundCornerBtn = ({ children, ...style }: Props) => {
  return <StyledButton {...style}>{children}</StyledButton>;
};

export default RoundCornerBtn;
