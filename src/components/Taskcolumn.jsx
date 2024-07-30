import React from 'react';
import '../styles/Taskcolumn.css';
import TaskCard from '../components/Taskcard.jsx';

const TaskColumn = ({ title, color }) => {
  return (
    <>
      {/* <h2 style={{ borderColor: color }}>{title}</h2> */}
    <div className="task-column">
      <TaskCard />
      <TaskCard />
      <TaskCard />
      <button className="add-new-card">+ Add New</button>
    </div>
    </>
  );
};

export default TaskColumn;