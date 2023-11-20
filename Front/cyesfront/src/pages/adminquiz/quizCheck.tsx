import React, { useState, useEffect } from "react";
import axios from "axios";
import Pagination from "./pageNation"; // Pagination 컴포넌트를 import
import ShortInsert from "./quizShortInsert";
import OXInsert from "./quizOXInsert";
import FourSelectInsert from "./quizFourSelectInsert";

interface Quiz {
  content: Content[];
  pageable: Pageable;
  totalElements: number;
  totalPages: number;
}

interface Content {
  id: string;
  question: string;
  category: string;
}

interface Pageable {
  pageSize: number;
  pageNumber: number;
}

const QuizsList: React.FC = () => {
  const [quizs, setQuizs] = useState<Content[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const itemsPerPage = 2;
  const [quizShortInsert, setQuizShortInsert] = useState(false);
  const [trueFalseInsert, setTrueFalseInsert] = useState(false);
  const [fourSelectInsert, setFourSelectInsert] = useState(false);

  const fetchData = async () => {
    try {
      const response = await axios.get<Quiz>(
        `https://cyes.site/api/adminproblem/no-check-all?page=${currentPage}&size=${itemsPerPage}`
      );
      setQuizs(response.data.content);
      setTotalPages(response.data.totalPages);
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchData();
  }, [currentPage]);

  const handlePageChange = (newPage: number) => {
    setCurrentPage(newPage);
  };

  const quizShortInserttClick = () => {
    setQuizShortInsert(true);
    setTrueFalseInsert(false);
    setFourSelectInsert(false);
  };

  const trueFalseInsertClick = () => {
    setQuizShortInsert(false);
    setTrueFalseInsert(true);
    setFourSelectInsert(false);
  };

  const fourSelectInsertClick = () => {
    setQuizShortInsert(false);
    setTrueFalseInsert(false);
    setFourSelectInsert(true);
  };

  const deleteQuiz = async (quizId: string) => {
    await axios
      .get(`https://cyes.site/api/adminproblem/noCheck/delete?id=${quizId}`)
      .then((response) => {
        console.log("id: ", quizId);
        console.log("문제 삭제 성공:", response.data);
        fetchData(); // 삭제 후 데이터 다시 불러오기
      })
      .catch((error) => {
        console.error("문제 삭제 중 오류:", error);
      });
  };

  return (
    <div>
      <h1>Quiz List</h1>
      <table className="quizCheck">
        <thead>
          <tr>
            <th>Question</th>
            <th>Category</th>
            <th>여부</th>
          </tr>
        </thead>
        <tbody>
          {quizs.map((quiz) => (
            <tr key={quiz.id}>
              <td>{quiz.question}</td>
              <td>{quiz.category}</td>
              <td>
                <button
                  onClick={() => {
                    console.log("Clicked on quiz with ID:", quiz.id);
                    deleteQuiz(quiz.id);
                  }}
                >
                  삭제
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={handlePageChange}
        displayRange={3}
      />

      <div className="button-container">
        <button onClick={quizShortInserttClick}>단답형</button>
        <button onClick={trueFalseInsertClick}>O/X형</button>
        <button onClick={fourSelectInsertClick}>4지선다</button>
      </div>

      {quizShortInsert && <ShortInsert />}
      {trueFalseInsert && <OXInsert />}
      {fourSelectInsert && <FourSelectInsert />}
    </div>
  );
};

export default QuizsList;
