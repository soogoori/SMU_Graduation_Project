// EditReviewModal.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "../styles/css/EditReviewModal.css";


const EditReviewModal = ({ reviewId, shoesId, onClose, onReviewUpdate }) => {
    const [editedContent, setEditedContent] = useState('');
    const [editedRating, setEditedRating] = useState(0);
    const [editedSize, setEditedSize] = useState(0);
    const [editedFit, setEditedFit] = useState("CHOICE");
    const [editedFeeling, setEditedFeeling] = useState("CHOICE");
    const [editedWidth, setEditedWidth] = useState("CHOICE");

    useEffect(() => {
        const fetchReviewData = async () => {
            try {
                const response = await axios.get(`/api/reviews/${reviewId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`,
                    },
                });
                setEditedContent(response.data.content);
                setEditedRating(response.data.rating);
                setEditedSize(response.data.size);
                setEditedFit(response.data.fit);
                setEditedFeeling(response.data.feeling);
                setEditedWidth(response.data.width);
            } catch (error) {
                console.error('Error fetching review data:', error);
            }
        };

        fetchReviewData();
    }, [reviewId]);

    const handleUpdateReview = async () => {
        try {
            await axios.put(`/api/${shoesId}/reviews/${reviewId}`, {
                content: editedContent,
                rating: editedRating,
                size: editedSize,
                fit: editedFit,
                feeling: editedFeeling,
                width: editedWidth
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem('token')}`,
                },
            });
            onReviewUpdate();
            onClose();
        } catch (error) {
            console.error('Error updating review:', error);
        }
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <h2>리뷰 수정</h2>

                {/* 별점 입력 */}
                <label htmlFor="editedRating">별점:</label>
                <input
                    type="number"
                    id="editedRating"
                    value={editedRating}
                    onChange={(e) => setEditedRating(Number(e.target.value))}
                    min="0"
                    max="5"
                />

                {/* 사이즈 입력 */}
                <label htmlFor="editedSize">신발 사이즈:</label>
                <input
                    type="number"
                    id="editedSize"
                    value={editedSize}
                    onChange={(e) => setEditedSize(Number(e.target.value))}
                />

                {/* Fit 입력 */}
                <label htmlFor="editedFit">사이즈:</label>
                <select id="editedFit" value={editedFit} onChange={(e) => setEditedFit(e.target.value)}>
                    <option value="CHOICE">선택하기</option>
                    <option value="BIG">크게 나옴</option>
                    <option value="NORMAL">보통</option>
                    <option value="SMALL">작게 나옴</option>
                </select>

                {/* Feeling 입력 */}
                <label htmlFor="editedFeeling">착화감:</label>
                <select id="editedFeeling" value={editedFeeling} onChange={(e) => setEditedFeeling(e.target.value)}>
                    <option value="CHOICE">선택하기</option>
                    <option value="GOOD">편함</option>
                    <option value="NORMAL">보통</option>
                    <option value="BAD">불편함</option>
                </select>

                {/* Width 입력 */}
                <label htmlFor="editedWidth">발볼:</label>
                <select id="editedWidth" value={editedWidth} onChange={(e) => setEditedWidth(e.target.value)}>
                    <option value="CHOICE">선택하기</option>
                    <option value="WIDE">넓게 나옴</option>
                    <option value="NORMAL">보통</option>
                    <option value="NARROW">좁게 나옴</option>
                </select>

                {/* 내용 입력 */}
                <label htmlFor="editedContent">리뷰 내용:</label>
                <textarea
                    id="editedContent"
                    value={editedContent}
                    onChange={(e) => setEditedContent(e.target.value)}
                />

                <button onClick={handleUpdateReview}>완료</button>
                <button onClick={onClose}>닫기</button>
            </div>
        </div>
    );
};

export default EditReviewModal;