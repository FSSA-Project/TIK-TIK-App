import React from 'react';
import './Header.css';

const Header = () => {
  return (
    <header className="header">
      <h1>My Todo</h1>
      <div className="search-container">
        <input type="text" placeholder="Search" />
        <button className="search-icon"></button>
      </div>
      <div className="date-filters">
        <input type="date" />
        <input type="date" />
      </div>
      <button className="new-task-button">+ New task</button>
      <div className="user-avatar"></div>
    </header>
  );
};

export default Header;
