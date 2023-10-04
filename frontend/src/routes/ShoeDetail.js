import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router";
import ReviewList from "./ReviewList";
// import "./ShoeDetail.css"; // ShoeDetail 컴포넌트에 대한 스타일링을 위한 CSS 파일

const ShoeDetail = () => {
    const [shoe, setShoe] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const { shoeId } = useParams();

    useEffect(() => {
        const fetchShoeDetail = async () => {
            try {
                // 신발 상세 정보 가져오기
                const shoeResponse = await axios.get(`/api/shoes/${shoeId}`,{
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });


                setShoe(shoeResponse.data);

                // 리뷰 목록 가져오기
                const reviewsResponse = await axios.get(`/api/shoes/${shoeId}/reviews`,
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${localStorage.getItem("token")}`
                        }
                    });
                const { reviews } = reviewsResponse.data;
                setReviews(reviews);

                setLoading(false);
            } catch (error) {
                console.error("Error fetching shoe detail:", error);
                setLoading(false);
            }
        };

        fetchShoeDetail();

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [shoeId]);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <section className="shoe-detail-container">
            <div className="shoe-detail-left">
                <img src={shoe.image} alt={shoe.name} />
            </div>
            <div className="shoe-detail-right">
                <h2>{shoe.name}</h2>
                <p>품번: {shoe.productCode}</p>
                <p>별점: {shoe.avgRating}</p>

                {reviews.length > 0 ? (
                    <>
                        <h3>리뷰</h3>
                        <ReviewList reviews={reviews} />
                    </>
                ) : (
                    <p>리뷰가 없습니다.</p>
                )}
            </div>
        </section>
    );
};

export default ShoeDetail;