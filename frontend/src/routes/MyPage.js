import React, { useState, useEffect } from 'react';
import MyShoes from './MyShoes';
import axios from 'axios';  // axios 추가

const MyPage = () => {
    const [activePage, setActivePage] = useState('MyShoes');
    const [shoesData, setShoesData] = useState([]);
    const [loading, setLoading] = useState(true);

    // 새로운 state 추가
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

        // 유저 프로필 정보를 가져오는 함수
        const fetchUserProfile = async () => {
            try {
                const response = await axios.get('/api/users/me',{
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${localStorage.getItem("token")}`}
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

        // 사용자 신발 및 프로필 정보 가져오기
        fetchUserShoes();
        fetchUserProfile();
    }, []);

    const renderPage = () => {
        switch (activePage) {
            case 'MyShoes':
                return <MyShoes shoesData={shoesData} loading={loading} />;
            // 다른 페이지도 추가 가능
            default:
                return <MyShoes shoesData={shoesData} loading={loading} />;
        }
    };

    return (
        <div>
            {/* 사용자 정보 상단 컨테이너 */}
            <div className="user-profile-container">
                <img src={userProfileImage} alt="Profile" />
                <p>UserNickname: {userNickname}</p>
                <p>ID: {userId}</p>
                <p>Size: {userSize}</p>
                <button>Edit Profile</button>
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