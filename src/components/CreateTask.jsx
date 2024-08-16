import React, { useState } from 'react';
import '../styles/CreateTask.css';

const useSessionStorage = (key) => sessionStorage.getItem(key);

const CreateTask = ({ onClose, onSave, dataUpdate }) => {
  const [taskData, setTaskData] = useState({ title: '', description: '' });
  const [message, setMessage] = useState(null);
  const [loading, setLoading] = useState(false);

  const User = JSON.parse(useSessionStorage('userProfile'));
  const Token = User.idToken
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTaskData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleAddTask = async () => {
    const newTask = {
      ...taskData,
      statusId: 1
    };
    console.log(Token);
    console.log(newTask);
    setLoading(true);
    try {
      const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/task/create', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${Token}`,
        },
        body: JSON.stringify(newTask),
      });
      console.log("Response in Creating Task", response);
      if (!response.ok) {
        throw new Error('Failed to create task');
      }

      const createdTask = await response.json();
      console.log('Task created:', createdTask);
      setTaskData({ title: '', description: '' });
      setMessage('Task created successfully');

      // onSave(createdTask);

      setTimeout(() => {
        setMessage(null);
        onClose();
      }, 1000);
      dataUpdate();
    } catch (error) {
      console.error('Error:', error);
      setMessage(error.message);
    } finally {
      setLoading(false);
    }
  };

  const handleCancelTask = () => {
    const confirmCancel = window.confirm("Are you sure you want to discard your current task creation?");
    if (confirmCancel) {
      setTaskData({ title: '', description: '' });
      setMessage('Task creation cancelled.');
      setTimeout(() => {
        setMessage(null);
        onClose();
      }, 1000);
    }
  };

  return (
    <div className="create-task-container">
      <div className="create-task-modal">
        <input 
          type="text" 
          name="title"
          placeholder="Title" 
          value={taskData.title}
          onChange={handleInputChange}
        />
        <textarea 
          name="description"
          placeholder="Description" 
          value={taskData.description}
          onChange={handleInputChange}
        />
        <div className="button-group">
          <button className='save-button' onClick={handleAddTask} disabled={loading}>{loading ? <div class="loader"></div> : 'Add Task' }</button>
          <button className='cancel-button' onClick={handleCancelTask}>Cancel</button>
        </div>
      </div>
      {message && <p className="error-message">{message}</p>}
    </div>
  );
};

export default CreateTask;
