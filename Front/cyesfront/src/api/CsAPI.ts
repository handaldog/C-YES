import axios from "axios";

const csApi = axios.create({
  baseURL: process.env.REACT_APP_SPRING_URI,
  headers: { "content-type": "application/json" },
  timeout: 3000,
});

export const getCsCategory = async () => {
  const data = await csApi
    .get(`/api/problem/category`)
    .then((resp) => {
      return resp.data;
    })
    .catch((err) => {
      console.log("getCategory error: ", err);
      return null;
    });

  return data;
};
