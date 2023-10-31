// Product.js
import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Shoe from "../components/Shoe";
import "../styles/css/Product.css";
//import "../styles/css/Shoe.css";
const Product = () => {
    const [brand, setBrand] = useState("Nike");
    const [shoes, setShoes] = useState([]);
    const [selectedBrand, setSelectedBrand] = useState("Adidas"); // 새로운 상태 변수 추가
    const brands = ["Adidas", "Salomon SportStyle", "Vans", "Converse", "New Balance", "Reebok", "Asics", "ETC"];

    useEffect(() => {
        const fetchShoesData = async () => {
            try {
                const response = await axios.get(`/api/shoes/category/${brand}`,{
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });

                const { hasNext, shoes } = response.data;
                setShoes(shoes);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchShoesData();
    }, [brand]);

    // 버튼 클릭 시 선택된 브랜드 업데이트
    const handleBrandClick = (b) => {
        setBrand(b);
        setSelectedBrand(b);
    };

    return (
        <section className="product-container">
            <div className="brand-buttons">
                {brands.map((b) => (
                    <button key={b}
                            onClick={() => handleBrandClick(b)}
                            className={selectedBrand === b ? "selected" : ""} // 선택된 버튼에 클래스 추가
                    >
                        {b}
                    </button>
                ))}
            </div>

            <div className="shoes">
                {shoes.map((shoe) => (
                    <Link to={`/shoes/${shoe.id}`} key={shoe.id}>
                        <Shoe
                            id={shoe.id}
                            brand={shoe.brand}
                            name={shoe.name}
                            image={shoe.image}
                            avgRating={shoe.avgRating}
                            reviewCnt={shoe.reviewCnt}
                        />
                    </Link>
                ))}
            </div>
        </section>
    );
};

export default Product;