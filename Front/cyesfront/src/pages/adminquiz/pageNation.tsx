import React from "react";
import "./pageNation.css";

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (newPage: number) => void;
  displayRange: number;
}

const Pagination: React.FC<PaginationProps> = ({
  currentPage,
  totalPages,
  onPageChange,
  displayRange,
}) => {
  const pageNumbers = Array.from(
    { length: totalPages },
    (_, index) => index + 1
  );

  const currentBlock = Math.floor((currentPage - 1) / displayRange);
  const startIndex = currentBlock * displayRange + 1;
  const endIndex = Math.min(startIndex + displayRange, totalPages);

  const displayedPageNumbers = pageNumbers.slice(startIndex - 1, endIndex);

  return (
    <div className="horizontal-pagination">
      <ul>
        <button
          onClick={() => onPageChange(currentPage - 1)}
          className={currentPage === 1 ? "disabled" : ""}
        >
          Prev
        </button>
        {displayedPageNumbers.map((page) => (
          <li
            className="selections"
            key={page}
            onClick={() => onPageChange(page)}
          >
            <button className={page === currentPage ? "active" : ""}>
              {page}
            </button>
          </li>
        ))}
        <button
          onClick={() => onPageChange(currentPage + 1)}
          className={currentPage === totalPages ? "disabled" : ""}
        >
          Next
        </button>
      </ul>
    </div>
  );
};

export default Pagination;
