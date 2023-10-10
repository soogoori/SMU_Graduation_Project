// WriteReview.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';

const WriteReview = ({ userId }) => {
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);

    const [userProfileImage, setUserProfileImage] = useState('');
    const [userNickname, setUserNickname] = useState('');
    const [userSize, setUserSize] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            try {
                // API로부터 리뷰 데이터 가져오기
                const response = await axios.get(`/api/users/me/reviews`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });
                const { hasNext, reviews } = response.data;

                // 사용자 리뷰 데이터 저장
                setReviews(reviews);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching reviews:', error);
                setLoading(false);
            }
        };

        const fetchUserProfile = async () => {
            try {
                const response = await axios.get(`/api/users/me`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });
                const { profileImage, nickname, size } = response.data;
                setUserProfileImage(profileImage);
                setUserNickname(nickname);
                setUserSize(size);
            } catch (error) {
                console.error('Error fetching user profile:', error);
            }
        };

        fetchUserProfile();
        fetchData();
    }, []);

    return (
        <div className="write-review-container">

            <h2>내가 작성한 리뷰 ({reviews.length})</h2>

            {loading ? (
                <p>Loading...</p>
            ) : (
                <div>
                    {reviews.map((review) => (
                        <div key={review.id} className="review-box">
                            <p>평가: {review.rating}</p>
                            <p>감정: {review.feeling}</p>
                            <p>신발 이름: {review.shoes.name}</p>
                            <p>브랜드: {review.shoes.brand}</p>
                            <p>제품 코드: {review.shoes.productCode}</p>
                            <p>평균 별점: {review.shoes.avgRating}</p>
                            {/* 기타 리뷰 정보 표시 */}
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default WriteReview;