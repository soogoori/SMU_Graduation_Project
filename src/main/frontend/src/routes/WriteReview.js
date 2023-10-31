// WriteReview.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import StarRating from '../components/StarRating';
import EditReviewModal from './EditReviewModal'; // 새로 추가한 모달 컴포넌트 import
import "../styles/css/WriteReview.css";

const WriteReview = ({ userId }) => {
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [editReviewId, setEditReviewId] = useState(null);
    const [deleteReviewId, setDeleteReviewId] = useState(null);
    const [deleteShoesId, setDeleteShoesId] = useState(null);

    useEffect(() => {
        fetchData();
    }, [userId]);

    const handleReviewUpdate = async () => {
        try {
            const response = await axios.get(`/api/users/${userId}/reviews`, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem('token')}`,
                },
            });
            const { hasNext, reviews } = response.data;
            setReviews(reviews);
        } catch (error) {
            console.error('리뷰 업데이트 오류:', error);
        }
    };

    const fetchData = async () => {
        try {
            const response = await axios.get(`/api/users/${userId}/reviews`, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem('token')}`,
                },
            });
            const { hasNext, reviews } = response.data;
            setReviews(reviews);
            setLoading(false);
        } catch (error) {
            console.error('리뷰 불러오기 오류:', error);
            setLoading(false);
        }
    };

    const openEditModal = (reviewId) => {
        setEditReviewId(reviewId);
    };

    const closeEditModal = () => {
        setEditReviewId(null);
    };

    const openDeleteModal = (reviewId, shoesId) => {
        setDeleteReviewId(reviewId);
        setDeleteShoesId(shoesId);
    };

    const closeDeleteModal = () => {
        setDeleteReviewId(null);
        setDeleteShoesId(null);
    };

    const handleDeleteReview = async () => {
        try {
            await axios.delete(`/api/${deleteShoesId}/reviews/${deleteReviewId}`, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem('token')}`,
                },
            });
            handleReviewUpdate();
            closeDeleteModal();
        } catch (error) {
            console.error('리뷰 삭제 오류:', error);
        }
    };

    return (
        <div className="write-review-container">
            <h2>내가 작성한 리뷰 ({reviews.length})</h2>
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
                                <div className="button-container">
                                    <button
                                        className="edit-button"
                                        onClick={() => openEditModal(review.id)}
                                    >
                                        수정
                                    </button>
                                    <button
                                        className="delete-button"
                                        onClick={() => openDeleteModal(review.id, review.shoes.id)}
                                    >
                                        삭제
                                    </button>
                                </div>

                                <p>신발 이름: {review.shoes.name}</p>
                                <p>제품 코드: {review.shoes.productCode}</p>
                                <p>
                                    <StarRating rating={review.rating} />
                                </p>
                                <p>리뷰 내용: {review.content}</p>

                                {editReviewId === review.id && (
                                    <EditReviewModal
                                        reviewId={editReviewId}
                                        shoesId={review.shoes.id}
                                        onClose={closeEditModal}
                                        onReviewUpdate={handleReviewUpdate}
                                    />
                                )}
                            </div>
                        </div>
                    ))}

                    {deleteReviewId && (
                        <div className="modal">
                            <div className="modal-content">
                                <h2>리뷰 삭제</h2>
                                <p>정말로 이 리뷰를 삭제하시겠습니까?</p>
                                <button onClick={handleDeleteReview}>확인</button>
                                <button onClick={closeDeleteModal}>취소</button>
                            </div>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};

export default WriteReview;