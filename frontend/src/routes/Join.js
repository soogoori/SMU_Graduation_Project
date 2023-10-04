import React, {useEffect} from "react";
import styles from "../styles/css/Login.module.css";
import { useNavigate } from "react-router-dom";

function Join() {

    const navigate = useNavigate();

    useEffect((v) => {
            const token = new URL(window.location.href).searchParams.get("accessToken")

            if (token) {
                localStorage.setItem("token", token)
            }
        }
    )
    return (
        <div className={styles.joincontainer}>
            <br></br>
            <br></br>
            <div className={styles.loginSuccess}> 로그인 성공 👏 </div>
            <br></br>
            <div className={styles.loginHello}> 환영합니다! </div>
            <br></br>
            <div>
                <button className={styles.cont}
                        onClick={() => navigate('/home')}> 계속하기 </button>
            </div>
        </div>
    );
}

export default Join;