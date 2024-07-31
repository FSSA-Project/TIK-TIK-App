import React, { useState } from 'react';
import '../styles/Taskcard.css';
import { useDrag } from 'react-dnd';

const TaskCard = () => {

  const [showOptions, setShowOptions] = useState(false);

  const handleOptionsClick = () => {
    setShowOptions(!showOptions);
  };

  const [{ isDragging }, drag] = useDrag(() => ({
    type: "task",
    // item: {id: initialData.id},
    collect: (monitor) => ({
      isDragging: !!monitor.isDragging()
    })
  }))

  return (
    <div className="task-card" draggable="true" ref={drag} style={{ opacity: isDragging ? 0.5 : 1 }}>
      <div className='task-content'>
      <h3>Title of the Task</h3>
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6" onClick={handleOptionsClick}>
        <path stroke-linecap="round" stroke-linejoin="round" d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z" />
      </svg>
      {showOptions && (
        <div className="options-menu">
          <button className="option-button">Edit</button>
          <button className="option-button">Delete</button>
        </div>
      )}
      </div>
      <p>Lorem Ipsum has been the industry's standard dummy text ever since the 1500s...</p>
      <span className="task-date">17/07/2024</span>


      {/* <button className="card-options">...</button> */}
    </div>
  );
};

export default TaskCard;
