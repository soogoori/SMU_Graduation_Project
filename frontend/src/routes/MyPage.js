import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import MyShoes from './MyShoes';
import WriteReview from './WriteReview';
import "../styles/css/MyPage.css"
import axios from 'axios';
import profileImageDefault from "../assets/profileDefault.png"

const MyPage = () => {
    const [activePage, setActivePage] = useState('MyShoes');
    const [shoesData, setShoesData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [userProfileImage, setUserProfileImage] = useState('');
    const [userNickname, setUserNickname] = useState('');
    const [userId, setUserId] = useState('');
    const [userSize, setUserSize] = useState('');

    useEffect(() => {
        const fetchUserShoes = async () => {
            try {
                const response = await axios.get('/api/users/me/purchases', {
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
                const response = await axios.get('/api/users/me', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem("token")}`
                    }
                });
                const { profileImage, nickname, id, size } = response.data;
                setUserProfileImage(profileImage);
                setUserNickname(nickname);
                setUserId(id);
                setUserSize(size);
            } catch (error) {
                console.error('Error fetching user profile:', error);
            }
        };

        fetchUserShoes();
        fetchUserProfile();
    }, []);

    const renderPage = () => {
        switch (activePage) {
            case 'MyShoes':
                return <MyShoes shoesData={shoesData} loading={loading} />;
            case 'WriteReview':
                return <WriteReview userId={userId} />;
            default:
                return <MyShoes shoesData={shoesData} loading={loading} />;
        }
    };

    return (
        <div>
            {/* 사용자 정보 상단 컨테이너 */}
            <div className="user-profile-container">
                <div className="user-profile-container-left">
                    <img src={userProfileImage || profileImageDefault} alt="Profile" className="profile-image"/>
                </div>
                <div className="user-profile-container-right">
                    <p>UserNickname: {userNickname}</p>
                    <p>ID: {userId}</p>
                    <p>Size: {userSize}</p>
                    <button>프로필 수정</button>
                </div>
            </div>

            {/* 페이지 이동 버튼들 */}
            <div className="page-buttons">
                <button onClick={() => setActivePage('MyShoes')}>보유신발</button>
                <button onClick={() => setActivePage('WriteReview')}>작성리뷰</button>
                <button onClick={() => setActivePage('ShoeSize')}>신발사이즈</button>
                <button onClick={() => setActivePage('ProfileManagement')}>프로필관리</button>
            </div>

            {/* 선택된 페이지 표시 */}
            {renderPage()}
        </div>
    );
};

export default MyPage;