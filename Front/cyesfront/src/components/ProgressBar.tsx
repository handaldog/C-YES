import React from "react";

interface ProgressBarProps {
  progress: number;
}

const ProgressBar: React.FC<ProgressBarProps> = ({ progress }) => {
  return (
    <div className="progress-bar" style={{ width: "300px", height: "20px" }}>
      <div
        style={{
          width: `${progress}%`,
          backgroundColor: progress >= 80 ? "red" : "blue",
          height: "100%",
        }}
      />
    </div>
  );
};

export default ProgressBar;
