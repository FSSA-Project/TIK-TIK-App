import React from 'react';
import '../styles/Taskcard.css';

const TaskCard = () => {
  return (
    <div className="task-card">
      <h3>Title of the Task</h3>
      <p>Lorem Ipsum has been the industry's standard dummy text ever since the 1500s...</p>
      <span className="task-date">17/07/2024</span>
      <button className="card-options">...</button>
    </div>
  );
};

export default TaskCard;
