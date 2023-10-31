import React, { useState, useEffect } from 'react';
import '../styles/css/MyShoes.css';
import '../styles/css/WriteReview.css';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import UserWriteReview from "./UserWriteReview";
import UserShoes from "./UserShoes";
import profileImageDefault from "../assets/profileDefault.png"

const UserPage = () => {
    const { userId } = useParams();
    const [activePage, setActivePage] = useState('UserShoes');
    const [shoesData, setShoesData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [userProfileImage, setUserProfileImage] = useState('');
    const [userNickname, setUserNickname] = useState('');
    const [userSize, setUserSize] = useState('');
    const [userWidth, setUserWidth] = useState('');

    useEffect(() => {
        const fetchUserShoes = async () => {
            try {
                const response = await axios.get(`/api/purchase/user/${userId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });
                const { hasNext, purchase } = response.data;
                setShoesData(purchase);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching user shoes:', error);
                setLoading(false);
            }
        };

        const fetchUserProfile = async () => {
            try {
                const response = await axios.get(`/api/users/${userId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });
                const { profileImage, nickname, size, width } = response.data;
                setUserProfileImage(profileImage);
                setUserNickname(nickname);
                setUserSize(size);
                setUserWidth(width);
            } catch (error) {
                console.error('Error fetching user profile:', error);
            }
        };

        fetchUserShoes();
        fetchUserProfile();
    }, [userId]);

    const renderPage = () => {
        switch (activePage) {
            case 'UserShoes':
                return <UserShoes shoesData={userId} loading={loading} />;
            case 'WriteReview':
                return <UserWriteReview userId={userId} />;
            default:
                return <UserShoes shoesData={userId} />;
        }
    };

    return (
        <div>
            {/* 사용자 정보 상단 컨테이너 */}
            <div className="user-profile-container">
                <div className="user-profile-container-left">
                    <img src={userProfileImage || profileImageDefault} alt="Profile" />
                </div>
                <div className="user-profile-container-right">
                    <p>UserNickname: {userNickname}</p>
                    <p>Size: {userSize}</p>
                    <p>Width: {userWidth}</p>
                </div>
            </div>

            {/* 페이지 이동 버튼들 */}
            <div className="page-buttons">
                <button onClick={() => setActivePage('UserShoes')}>유저의 보유 신발</button>
                <button onClick={() => setActivePage('UserWriteReview')}>유저의 작성 리뷰</button>
            </div>

            {/* 선택된 페이지 표시 */}
            {renderPage()}
        </div>
    );
};

export default UserPage;