import React from "react";
import "./AnswerResultComponent.css";

type Props = {
    answer: string;
    submit: string;
    correctNumber?: number;
    totalNumber?: number;
};

const AnswerResultComponent = ({
    answer,
    submit,
    correctNumber,
    totalNumber,
}: Props) => {
    return (
        <div className="answer-result-body">
            <div className="answer-result-title">정답은?</div>
            <div className="answer-result-answer">{answer}</div>
            <div
                className={
                    answer === submit
                        ? "answer-result-correct"
                        : "answer-result-fail"
                }
            >
                {answer === submit ? "맞았습니다!" : "틀렸습니다!"}
            </div>
            <div className="answer-result-ratio">{`총 ${totalNumber}명 중 ${correctNumber}명이 맞췄습니다`}</div>
        </div>
    );
};

export default AnswerResultComponent;
