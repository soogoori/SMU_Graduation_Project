// UserShoes.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../styles/css/MyShoes.css'; // 같은 스타일을 사용하거나 UserShoes 전용 스타일을 만들 수 있습니다.

const UserShoes = ({ userId }) => {
    const [shoesData, setShoesData] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchUserShoes = async () => {
            try {
                const response = await axios.get(`/api/purchase/user/${userId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });
                const { purchase } = response.data;
                setShoesData(purchase);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching user shoes:', error);
                setLoading(false);
            }
        };

        fetchUserShoes();
    }, [userId]);

    return (
        <div>
            <h2 className="shoe-list-heading">보유 신발 목록</h2>
            {loading ? (
                <p className="loading-message">Loading...</p>
            ) : (
                <div className="shoe-list">
                    {shoesData.length > 0 ? (
                        shoesData.map((shoe) => (
                            <div key={shoe.id} className="shoe-item">
                                <p>이름: {shoe.name}</p>
                                <p>사이즈: {shoe.size}</p>
                                <p>핏: {shoe.fit}</p>
                            </div>
                        ))
                    ) : (
                        <p>보유 중인 신발이 없습니다.</p>
                    )}
                </div>
            )}
        </div>
    );
};

export default UserShoes;