import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom"; // Link와 useNavigate 추가
import styles from "../styles/css/Login.module.css";
import logoImage from "../assets/shoewiseLogoB.png";
import loginImage from "../assets/kakao_login.png";
import {KAKAO_AUTH_URL} from "./Oauth"; // 로고 이미지 추가
import {KAKAO_LOGOUT_AUTH_URL} from "./Oauth";
import Logout from "./Logout"; // Logout 컴포넌트 추가

import "../styles/css/Login.module.css"
function Login() {

    const navigate = useNavigate(); // useNavigate 훅 사용

    const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem("token")); // 로그인 상태 관리
    const [showLogout, setShowLogout] = useState(false); // 로그아웃 컴포넌트 표시 여부 상태 추가

    useEffect(() => {
        // 카카오와 네이버 SDK 초기화 코드를 여기에 작성합니다 (필요한 경우).
    }, []);

    const kakaoLogin = () => {
        // 카카오 로그인 처리 로직을 여기에 구현합니다.
    };

    const handleKakaoLogout = () => {
        // 로그아웃 버튼을 눌렀을 때 호출되는 함수
        //setIsLoggedIn(false); // 로그아웃 상태로 변경
        setShowLogout(true); // 로그아웃 컴포넌트를 표시
    };

    return (
        <div className={styles.loginContainer}>
            <header className="loginHeader">
                <img src={logoImage} alt="SHOEWISE 로고" className={styles.loginLogo} />
            </header>
            <ul>
                {isLoggedIn ?(
                    // 로그인 상태에 따라 버튼 표시 여부 결정
                    <li onClick={handleKakaoLogout} className={styles.logoutButton}>
                        <span>카카오 로그아웃</span>
                    </li>
                ) :(
                    <li onClick={kakaoLogin}>
                        <a href="http://localhost:8080/oauth2/authorization/kakao">
                            <img src={loginImage} alt="Kakao 로그인" className="loginButton" />
                        </a>
                    </li>
                )}

            </ul>

            {/* isLoggedIn이 true일 때만 홈으로 가기 버튼 표시 */}
            {isLoggedIn && (
                <Link to="/home" className={styles.homeButton}>
                    홈으로 가기
                </Link>
            )}

            {showLogout && <Logout />}
        </div>
    );
}

export default Login;
