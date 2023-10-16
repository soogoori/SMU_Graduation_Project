// EditReviewModal.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';

const EditReviewModal = ({ reviewId, onClose, onReviewUpdate }) => {
    const [editedContent, setEditedContent] = useState('');

    useEffect(() => {
        // 리뷰 정보를 불러오는 API 호출 (리뷰 내용을 수정하는 경우에 필요)
        const fetchReviewData = async () => {
            try {
                const response = await axios.get(`/api/reviews/${reviewId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });
                setEditedContent(response.data.content);
            } catch (error) {
                console.error('Error fetching review data:', error);
            }
        };

        fetchReviewData();
    }, [reviewId]);

    const handleUpdateReview = async () => {
        try {
            // 리뷰 수정을 위한 API 호출
            await axios.put(`/api/reviews/${reviewId}`, { content: editedContent }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
            // 부모 컴포넌트에 수정 완료를 알리기
            onReviewUpdate();
            // 모달 닫기
            onClose();
        } catch (error) {
            console.error('Error updating review:', error);
        }
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <h2>리뷰 수정</h2>
                <textarea
                    value={editedContent}
                    onChange={(e) => setEditedContent(e.target.value)}
                />
                <button onClick={handleUpdateReview}>수정 완료</button>
                <button onClick={onClose}>닫기</button>
            </div>
        </div>
    );
};

export default EditReviewModal;