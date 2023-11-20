import React, { useEffect, useRef, useState } from "react";
import "./SubmitSelection.css";

type Props = {
    selectionList: string[];
    isSubmitted: boolean;
    setIsSubmitted: Function;
    answer: string;
    setSubmit: Function;
};

const SubmitSelection = ({
    selectionList,
    isSubmitted,
    setIsSubmitted,
    answer,
    setSubmit,
}: Props) => {
    const [selected, setSelected] = useState(-1);
    const box1 = useRef<any>();

    const selectAnswer = (select: number) => {
        if (!isSubmitted) {
            setSubmit(selectionList[select]);
            setIsSubmitted(true);
            setSelected(select);
        }
    };

    useEffect(() => {
        setSelected(-1);
    }, [answer]);

    return (
        <div className="select-answer-container">
            <div className="select-answer-four">
                <div
                    ref={box1}
                    className={`select-answer-box-blue ${
                        isSubmitted && selected !== 0
                            ? "select-answer-box-incorrect"
                            : ""
                    }`}
                    onClick={() => {
                        selectAnswer(0);
                    }}
                >
                    {selectionList[0] || "X"}
                </div>
                <div
                    className={`select-answer-box-green ${
                        isSubmitted && selected !== 1
                            ? "select-answer-box-incorrect"
                            : ""
                    }`}
                    onClick={() => {
                        selectAnswer(1);
                    }}
                >
                    {selectionList[1] || "X"}
                </div>
            </div>
            <div className="select-answer-four">
                <div
                    className={`select-answer-box-yellow ${
                        isSubmitted && selected !== 2
                            ? "select-answer-box-incorrect"
                            : ""
                    }`}
                    onClick={() => {
                        selectAnswer(2);
                    }}
                >
                    {selectionList[2] || "X"}
                </div>
                <div
                    className={`select-answer-box-red ${
                        isSubmitted && selected !== 3
                            ? "select-answer-box-incorrect"
                            : ""
                    }`}
                    onClick={() => {
                        selectAnswer(3);
                    }}
                >
                    {selectionList[3] || "X"}
                </div>
            </div>
        </div>
    );
};

export default SubmitSelection;
