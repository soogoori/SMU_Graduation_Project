// ShoeDetail.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import ReviewList from './ReviewList';
import WriteReviewForShoe from './WriteReviewForShoe';
import '../styles/css/ShoeDetail.css';
import StarRating from '../components/StarRating'
import profileImageDefault from "../assets/profileDefault.png"

const ShoeDetail = () => {
    const [shoe, setShoe] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const { shoeId } = useParams();
    const [isWritingReview, setIsWritingReview] = useState(false);
    const history = useNavigate();

    const fetchUserInformation = async (userId) => {
        try {
            const userResponse = await axios.get(`/api/users/${userId}`, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
            const { nickname, profileImage } = userResponse.data;
            return { nickname, profileImage };
        } catch (error) {
            console.error("Error fetching user information:", error);
            return { nickname: '', profileImage: '' };
        }
    };

    const processReviewsWithUserInformation = async (reviews) => {
        const processedReviews = [];
        for (const review of reviews) {
            const { author, ...rest } = review;
            const { nickname, profileImage } = await fetchUserInformation(author.id);
            const processedReview = { author: { nickname, profileImage }, ...rest };
            processedReviews.push(processedReview);
        }
        return processedReviews;
    };

    const fetchReviews = async () => {
        try {
            const reviewsResponse = await axios.get(`/api/shoes/${shoeId}/reviews`, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
            const { reviews } = reviewsResponse.data;
            const processedReviews = await processReviewsWithUserInformation(reviews);
            setReviews(processedReviews);
        } catch (error) {
            console.error("Error fetching reviews:", error);
        }
    };

    useEffect(() => {
        const fetchShoeDetail = async () => {
            try {
                const shoeResponse = await axios.get(`/api/shoes/${shoeId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });
                setShoe(shoeResponse.data);

                const reviewsResponse = await axios.get(`/api/shoes/${shoeId}/reviews`, {
                    headers: {
                        'Content-Type': 'application.json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });
                const { reviews } = reviewsResponse.data;
                const processedReviews = await processReviewsWithUserInformation(reviews);
                setReviews(processedReviews);

                setLoading(false);
            } catch (error) {
                console.error("Error fetching shoe detail:", error);
                setLoading(false);
            }
        };

        fetchShoeDetail();
    }, [shoeId, isWritingReview]);

    const handleReviewSubmit = () => {
        fetchReviews();
    };

    const handleWriteReviewClick = () => {
        setIsWritingReview(true);
    };

    const handleCloseModal = () => {
        setIsWritingReview(false);
    }

    if (loading) {
        return <div>Loading...</div>;
    }

    const AIReview = ({ name, productCode }) => (
        <div className="ai-review">
            <h2>AI Review</h2>
            <p>"{name}"({productCode}): 사이즈는 보통, 착화감은 편함, 발볼은 넓게 나옴.
            </p>
        </div>
    );

    const options = {
        fit: ['BIG', 'NORMAL', 'SMALL'],
        feeling: ['GOOD', 'NORMAL', 'BAD'],
        width: ['WIDE', 'NORMAL', 'NARROW'],
    };

    const renderHorizontalGaugeBar = (reviews, criterion) => {
        const criterionOptions = options[criterion];
        const totalReviews = reviews.length;

        return criterionOptions.map((option) => {
            const count = reviews.filter((review) => review[criterion] === option).length;
            const percentage = (count / totalReviews) * 100;

            return (
                <div key={option} className={`horizontal-gauge-bar horizontal-gauge-bar-${criterion.toLowerCase()}`}>
                    <p>{option}</p>
                    <div
                        className={`horizontal-gauge-bar-inner horizontal-gauge-bar-inner-${criterion.toLowerCase()}`}
                        style={{ width: `${percentage}%` }}
                    ></div>
                    <p>{`${percentage.toFixed(1)}%`}</p>
                </div>
            );
        });
    };

    return (
        <div className="detail-page-container">
            <section className="shoe-detail-container">
                <div className="shoe-detail-left">
                    {shoe ? (
                        <img src={shoe.image} alt={shoe.name} />
                    ) : (
                        <p>이 신발에 대한 정보를 불러올 수 없습니다.</p>
                    )}
                </div>

                <div className="shoe-detail-right">
                    <h2>{shoe ? shoe.name : '신발 정보를 불러올 수 없습니다.'}</h2>

                    <p>품번: {shoe ? shoe.productCode : '신발 정보를 불러올 수 없습니다.'}</p>
                    <p>별점: <StarRating rating={shoe ? shoe.avgRating : 0} /></p>

                    {isWritingReview ? (
                        <WriteReviewForShoe
                            shoeId={shoeId}
                            onReviewSubmit={() => {
                                setIsWritingReview(false);
                                fetchReviews(); // 리뷰를 다시 불러오기
                            }}
                            onClose={handleCloseModal} // 모달 닫기 이벤트 추가
                        />
                    ) : (
                        <>
                            <div><AIReview name={shoe.name} productCode={shoe.productCode} /></div>

                            {/* 각 기준에 대한 가로 게이지바 */}
                            <div className="horizontal-gauge-bars">
                                <h3>Size</h3>
                                {renderHorizontalGaugeBar(reviews, 'fit')}
                                <h3>Feeling</h3>
                                {renderHorizontalGaugeBar(reviews, 'feeling')}
                                <h3>Width</h3>
                                {renderHorizontalGaugeBar(reviews, 'width')}
                            </div>
                        </>
                    )}
                </div>
            </section>

            <section className="special-issue">
                <h2>스페셜 이슈</h2>
                <p>이 신발은 이번 시즌 특별한 이슈를 가지고 있습니다.
                    어떤 특징이 돋보이나요?</p>
            </section >

            <section className="reviews-section">
                <h2>전체 리뷰 ({reviews.length})</h2>
                <div>
                    <button onClick={handleWriteReviewClick}>리뷰 작성</button>
                </div>
                {reviews.length > 0 ? (
                    reviews.map((review) => (
                        <div key={review.id} className="review-item">
                            <div className="user-info">
                                <img
                                    className="user-profile-image"
                                    src={review.author.profileImage}
                                    alt={review.author.nickname}
                                />
                                <p className="user-name">{review.author.nickname}</p>
                            </div>
                            <div className="review-contents">
                                <p className="star-rating"><StarRating rating={review.rating} /></p>
                                <p className="size-info">사이즈: {review.size}</p>
                                <p className="review-content">{review.content}</p>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>리뷰가 없습니다.</p>
                )}
            </section>
        </div >
    );
};

export default ShoeDetail;