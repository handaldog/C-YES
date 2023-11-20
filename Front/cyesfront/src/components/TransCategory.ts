export const transCategory = (inputStr: string): string => {
  if (inputStr === "DB") {
    return "데이터베이스";
  } else if (inputStr === "NETWORK") {
    return "네트워크";
  } else if (inputStr === "OS") {
    return "운영체제";
  } else if (inputStr === "DATASTRUCTURE") {
    return "자료구조";
  } else if (inputStr === "ALGORITHM") {
    return "알고리즘";
  } else if (inputStr === "DESIGNPATTERN") {
    return "디자인패턴";
  } else if (inputStr === "COMPUTERARCHITECTURE") {
    return "컴퓨터구조";
  } else {
    return inputStr;
  }
};
