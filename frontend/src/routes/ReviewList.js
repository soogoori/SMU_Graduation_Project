import React from "react";

const ReviewList = ({ reviews }) => {
    return (
        <ul className="reviews">
            {reviews.map((review) => (
                <li key={review.id}>
                    <p>{review.content}</p>
                    <p>평점: {review.rating}</p>
                    {/* 기타 리뷰 정보들을 출력 */}
                </li>
            ))}
        </ul>
    );
};

export default ReviewList;