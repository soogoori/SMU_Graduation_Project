import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import MyShoes from './MyShoes';
import WriteReview from './WriteReview';
import ProfileManagement from './ProfileManagement';
import "../styles/css/MyPage.css"
import axios from 'axios';
import profileImageDefault from "../assets/profileDefault.png"

import '../styles/css/MyPage.css';
//import '../ProfileManagement.css';
const MyPage = () => {
    const [activePage, setActivePage] = useState('MyShoes');
    const [shoesData, setShoesData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [userProfileImage, setUserProfileImage] = useState('');
    const [userNickname, setUserNickname] = useState('');
    const [userId, setUserId] = useState('');
    const [userSize, setUserSize] = useState('');
    const [userWidth, setUserWidth] = useState('');

    const [profileUpdate, setProfileUpdate] = useState(false);

    useEffect(() => {
        const fetchUserShoes = async () => {
            try {
                const response = await axios.get('/api/users/me/purchases', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem('token')}`,
                    },
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
                        'Authorization': `Bearer ${localStorage.getItem('token')}`,
                    },
                });
                const { profileImage, nickname, id, size, width } = response.data;
                setUserProfileImage(profileImage);
                setUserNickname(nickname);
                setUserId(id);
                setUserSize(size);
                setUserWidth(width);
            } catch (error) {
                console.error('Error fetching user profile:', error);
            }
        };

        fetchUserShoes();
        fetchUserProfile();
    }, [profileUpdate]); // 프로필 업데이트 감지

    const onUpdateProfile = () => {
        // 프로필이 업데이트되었음을 알림
        setProfileUpdate(true);
    };

    const renderPage = () => {
        switch (activePage) {
            case 'MyShoes':
                return <MyShoes shoesData={shoesData} loading={loading} />;
            case 'WriteReview':
                return <WriteReview userId={userId} />;
            case 'ProfileManagement':
                // 프로필 관리 페이지에 대한 렌더링 코드 추가
                return <ProfileManagement userId={userId} onUpdateProfile={onUpdateProfile} />;
            default:
                return <MyShoes shoesData={shoesData} loading={loading} />;
        }
    };

    return (
        <div>
            {/* 사용자 정보 상단 컨테이너 */}
            <div className="user-profile-container">
                <div className="user-profile-container-left">
                    <img src={userProfileImage} alt="Profile" />
                </div>
                <div className="user-profile-container-right">
                    <p>UserNickname: {userNickname}</p>
                    <p>ID: {userId}</p>
                    <p>Size: {userSize}</p>
                    <p>Width: {userWidth}</p>
                    <Link to="profile-management">
                        <button>프로필 수정</button>
                    </Link>
                </div>
            </div>

            {/* 페이지 이동 버튼들 */}
            <div className="page-buttons">
                <button onClick={() => setActivePage('MyShoes')}>보유신발</button>
                <button onClick={() => setActivePage('WriteReview')}>작성리뷰</button>
                <button onClick={() => setActivePage('ProfileManagement')}>프로필관리</button>
            </div>

            {/* 선택된 페이지 표시 */}
            {renderPage()}
        </div>
    );
};

export default MyPage;