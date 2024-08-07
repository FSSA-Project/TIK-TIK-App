import React, { useState } from 'react';
import '../styles/CreateTask.css';

const useSessionStorage = (key) => sessionStorage.getItem(key);

const CreateTask = () => {
  const [taskData, setTaskData] = useState({ title: '', description: '' });
  const [message, setMessage] = useState(null);
  const [showInputFields] = useState(true);

  const token = useSessionStorage('token');
  const userId = useSessionStorage('userId');

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTaskData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleAddTask = async () => {
    const newTask = {
      ...taskData,
      statusId: 1,
      userId: userId,
    };

    try {
      const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/task/create', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(newTask),
      });

      if (!response.ok) {
        throw new Error('Failed to create task');
      }

      const createdTask = await response.json();
      console.log('Task created:', createdTask);
      setTaskData({ title: '', description: '' });
      setMessage('Task created successfully');

      setTimeout(() => {
        setMessage(null);
      }, 1000);
    } catch (error) {
      console.error('Error:', error);
      setMessage(error.message);
    }
  };

  const handleCancelTask = () => {
    const confirmCancel = window.confirm("Are you sure you want to discard your current task creation?");
    if (confirmCancel) {
      setTaskData({ title: '', description: '' });
    //   setShowInputFields(false);
      setMessage('Task creation cancelled');
      setTimeout(() => {
        setMessage(null);
      }, 1000);
    }
  };

  return (
    <div className="create-task-container">
      {showInputFields && (
        <>
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
            <button onClick={handleAddTask}>Add Task</button>
            <button onClick={handleCancelTask}>Cancel</button>
          </div>
        </>
      )}
      {message && <p className="message">{message}</p>}
    </div>
  );
};

export default CreateTask;
