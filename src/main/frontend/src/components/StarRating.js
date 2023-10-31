// StarRating.js

import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar, faStarHalf } from '@fortawesome/free-solid-svg-icons';

const StarRating = ({ rating }) => {
    const stars = [];
    const roundedRating = Math.round(rating * 2) / 2; // 반올림하여 0.5 단위로 평점 계산

    for (let i = 0; i < 5; i++) {
        if (i < roundedRating - 0.5) {
            stars.push(<FontAwesomeIcon icon={faStar} color="gold" key={i} />);
        } else if (i === Math.floor(roundedRating - 0.5)) {
            stars.push(<FontAwesomeIcon icon={faStarHalf} color="gold" key={i} />);
        } else {
            stars.push(<FontAwesomeIcon icon={faStar} color="white" key={i} />);
        }
    }

    return <div>{stars}</div>;
};

export default StarRating;