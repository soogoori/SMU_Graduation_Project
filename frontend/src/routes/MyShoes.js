import React from 'react';

const MyShoes = ({ shoesData, loading }) => {
    return (
        <div>
            <h2>보유 신발 목록</h2>
            {loading ? (
                <p>Loading...</p>
            ) : (
                <div>
                    {shoesData.map((shoe) => (
                        <div key={shoe.id}>
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