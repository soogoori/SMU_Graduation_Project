// WriteReviewForShoe.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';  // useHistory 추가
import '../styles/css/WriteReviewForShoe.css';

const WriteReviewForShoe = ({ shoeId, onReviewSubmit, onClose }) => {
    const [shoeInfo, setShoeInfo] = useState(null);
    const [rating, setRating] = useState(0);
    const [fit, setFit] = useState('');
    const [feeling, setFeeling] = useState('');
    const [width, setWidth] = useState('');
    const [reviewContent, setReviewContent] = useState('');

    useEffect(() => {
        const fetchShoeInfo = async () => {
            try {
                // 신발 정보 가져오기
                const shoeResponse = await axios.get(`/api/shoes/${shoeId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });
                setShoeInfo(shoeResponse.data);
            } catch (error) {
                console.error('Error fetching shoe information:', error);
            }
        };

        fetchShoeInfo();
    }, [shoeId]);

    const handleReviewSubmit = async (event) => {
        event.preventDefault();

        try {
            // 백엔드로 리뷰를 전송합니다.
            await axios.post(`/api/shoes/${shoeId}/reviews`, {
                rating,
                fit,
                feeling,
                width,
                content: reviewContent,
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });

            // 폼을 초기화합니다.
            setRating(0);
            setFit('');
            setFeeling('');
            setWidth('');
            setReviewContent('');

            // 리뷰를 다시 불러오기 위한 콜백을 실행합니다.
            onReviewSubmit();
        } catch (error) {
            console.error('Error submitting review:', error);
        }
    };

    if (!shoeInfo) {
        return <div>Loading...</div>;
    }

    return (
        <div className="write-review-for-shoe">
            <div className="close-button" onClick={onClose}>
                &times;
            </div>
            <h3>리뷰 작성</h3>
            <div>
                <img src={shoeInfo.image} alt={shoeInfo.name} />
                <p>{shoeInfo.name}</p>
                <p>품번: {shoeInfo.productCode}</p>
            </div>

            <form onSubmit={handleReviewSubmit}>
                {/* 별점 입력 */}
                <label htmlFor="rating">별점:</label>
                <input
                    type="number"
                    id="rating"
                    value={rating}
                    onChange={(e) => setRating(Number(e.target.value))}
                    min="0"
                    max="5"
                />

                {/* Fit 입력 */}
                <label htmlFor="fit">사이즈:</label>
                <select id="fit" value={fit} onChange={(e) => setFit(e.target.value)}>
                    <option value="CHOICE">선택하기</option>
                    <option value="BIG">크게 나옴</option>
                    <option value="NORMAL">보통</option>
                    <option value="SMALL">작게 나옴</option>
                </select>

                {/* Feeling 입력 */}
                <label htmlFor="feeling">착화감:</label>
                <select id="feeling" value={feeling} onChange={(e) => setFeeling(e.target.value)}>
                    <option value="CHOICE">선택하기</option>
                    <option value="GOOD">편함</option>
                    <option value="NORMAL">보통</option>
                    <option value="BAD">불편함</option>
                </select>

                {/* Width 입력 */}
                <label htmlFor="width">발볼:</label>
                <select id="width" value={width} onChange={(e) => setWidth(e.target.value)}>
                    <option value="CHOICE">선택하기</option>
                    <option value="WIDE">넓게 나옴</option>
                    <option value="NORMAL">보통</option>
                    <option value="NARROW">좁게 나옴</option>
                </select>

                {/* 리뷰 내용 입력 */}
                <label htmlFor="reviewContent">리뷰 작성하기:</label>
                <textarea
                    id="reviewContent"
                    value={reviewContent}
                    onChange={(e) => setReviewContent(e.target.value)}
                />

                {/* 제출 버튼 */}
                <button type="submit">리뷰 작성 완료</button>
            </form>
        </div>
    );
};

export default WriteReviewForShoe;