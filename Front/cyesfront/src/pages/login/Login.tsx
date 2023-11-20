import React from "react";
import "./Login.css";

const KAKAO_AUTH_URI = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_KAKAO_API_KEY}
&redirect_uri=${process.env.REACT_APP_KAKAO_REDIRECT_URI}&response_type=code`;

const Login = () => {
  const handleKakaoLogin = () => {
    window.location.href = KAKAO_AUTH_URI;
  };

  return (
    <div className="login-container">
      <img className="logo-img" src="/img/cyes_logo_img.png" alt="logo img" />
      <button onClick={handleKakaoLogin} className="login-button kakao-login" />
    </div>
  );
};

export default Login;
