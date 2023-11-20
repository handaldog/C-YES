import React from "react";

type CircleProgressProps = {
  radius: number;
  stroke: string;
  progress: number;
};

const CircleProgress: React.FC<CircleProgressProps> = ({
  radius,
  stroke,
  progress,
}) => {
  const CIRCUMFERENCE = 2 * Math.PI * radius;

  return (
    <g transform={`rotate(-90 ${radius + 10} ${radius + 10})`}>
      <circle
        cx={radius + 10}
        cy={radius + 10}
        r={radius}
        fill="none"
        stroke="#D6CEFF"
        strokeWidth="20"
      />
      <circle
        cx={radius + 10}
        cy={radius + 10}
        r={radius}
        fill="none"
        stroke={stroke}
        strokeWidth="20"
        strokeDasharray={CIRCUMFERENCE.toString()}
        strokeDashoffset={(CIRCUMFERENCE - CIRCUMFERENCE * progress).toString()}
      />
    </g>
  );
};

export default CircleProgress;
