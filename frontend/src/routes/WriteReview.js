// WriteReview.js
// WriteReview.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import StarRating from '../components/StarRating';
import EditReviewModal from './EditReviewModal';  // 새로 추가한 모달 컴포넌트 import

const WriteReview = ({ userId }) => {
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [editReviewId, setEditReviewId] = useState(null);  // 수정 중인 리뷰의 ID를 저장하는 state

    useEffect(() => {
        fetchData();  // 리뷰 데이터를 불러오는 함수 호출
    }, [userId]);

    const handleReviewUpdate = () => {
        fetchData();  // 리뷰가 업데이트되면 다시 데이터를 가져옴
    };

    const fetchData = async () => {
        try {
            const response = await axios.get(`/api/users/${userId}/reviews`, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
            const { hasNext, reviews } = response.data;
            setReviews(reviews);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching reviews:', error);
            setLoading(false);
        }
    };

    const openEditModal = (reviewId) => {
        setEditReviewId(reviewId);  // 수정 모달을 열 때 수정 중인 리뷰의 ID를 설정
    };

    const closeEditModal = () => {
        setEditReviewId(null);  // 모달을 닫을 때 수정 중인 리뷰의 ID를 초기화
    };

    return (
        <div className="write-review-container">
            <h2>내가 작성한 리뷰 ({reviews.length})</h2>
            {loading ? (
                <p>Loading...</p>
            ) : (
                <div>
                    {reviews.map((review) => (
                        <div key={review.id} className="review-box">
                            <div className="image-container">
                                <img src={review.shoes.image} alt="Shoe" />
                            </div>
                            <div className="review-details">
                                <div className="button-container">
                                    <button
                                        className="edit-button"
                                        onClick={() => openEditModal(review.id)}
                                    >
                                        수정
                                    </button>
                                    <button className="delete-button">삭제</button>
                                </div>

                                <p>신발 이름: {review.shoes.name}</p>
                                <p>제품 코드: {review.shoes.productCode}</p>
                                <p><StarRating rating={review.rating} /></p>
                                <p>리뷰 내용: {review.content}</p>
                            </div>
                        </div>
                    ))}
                    {/* 수정 중인 리뷰가 있을 때 모달 렌더링 */}
                    {editReviewId && (
                        <EditReviewModal
                            reviewId={editReviewId}
                            onClose={closeEditModal}
                            onReviewUpdate={handleReviewUpdate}
                        />
                    )}
                </div>
            )}
        </div>
    );
};

export default WriteReview;