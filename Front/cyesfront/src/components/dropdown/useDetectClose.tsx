import { useEffect, useState, MutableRefObject } from "react";

type Props = {
  elem: MutableRefObject<HTMLElement | null>;
  initialState: boolean;
};

const useDetectClose = (props: Props) => {
  const { elem, initialState } = props;
  const [isOpen, setIsOpen] = useState(initialState);

  useEffect(() => {
    const onClick = (e: MouseEvent) => {
      if (elem.current !== null && !elem.current.contains(e.target as Node)) {
        setIsOpen(!isOpen);
      }
    };

    if (isOpen) {
      window.addEventListener("click", onClick);
    }

    return () => {
      window.removeEventListener("click", onClick);
    };
  }, [isOpen, elem]);

  return [isOpen, setIsOpen] as const;
};

export default useDetectClose;
