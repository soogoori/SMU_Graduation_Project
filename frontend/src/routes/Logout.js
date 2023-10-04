import React from "react";
import styles from "../styles/css/Login.module.css";
import { useNavigate } from "react-router-dom";
import {KAKAO_LOGOUT_AUTH_URL} from "./Oauth";
function Logout({ onClose }) {
    //로그아웃 페이지
    const navigate = useNavigate();

    function logout(){

        localStorage.removeItem("token")

        // Kakao 로그아웃 페이지로 리디렉션
        window.location.href = KAKAO_LOGOUT_AUTH_URL;

        alert("로그아웃 되었습니다.");
        navigate("/home");

        {/*onClose();*/}
    }

    return (
        <div className={styles.logoutcontainer}>
            <img alt="" src={"img/임시로고.svg"} />
            <br></br>
            <br></br>
            <div className={styles.logoutAsk}> 로그아웃 하시겠습니까? </div>
            <br></br>
            <div>
                <button className={styles.no}
                        onClick={() => navigate(-1)}
                > 취소 </button>
                <button className={styles.yes}
                        onClick={logout}> 확인 </button>
            </div>
        </div>
    );
}

export default Logout;