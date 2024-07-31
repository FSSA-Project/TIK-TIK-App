import React from 'react';
import '../styles/Taskcolumn.css';
import TaskCard from '../components/Taskcard.jsx';
import { useDrop } from 'react-dnd';

const TaskColumn = ({ title, color, showButton, taskCards, onDrop }) => {
  const [{ isOver }, drop] = useDrop(() => ({
    accept: "task",
    drop: (item) => ondrop(item.id),
    collect: (monitor) => ({
      isOver: !!monitor.isOver()
    })
  }));

  return (
    <>
      {/* <h2 style={{ borderColor: color }}>{title}</h2> */}
    <div 
      ref={drop}
      className="task-column"
      style={{ backgroundColor: isOver ? '#e0e0e0' : '' }}>
      <TaskCard />
      {/* <TaskCard />
      <TaskCard /> */}
      {showButton && <button className="add-new-card">
        <div className="add-button"></div>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#000000" fill="none">
          <path d="M12 8V16M16 12L8 12" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
          <path d="M22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22C17.5228 22 22 17.5228 22 12Z" stroke="currentColor" stroke-width="1.5" />
        </svg>
      Add New</button>}
    </div>
    </>
  );
};

export default TaskColumn;
