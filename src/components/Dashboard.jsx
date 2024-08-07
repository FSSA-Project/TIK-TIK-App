import React, { useState, useEffect } from 'react';
import useSessionStorage from './Auth';
import '../styles/Dashboard.css';
import { TaskColumnCompleted, TaskColumnInProgress, TaskColumnToStart } from './Taskcolumn';
import Sidebar from './Sidebar';
import '../App.css';

const Dashboard = () => {
  const [tasks, setTasks] = useState([]);
  const [inputTypeDate, setInputTypeDate] = useState('text');
  const [token] = useSessionStorage('token');
  const [id] = useSessionStorage('userId');

  useEffect(() => {
    const fetchTasks = async () => {
      console.log("fetchTasks: Fetching tasks...");
      try {
        const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/task/tasks', {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ id })
        });
    
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
    
        const result = await response.json();
        console.log('fetchTasks: Fetched tasks data:', result);
    
        // Extract the `data` property from the result and ensure it's an array
        if (Array.isArray(result.data)) {
          setTasks(result.data);
          console.log('fetchTasks: Tasks set successfully:', result.data);
        } else {
          console.error('fetchTasks: Unexpected data format:', result);
          setTasks([]); // Set an empty array if the format is unexpected
        }
      } catch (error) {
        console.error('fetchTasks: Error fetching tasks:', error);
        setTasks([]); // Set an empty array in case of an error
      }
    };

    if (token) {
      console.log("useEffect: Token exists, fetching data from backend...");
      fetchTasks();
    } else {
      console.log("useEffect: No token found.");
    }
  }, [token, id]);

  const handleDrop = (taskId, newStatus) => {
    console.log(`handleDrop: Changing status of task ${taskId} to ${newStatus}`);
    setTasks(prevTasks =>
      prevTasks.map(task =>
        task.id === taskId ? { ...task, status: newStatus } : task
      )
    );
  };
  console.log("After function", tasks)
  const toStartTasks = tasks.filter(task => task.statusId === 1);
  const inProgressTasks = tasks.filter(task => task.statusId === 2);
  const completedTasks = tasks.filter(task => task.statusId === 3);

  console.log("Dashboard: Rendering with tasks:", tasks);

  return (
    <div className='dashboard-container'>
      <Sidebar/>
      <div className="dashboard">
        <Header1 />
        <header className="dashboard-header-container">
          <div className="dashboard-header-content-1">
            <h3 className='dashboard-header-title'>My Todo</h3>
          </div>
          <div className="dashboard-header-content-2">
            <button className="new-task-btn">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#ffffff" fill="none">
                <path d="M12 8V16M16 12L8 12" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
                <path d="M22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22C17.5228 22 22 17.5228 22 12Z" stroke="currentColor" strokeWidth="1.5" />
              </svg>
              New task
            </button>
            <div className="user-avatar">
              <img src='https://discoveries.vanderbilthealth.com/wp-content/uploads/2023/09/test-Headshot.jpg' alt='profile-image' />
            </div>
          </div>
        </header>
        {/* Search div */}
        <div className="dashboard-header-container-2">
          <div className="search">
            <input type="text" placeholder="Search" />
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#000000" fill="none" className="search-icon">
              <path d="M17.5 17.5L22 22" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
              <path d="M20 11C20 6.02944 15.9706 2 11 2C6.02944 2 2 6.02944 2 11C2 15.9706 6.02944 20 11 20C15.9706 20 20 15.9706 20 11Z" stroke="currentColor" strokeWidth="1.5" strokeLinejoin="round" />
            </svg>
          </div>
          <div className="date-filter">
            <input placeholder={inputTypeDate === 'text' ? 'From' : ''} onFocus={() => setInputTypeDate('date')} type={inputTypeDate} />
            <input placeholder={inputTypeDate === 'text' ? 'To' : ''} onFocus={() => setInputTypeDate('date')} type={inputTypeDate} />
          </div>
        </div>

        <div className="task-columns">
          <div className='to-start'>
            <p className='to-start-color'></p>To Start ({toStartTasks?.length || 0})
          </div>
          <div className='in-progress'>
            <p className='in-progress-color'></p>In Progress ({inProgressTasks?.length || 0})
          </div>
          <div className='completed'>
            <p className='completed-color'></p>Completed ({completedTasks?.length || 0})
          </div>
        </div>

        <div className="task-columns">
          <TaskColumnToStart
            title="To Start"
            color="blue"
            showButton={true}
            taskCards={toStartTasks}
            onDrop={(taskId) => handleDrop(taskId, 1)} 
          />
          <TaskColumnInProgress
            title="In Progress"
            color="yellow"
            showButton={false}
            taskCards={inProgressTasks}
            onDrop={(taskId) => handleDrop(taskId, 2)} 
          />
          <TaskColumnCompleted
            title="Completed"
            color="green"
            showButton={false}
            taskCards={completedTasks}
            onDrop={(taskId) => handleDrop(taskId, 3)}
          />
        </div>
      </div>
    </div>
  );
};

export const Header1 = () => {
  return (
    <>
      <div className="header1-icons">
        {/* Todo Icon */}
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#a3a3a5" fill="none">
          <path d="M11 6L21 6" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" />
          <path d="M11 12L21 12" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" />
          <path d="M11 18L21 18" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" />
          <path d="M3 7.39286C3 7.39286 4 8.04466 4.5 9C4.5 9 6 5.25 8 4" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
          <path d="M3 18.3929C3 18.3929 4 19.0447 4.5 20C4.5 20 6 16.25 8 15" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
        </svg>
        <div className='arrow-icon'>
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#a6a6a8" fill="none">
            <path d="M9.00005 6C9.00005 6 15 10.4189 15 12C15 13.5812 9 18 9 18" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
          </svg>
        </div>
        <div className="dashboard-title">Dashboard</div>
        <div className='arrow-icon'>
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#a6a6a8" fill="none">
            <path d="M9.00005 6C9.00005 6 15 10.4189 15 12C15 13.5812 9 18 9 18" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
          </svg>
        </div>
        <div className="overview-title">Overview</div>
      </div>
      <hr className="separator-line"/>
    </>
  );
}

export default Dashboard;
