import React, { useEffect, useState } from "react"
import "../styles/css/Start.css";
import logo from "../../src/assets/shoewiseLogoB.png"
import {Link} from "react-router-dom";

function Start() {

    return (
        <div className="start-container">
            <img src={logo} alt="SHOEWISE 로고" className="logo" />
            <h1>SHOEWISE</h1>
            <p>슈와이즈로 리뷰를 확인하고, 발에 딱 맞는 신발을 찾아보세요 !</p>

            <Link to="/login" className="start-button">
                시작하기
            </Link>
        </div>
    );
}

export default Start;