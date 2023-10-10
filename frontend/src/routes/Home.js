import React from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import Shoe from "../components/Shoe.js";
import "../styles/css/Home.css";
import logoImage from "../assets/logo.png"; // 로고 이미지 추가

class Home extends React.Component {
    state = {
        isLoading: true,
        shoes: [],
    };

    // 로컬 API에서 신발 정보를 가져오는 함수
    fetchShoesData = async () => {
        try {
            const response = await axios.get("/api/shoes", {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });

            const { hasNext, shoes } = response.data;
            this.setState({ hasNext, shoes, isLoading: false });
        } catch (error) {
            console.error("Error fetching data:", error);
            this.setState({ isLoading: false });
        }
    };

    componentDidMount() {
        this.fetchShoesData();
    }

    render() {
        const { isLoading, shoes } = this.state;

        return (
            <section className="home-container">
                {/* <div className="logo-container">
                    <Link to="/mypage">
                        <img src={logoImage} alt="Logo" className="logo" />
                    </Link>
                </div> */}

                <h1>인기있는 제품</h1>

                {isLoading ? (
                    <div className="loader">
                        <span className="loader_text">로딩 중...</span>
                    </div>
                ) : (
                    <div className="shoes">
                        {shoes.map((shoe) => (
                            <Link key={shoe.id} to={`/shoes/${shoe.id}`}>
                                {/* 링크를 클릭하면 해당 신발의 상세 페이지로 이동 */}
                                <Shoe
                                    id={shoe.id}
                                    brand={shoe.brand}
                                    name={shoe.name}
                                    productCode={shoe.productCode}
                                    image={shoe.image}
                                    avgRating={shoe.avgRating}
                                    reviewCnt={shoe.reviewCnt}
                                />
                            </Link>
                        ))}
                    </div>
                )}
            </section>
        );
    }
}

export default Home;