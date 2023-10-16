// ProfileManagement.js

import React, { useState } from 'react';
import axios from 'axios';

const ProfileManagement = ({ userId, onUpdateProfile }) => {
    const [newNickname, setNewNickname] = useState('');
    const [newSize, setNewSize] = useState(0); // 신발 사이즈 추가
    const [newWidth, setNewWidth] = useState(0); // 발볼 추가

    const handleUpdateProfile = async () => {
        try {
            // 여기에서 API 호출하여 프로필 업데이트
            const response = await axios.put(
                `/api/users/me`,
                {
                    nickname: newNickname,
                    size: newSize,
                    width: newWidth,
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem('token')}`,
                    },
                }
            );

            // 업데이트가 성공하면 부모 컴포넌트로 알림
            onUpdateProfile(response.data);
        } catch (error) {
            console.error('Error updating profile:', error);
        }
    };

    return (
        <div>
            <h2>프로필 관리</h2>
            <div>
                <label htmlFor="newNickname">새 닉네임: </label>
                <input
                    type="text"
                    id="newNickname"
                    value={newNickname}
                    onChange={(e) => setNewNickname(e.target.value)}
                />
            </div>
            <div>
                <label htmlFor="newSize">신발 사이즈: </label>
                <input
                    type="number"
                    id="newSize"
                    value={newSize}
                    onChange={(e) => setNewSize(parseInt(e.target.value, 10))}
                />
            </div>
            <div>
                <label htmlFor="newWidth">발볼: </label>
                <input
                    type="number"
                    id="newWidth"
                    value={newWidth}
                    onChange={(e) => setNewWidth(parseInt(e.target.value, 10))}
                />
            </div>
            <button onClick={handleUpdateProfile}>프로필 업데이트</button>
        </div>
    );
};

export default ProfileManagement;