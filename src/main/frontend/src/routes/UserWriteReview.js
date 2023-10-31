import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "../styles/css/WriteReview.css";
import StarRating from '../components/StarRating';

const UserWriteReview = ({ userId }) => {
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchUserReviews = async () => {
            try {
                const response = await axios.get(`/api/users/${userId}/reviews`, {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`,
                    },
                });
                const { reviews } = response.data;
                setReviews(reviews);
                setLoading(false);
            } catch (error) {
                console.error('리뷰 가져오기 오류:', error);
                setLoading(false);
            }
        };

        fetchUserReviews();
    }, [userId]);

    return (
        <div className="write-review-container">
            <h2>유저가 작성한 리뷰 ({reviews.length})</h2>
            {loading ? (
                <p>로딩 중...</p>
            ) : (
                <div>
                    {reviews.map((review) => (
                        <div key={review.id} className="review-box">
                            <div className="image-container">
                                <img src={review.shoes.image} alt="신발" />
                            </div>
                            <div className="review-details">
                                <p>신발 이름: {review.shoes.name}</p>
                                <p>제품 코드: {review.shoes.productCode}</p>
                                <p>제품 사이즈 {review.shoes.size}</p>
                                <p>
                                    <StarRating rating={review.rating} />
                                </p>
                                <p>리뷰 내용: {review.content}</p>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default UserWriteReview;