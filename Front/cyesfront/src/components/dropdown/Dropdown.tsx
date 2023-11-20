import React, { useState } from "react";
import "./Dropdown.css";
import { transCategory } from "../TransCategory";

interface DropdownProps {
  items: string[];
  onChange: (selectedItem: string) => void; // 새로운 props 추가
}

const Dropdown: React.FC<DropdownProps> = ({ items, onChange }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState(""); // 새로운 state 추가

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  const selectItem = (item: string) => {
    setSelectedItem(item); // 항목을 선택하면 상태 업데이트
    onChange(item); // 선택한 항목을 상위 컴포넌트로 전달
    setIsOpen(false); // 선택 후 드롭다운 닫기
  };

  return (
    <div className="dropdown">
      <div className="dropdown-toggle" onClick={toggleDropdown}>
        {transCategory(selectedItem) || "선택하세요"}
      </div>
      {isOpen && (
        <div className="dropdown-menu">
          {items.map((item, index) => (
            <div
              key={index}
              className="dropdown-item"
              onClick={() => selectItem(item)}
            >
              {transCategory(item)}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Dropdown;
