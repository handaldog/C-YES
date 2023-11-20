import React, { useState } from "react";
import "./SubmitOX.css";
import RoundCornerBtn from "../../components/RoundCornerBtn";

type Props = {
    isSubmitted: boolean;
    setIsSubmitted: Function;
    setSubmit: Function;
};

const SubmitOX = ({ isSubmitted, setIsSubmitted, setSubmit }: Props) => {
    const [selected, setSelected] = useState<boolean>();

    const submitSelect = (submit: string) => {
        if (!isSubmitted) {
            setSubmit(submit);
            setIsSubmitted(true);

            const selectBool: boolean = submit === "TRUE";
            setSelected(selectBool);
        }
    };

    return (
        <div className="submit-ox-button-content">
            <RoundCornerBtn
                onClick={() => submitSelect("TRUE")}
                bgcolor="#57FF5E"
                bghover="#39A63D"
                height="100px"
                width="150px"
                fontSize="40px"
                opacity={isSubmitted && selected === false ? 0.2 : 1}
            >
                True
            </RoundCornerBtn>
            <div style={{ marginRight: "30px" }}></div>
            <RoundCornerBtn
                onClick={() => submitSelect("FALSE")}
                bgcolor="#FF2A2A"
                bghover="#A61B1B"
                height="100px"
                width="150px"
                fontSize="40px"
                opacity={isSubmitted && selected === true ? 0.2 : 1}
            >
                False
            </RoundCornerBtn>
        </div>
    );
};

export default SubmitOX;
