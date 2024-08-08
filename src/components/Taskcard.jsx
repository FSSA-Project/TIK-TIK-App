import React, { useEffect, useRef, useState } from 'react';
import '../styles/Taskcard.css';
import { useDrag } from 'react-dnd';
import useSessionStorage from './Auth';

const TaskCard = ({ id, title, description, date }) => {
  const [showOptions, setShowOptions] = useState(false);
  const [showFullDesc, setShowFullDesc] = useState(false);
  const optionsMenuRef = useRef(null);
  const [token] = useSessionStorage('token');
  
  const handleOptionsClick = () => setShowOptions(!showOptions);
  const handleToggleDesc = () => setShowFullDesc(!showFullDesc);

  const handleClickOutside = (event) => {
    if (optionsMenuRef.current && !optionsMenuRef.current.contains(event.target)) {
      setShowOptions(false);
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);
  
  const [{ isDragging }, drag] = useDrag({
    type: 'task',
    item: { taskId: id },
    collect: (monitor) => ({
      isDragging: !!monitor.isDragging(),
    }),
  });

  const truncateDescription = (description) => {
    if (typeof description === 'string' && description.length > 120) {
      return `${description.slice(0, 105)}... `;
    }
    return description || '';
  };
  
  const deleteTask = async (removeTask) => {
    try {
      const response = await fetch(`https://todo-app-wpbz.onrender.com/api/v1/task/delete`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id }), // Send the user ID in the body
      });

      if (response.ok) {
        removeTask(id);
      } else {
        console.error('Failed to delete the task');
      }
    } catch (error) {
      console.error('An error occurred while deleting the task:', error);
    }
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
          <div className="options-menu" ref={optionsMenuRef}>
            <div className='options-menu-task'>
            <button className="option-button">Edit</button>
            <button className="option-button" onClick={deleteTask}>Delete</button>
              </div>
          </div>
        )}
      </div>
      <p title={description}>
        {showFullDesc ? description : truncateDescription(description)}
        {description.length > 120 && (
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
