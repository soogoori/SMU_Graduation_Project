import React from "react";

const ReviewList = ({ reviews }) => {
    return (
        <ul className="reviews">
            {reviews.map((review) => (
                <li key={review.id}>
                    <p>{review.content}</p>
                    <p>평점: {review.rating}</p>
                    {/* 작성자 정보 표시 */}
                    {review.author && (
                        <div className="review-author-info">
                            <img src={review.author.profileImage} alt={`${review.author.nickname}'s profile`} />
                            <p>{review.author.nickname}</p>
                        </div>
                    )}
                    {/* 기타 리뷰 정보들을 출력 */}
                </li>
            ))}
        </ul>
    );
};

export default ReviewList;