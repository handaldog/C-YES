import React from "react";
import BottomNav from "../../components/bottomnav/BottomNav";
import Dropdown from "../../components/dropdown/Dropdown";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import "./GroupQuiz.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { getCsCategory } from "../../api/CsAPI";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

type Props = {};
type QuizInfo = {
  quizName: string;
  quizType: string;
  quizSubject: string;
  quizNumber: number;
  quizStartTime: string;
};

const GroupQuiz = (props: Props) => {
  const types = ["TF 퀴즈", "객관식", "단답형"];
  const [categories, setCategories] = useState<string[]>([]);

  const [quizName, setQuizName] = useState<string>("");
  const [quizType, setQuizType] = useState("");
  const [quizSubject, setQuizSubject] = useState("");
  const [number, setNumber] = useState<number>(0);
  const [selectedDate, setSelectedDate] = useState<Date | null>(new Date());
  const navigate = useNavigate();

  const CancelButtonClick = () => {
    navigate("/group");

    // history.push("/nextpage", quizInfo);
  };
  const NextButtonClick = () => {
    const quizInfo: QuizInfo = {
      quizName,
      quizType,
      quizSubject,
      quizNumber: number,
      quizStartTime: formatDate(selectedDate),
    };
    if (number <= 0) {
      alert("문제 갯수는 최소 1개이상 가능합니다.");
      setNumber(1);
    } else if (quizName == "") {
      alert("퀴즈 제목을 작성하세요.");
    } else if (quizType == "") {
      alert("문제 유형을 선택하세요.");
    } else if (quizSubject == "") {
      alert("퀴즈 과목을 선택하세요.");
    } else {
      navigate("/group/create/detail", { state: quizInfo });
    }
  };

  // API
  const fetchCategories = async () => {
    const data = await getCsCategory(); // API 호출
    if (data) {
      setCategories(data);
    }
  };

  // useEffect
  useEffect(() => {
    fetchCategories();
  }, []);

  const handleDateChange = (date: Date | null) => {
    setSelectedDate(date);
  };

  const formatDate = (date: Date | null) => {
    if (date) {
      const offset = 1000 * 60 * 60 * 9;
      const koreaNow = new Date(date.getTime() + offset);
      koreaNow.setSeconds(0, 0);

      return koreaNow.toISOString();
    }
    return "";
  };

  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const input = event.target.value;
    setQuizName(input);
  };
  const handleTypeChange = (selectedValue: string) => {
    setQuizType(selectedValue);
  };

  const handleSubjectChange = (selectedValue: string) => {
    setQuizSubject(selectedValue);
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const input = event.target.value;
    const numberInput = Number(input);

    // 음수인 경우 0으로 설정
    if (numberInput < 0) {
      setNumber(0);
    } else if (numberInput > 20) {
      // 20보다 큰 경우 경고 메시지 표시 후 10으로 설정
      alert("문제 갯수는 최대 20개까지 가능합니다.");
      setNumber(10);
    } else if (!isNaN(numberInput)) {
      // 숫자인 경우 상태 업데이트
      setNumber(numberInput);
    }
  };

  return (
    <div className="live-container">
      <div className="content">
        <div className="group-content">
          <div className="group-title">그룹 퀴즈 생성 페이지</div>
        </div>
        <div>
          <div className="dropdown-content">
            <div>퀴즈이름</div>
            <div>
              <input
                className="textarea"
                value={quizName}
                onChange={handleTitleChange}
                maxLength={6}
              />
            </div>
          </div>
          <div className="dropdown-content">
            <div>문제유형</div>
            <div>
              <Dropdown items={types} onChange={handleTypeChange} />
            </div>
          </div>

          <div className="dropdown-content">
            <div>퀴즈과목</div>
            <div>
              <Dropdown items={categories} onChange={handleSubjectChange} />
            </div>
          </div>
          <div className="dropdown-content">
            <div>문제 갯수</div>
            <div>
              <input
                className="textarea"
                value={number}
                onChange={handleInputChange}
                type="number"
              />
            </div>
          </div>
          <div className="date-content">
            <div>시작 시간</div>
            <DatePicker
              selected={selectedDate}
              onChange={handleDateChange}
              showTimeSelect
              dateFormat="yyyy-MM-dd HH:mm"
              timeFormat="HH:mm:ss"
              className="custom-datepicker"
            />
          </div>

          <br />
          <br />
          <div className="group-button-content">
            <RoundCornerBtn onClick={CancelButtonClick}>취소</RoundCornerBtn>
            <div className="box"></div>
            <RoundCornerBtn onClick={NextButtonClick}>다음</RoundCornerBtn>
          </div>
        </div>
      </div>
      <BottomNav checkCS={false} checkLive={false} checkGroup={true} />
    </div>
  );
};
export default GroupQuiz;
