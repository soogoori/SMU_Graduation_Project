import React, { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom"; // Link와 useNavigate 추가
//import "./Login.css";
import logoImage from "../assets/logo.png";
import loginImage from "../assets/kakao_login.png";
import {KAKAO_AUTH_URL} from "./Oauth"; // 로고 이미지 추가
import {KAKAO_LOGOUT_AUTH_URL} from "./Oauth";
function Login() {
    const navigate = useNavigate(); // useNavigate 훅 사용

    const isLoggedIn = !!localStorage.getItem("token");

    useEffect(() => {
        // 카카오와 네이버 SDK 초기화 코드를 여기에 작성합니다 (필요한 경우).
    }, []);

    const kakaoLogin = () => {
        // 카카오 로그인 처리 로직을 여기에 구현합니다.
    };

    return (
        <div className="login-container">
            <header className="login-header">
                <img src={logoImage} alt="SHOEWISE 로고" className="logo" />
            </header>
            <ul>
                {isLoggedIn ?( <li onClick={kakaoLogin}>
                    <a href={KAKAO_LOGOUT_AUTH_URL}>
                        <span>카카오 로그아웃</span>
                    </a>
                </li>) :<li onClick={kakaoLogin}>
                    <a href="http://localhost:8080/oauth2/authorization/kakao">
                        <img src={loginImage} alt="Kakao 로그인" className="loginButton" />
                    </a>
                </li>}

            </ul>
            <Link to="/" className="start-button"> {/* 이 부분 수정 */}
                홈으로
            </Link>
        </div>
    );
}

export default Login;
