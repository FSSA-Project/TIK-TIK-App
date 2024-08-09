import React, { useEffect, useRef, useState } from 'react';
import '../styles/Taskcard.css';
import { useDrag } from 'react-dnd';
import useSessionStorage from './Auth';

const TaskCard = ({ id, title, description, createdAt, statusId, dataUpdate }) => {
  const [showOptions, setShowOptions] = useState(false);
  const [showFullDesc, setShowFullDesc] = useState(false);
  const optionsMenuRef = useRef(null);
  const [token] = useSessionStorage('token');
  const [isEditing, setIsEditing] = useState(false);
  const [editTitle, setEditTitle] = useState(title);
  const [editDescription, setEditDescription] = useState(description);
  const [loading, setLoading] = useState(false);
  
  const handleOptionsClick = () => setShowOptions(!showOptions);
  const handleToggleDesc = () => setShowFullDesc(!showFullDesc);
  const handleEditClick = () => {
    setIsEditing(true);
    setShowOptions(false);
  }
  const handleCancelEdit = () => setIsEditing(false);

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

  // Edit and updating the task
  const handleSaveEdit = async () => {
    setLoading(true);
    try {
    const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/task/update', {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({
        id,
        title: editTitle,
        description: editDescription,
        statusId: statusId,
      }),
    });

    if (response.ok) {
      setIsEditing(false);
      setShowOptions(false);
      dataUpdate();
    } else {
      console.error('Failed to update task');
    }
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  // Delete task
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
        setShowOptions(false);
        dataUpdate();
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
    id={id}
    >
      <div className="task-content">
      {!isEditing && <h3 title={title}>{title}</h3>}
        {!isEditing && <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="size-6" onClick={handleOptionsClick}>
          <path strokeLinecap="round" strokeLinejoin="round" d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z" />
        </svg> }
        {showOptions && (
          <div className="options-menu" ref={optionsMenuRef}>
            <div className='options-menu-task'>
            <button className="option-button" onClick={handleEditClick} >Edit</button>
            <button className="option-button" onClick={deleteTask}>Delete</button>
          </div>
          </div>
        )}
      </div>
        {!isEditing ? (
          <>
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
      <span className="task-date">{createdAt}</span>
      </>
    ) : (
      <div className="update-task-container">
          <input
            type="text"
            value={editTitle}
            onChange={(e) => setEditTitle(e.target.value)}
            placeholder="Title"
          />
          <textarea
            value={editDescription}
            onChange={(e) => setEditDescription(e.target.value)}
            placeholder="Description"
          ></textarea>
          <div className="button-group">
          <button className="save-button" onClick={handleSaveEdit} disabled={loading}>{loading ? <div class="loader"></div> : 'Update' }</button>
          <button className="cancel-button" onClick={handleCancelEdit}>Cancel</button>
          </div>
      </div>
    )}
    </div>
  );
};

export default TaskCard;
