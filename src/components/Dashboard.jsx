import React from 'react';
import '../styles/Dashboard.css';
import TaskColumn from './Taskcolumn';

const Dashboard = () => {
  return (

    <div className="dashboard">
      <header className="dashboard-header">
        <h1>My Todo</h1>
        <div className="search">
          <input type="text" placeholder="Search" />
          <button className="search-btn"></button>
        </div>
        <div className="date-filter">
          <input type="date" />
          <input type="date" />
        </div>
        <button className="new-task-btn">+ New task</button>
        <div className="user-avatar"></div>
      </header>
      <div className="task-columns">
        <TaskColumn title="To Start" color="blue" />
        <TaskColumn title="In Progress" color="yellow" />
        <TaskColumn title="Completed" color="green" />
      </div>
    </div>
  );
};

export default Dashboard;
