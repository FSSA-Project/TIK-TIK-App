import React from 'react';
import './UserProfile.css';

const UserProfile = () => {
    return (
        <div className="user-profile">
            <div className="profile-header">
                <div className="profile-icon">M</div>
            </div>
            <div className="profile-details">
                <div className="profile-name">Meenu 1212</div>
                <div className="profile-email">vasumeenu2004@gmail.com</div>
            </div>
            <div className="logout">
                <button>Log Out</button>
            </div>
        </div>
    );
};

export default UserProfile;
