// Shoe.js (가정: 신발 정보를 표시하는 컴포넌트)
import React from "react";
import "../styles/css/Shoe.css";

const Shoe = ({ id, brand, name, productCode, image, avgRating, reviewCnt }) => {
    return (
        <div className="shoe-card">
            <img src={image} alt={`${brand} - ${name}`} />
            <div className="shoe-details">
                <h2>{brand}</h2>
                <p>{name}</p>
                <p>{productCode}</p>
                <p>평점: {avgRating}</p>
                <p>리뷰 수: {reviewCnt}</p>
            </div>
        </div>
    );
};

export default Shoe;