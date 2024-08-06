import React, { useState } from 'react';
import '../styles/Taskcard.css';
import { useDrag } from 'react-dnd';

const TaskCard = ({ id, title, desc, date }) => {
  const [showOptions, setShowOptions] = useState(false);
  const [showFullDesc, setShowFullDesc] = useState(false);

  const handleOptionsClick = () => setShowOptions(!showOptions);
  const handleToggleDesc = () => setShowFullDesc(!showFullDesc);

  const [{ isDragging }, drag] = useDrag({
    type: 'task',
    item: { taskId: id },
    collect: (monitor) => ({
      isDragging: !!monitor.isDragging(),
    }),
  });

  const truncateDescription = (description) => {
    if (description.length > 120) {
      return `${description.slice(0, 112)}... `;
    }
    return description;
  };

  return (
    <div
      ref={drag}
      className="task-card"
      style={{ opacity: isDragging ? 0.5 : 1 }}
    >

      <div className="task-content">
        <h3 title={title} >{title}</h3>
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="size-6" onClick={handleOptionsClick}>
          <path strokeLinecap="round" strokeLinejoin="round" d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z" />
        </svg>
        {showOptions && (
          <div className="options-menu">
            <button className="option-button">Edit</button>
            <button className="option-button">Delete</button>
          </div>
        )}
      </div>
      <p title={desc} >
        {showFullDesc ? desc : truncateDescription(desc)}
        {desc.length > 120 && (
          <span
            onClick={handleToggleDesc}
            style={{ color: '#0C97ED', cursor: 'pointer' }}
          >
            {showFullDesc ? ' See less' : ' See more'}
          </span>
        )}
      </p>
      <span className="task-date">{date}</span>
    </div>
  );
};

export default TaskCard;
