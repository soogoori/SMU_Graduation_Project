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

    // 리뷰 작성자의 닉네임과 프로필 이미지를 가져오는 함수
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

    // 리뷰 목록을 가공하여 작성자의 닉네임과 프로필 이미지를 추가
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
                        'Content-Type': 'application/json',
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


    const handleReviewSubmit = () => {
        // 리뷰를 다시 불러오기 위한 콜백을 실행합니다.
        fetchReviews();
    };

    const handleWriteReviewClick = () => {
        setIsWritingReview(true);
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    const AIReview = ({ name, productCode }) => (
        <div className="ai-review">
            <h2>AI Review</h2>
            <p>"{name}"({productCode}): 사이즈는 보통, 착화감은 편함, 발볼은 넓게 나옴.</p>
        </div>
    );

    return (
        <div className="detail-page-container">
            <section className="shoe-detail-container">
                <div className="shoe-detail-left">
                    <img src={shoe.image} alt={shoe.name} />
                </div>
                <div className="shoe-detail-right">
                    <h2>{shoe.name}</h2>
                    <p>품번: {shoe.productCode}</p>
                    <p>별점: <StarRating rating={shoe.avgRating} /></p>

                    {isWritingReview ? (
                        <WriteReviewForShoe
                            shoeId={shoeId}
                            onReviewSubmit={() => {
                                setIsWritingReview(false);
                                history.push(`/shoes/${shoeId}`,{
                                    headers: {
                                        'Content-Type': 'application/json',
                                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                                    }
                                });
                            }}
                        />
                    ) : (
                        <div><AIReview name={shoe.name} productCode={shoe.productCode} /></div>
                    )}
                </div>
            </section>
            <section className="reviews-section">
                <h2>전체 리뷰</h2>
                <div>
                    <button onClick={handleWriteReviewClick}>리뷰 작성</button>
                </div>
                {reviews.length > 0 ? (
                    reviews.map((review) => (
                        <div key={review.id} className="review-item">
                            <div className="user-info">
                                <img
                                    className="user-profile-image"
                                    src={review.author.profileImage|| profileImageDefault}
                                    alt={review.author.nickname}
                                />
                                <p className="user-name">{review.author.nickname}</p>
                            </div>

                            <p>{review.content}</p>
                        </div>
                    ))
                ) : (
                    <p>리뷰가 없습니다.</p>
                )}
            </section>
        </div>
    );
};

export default ShoeDetail;