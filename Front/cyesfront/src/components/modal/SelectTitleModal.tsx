import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./SelectTitleModal.css";
import { getCsCategory } from "../../api/CsAPI";
import { getProblems } from "../../api/QuestionAPI";
import { transCategory } from "../TransCategory";

interface SelectTitleModalProps {
  isOpen: boolean;
  close: () => void;
  type: string;
}

interface ContentResponse {
  question: string;
  answer: string;
}

interface QuestionContent {
  id: string;
  contentResponse: ContentResponse;
  problemOrder: number;
  category: string;
  type: string;
}

const SelectTitleModal: React.FC<SelectTitleModalProps> = ({
  isOpen,
  close,
  type,
}) => {
  const [categories, setCategories] = useState<string[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);
  const [questions, setQuestions] = useState<QuestionContent[]>([]);
  const navigate = useNavigate();

  // API
  const fetchCategories = async () => {
    const data = await getCsCategory(); // API 호출
    if (data) {
      setCategories(data);
    }
  };

  // API
  const fetchQuestions = async (category: string, type: string) => {
    try {
      const response = await getProblems(category, type, 0, 10, "string"); // 10개의 문제를 ID 내림차순으로 가져옵니다.
      return response.content; // 응답에서 content만 반환합니다.
    } catch (err) {
      console.error("Error fetching questions: ", err);
      return [];
    }
  };

  // useEffect
  useEffect(() => {
    fetchCategories();
  }, []);

  // 문제 유형 파라미터 바꾸는 함수
  const getProblemType = (type: string) => {
    switch (type) {
      case "card":
        return "SHORTANSWER";
      case "select":
        return "MULTIPLECHOICE";
      case "torf":
        return "TRUEORFALSE";
      default:
        return "UNKNOWN"; // 이것은 기본값으로, 정의되지 않은 type이 전달되었을 때 사용됩니다.
    }
  };

  // 시작 버튼 함수
  const startStudy = async () => {
    if (selectedCategory) {
      // API 호출을 위한 파라미터 설정
      const problemType = getProblemType(type);
      // 선택된 카테고리와 타입으로 문제를 가져옴
      const fetchedQuestions = await fetchQuestions(
        selectedCategory,
        problemType
      );
      setQuestions(fetchedQuestions); // 가져온 문제를 상태에 저장
      console.log("fetch data: ", fetchedQuestions);
      // 문제 목록과 함께 다음 페이지로 이동
      navigate(`/cs/${type}`, { state: { questions: fetchedQuestions } });
    } else {
      // 카테고리가 선택되지 않았을 경우 경고창을 표시합니다.
      alert("카테고리를 선택하세요.");
    }
  };

  // 카테고리 선택 핸들러
  const handleCategorySelect = (category: string) => {
    setSelectedCategory(category);
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay" onClick={close}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <p>주제 선택</p>
        <div className="category-container">
          {categories.map((category, index) => (
            <div
              key={index}
              className={`category-item ${
                selectedCategory === category ? "selected-category" : ""
              }`}
              onClick={() => handleCategorySelect(category)}
            >
              {transCategory(category)}
            </div>
          ))}
        </div>
        <button className="st-btn" onClick={startStudy}>
          시작
        </button>
      </div>
    </div>
  );
};

export default SelectTitleModal;
