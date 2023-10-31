import React from "react";
import { Link, useLocation } from "react-router-dom";
import "../styles/css/Navbar.css";

function Navbar() {
    const location = useLocation();

    // 로그인 상태 여부를 확인하는 함수
    const isLoggedIn = !!localStorage.getItem("token");

    // 현재 경로가 "/start" 또는 "/login"인 경우 Navbar를 숨깁니다.
    if (location.pathname === "/" || location.pathname === "/login") {
        return null;
    }

    return (
        <nav className="navbar">
            <div className="navbar-left">
                <Link to="/home" >SHOEWISE</Link>
            </div>
            <div className="navbar-right">
                <Link to="/shoes">Product</Link>
                <Link to="/mypage">Mypage</Link>
                {isLoggedIn ? (
                    // 로그인 상태일 때만 "Logout" 링크를 보여줍니다.
                    <Link to="/logout">Logout</Link>
                ) : (
                    // 로그인 상태가 아닐 때는 "Login" 링크를 보여줍니다.
                    <Link to="/login">Login</Link>
                )}
            </div>
        </nav>
    );
}

export default Navbar;