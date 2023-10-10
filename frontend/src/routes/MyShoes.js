// MyShoes.js

import React from 'react';
import '../styles/css/MyShoes.css';

const MyShoes = ({ shoesData, loading }) => {
    return (
        <div>
            <h2>보유 신발 목록</h2>
            {loading ? (
                <p className="loading-message">Loading...</p>
            ) : (
                <div>
                    {shoesData.map((shoe) => (
                        <div key={shoe.id} className="shoe-item">
                            <p>이름: {shoe.name}</p>
                            <p>사이즈: {shoe.size}</p>
                            <p>핏: {shoe.fit}</p>
                            {/* 추가적인 신발 정보 표시 */}
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default MyShoes;