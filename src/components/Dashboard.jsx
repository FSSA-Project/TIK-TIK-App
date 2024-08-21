import React, { useState, useEffect } from 'react';
import '../styles/Dashboard.css';
import { TaskColumnCompleted, TaskColumnInProgress, TaskColumnToStart } from './Taskcolumn';
import Sidebar from './Sidebar';
import '../App.css';

const useSessionStorage = (key) => sessionStorage.getItem(key);

const Dashboard = () => {
  const [tasks, setTasks] = useState([]);
  // const [inputTypeDate, setInputTypeDate] = useState('text');
  const User = JSON.parse(useSessionStorage('userProfile'));
  const token = JSON.parse(useSessionStorage('usertoken'));
  const userName = User.userName;
  const profileURL = User.photoURL;
  const [isDataUpdated, setIsDataUpdated] = useState(false);
  // const [showInputFields, setShowInputFields] = useState(false);
  const [searchQuery, setSearchQuery] = useState(''); // State for search query

  // Callback to trigger data updates
  const callBackFuncForDataUpdate = () => {
    setIsDataUpdated((prev) => !prev); 
  }

  // Fetch tasks on component mount and when data updates
  useEffect(() => {
    const fetchTasks = async () => {
      console.log("fetchTasks: Fetching tasks...");
      try {
        const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/task/tasks', {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
    
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
    
        const result = await response.json();
        console.log('fetchTasks: Fetched tasks data:', result);
    
        if (Array.isArray(result.data)) {
          setTasks(result.data);
          console.log('fetchTasks: Tasks set successfully:', result.data);
        } else {
          console.error('fetchTasks: Unexpected data format:', result);
          setTasks([]);
          console.log("Set an empty array if the format is unexpected");
        }
      } catch (error) {
        console.error('fetchTasks: Error fetching tasks:', error);
        setTasks([]);
        console.log("Set an empty array in case of an error");
      }
    };

    if (token) {
      console.log("useEffect: Token exists, fetching data...");
      fetchTasks();
    } else {
      console.log("useEffect: No token found.");
    }
  }, [token, isDataUpdated]);

  // Search function to filter tasks based on query
  const search = async () => {
    console.log(`search: Initiating search with query "${searchQuery}"...`);
    try {
      const response = await fetch(`https://todo-app-wpbz.onrender.com/api/v1/task/search?search=${encodeURIComponent(searchQuery)}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error('search: Network response was not ok');
      }

      const result = await response.json();
      console.log('search: Received search results:', result);

      if (Array.isArray(result.data)) {
        setTasks(result.data);
        console.log('search: Tasks set successfully after search:', result.data);
      } else {
        console.error('search: Unexpected data format:', result);
        setTasks([]);
      }
    } catch (error) {
      console.error('search: Error during search operation:', error);
      setTasks([]);
    }
  };

  // Handle task drop to update status
  const handleDrop = async (taskId, newStatus) => {
    console.log(`handleDrop: Attempting to change status of task ${taskId} to ${newStatus}...`);
    setTasks((prevTasks) =>
      prevTasks.map((task) =>
        task.id === taskId ? { ...task, statusId: newStatus } : task
      )
    );

    try {
      const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/task/update/status', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({
          id: taskId,
          statusId: newStatus,
        }),
      });

      if (response.ok) {
        console.log(`handleDrop: Task ${taskId} status updated successfully.`);
      } else {
        console.error(`handleDrop: Failed to update task ${taskId} status.`);
      }
    } catch (error) {
      console.error(`handleDrop: Error updating task ${taskId} status:`, error);
    }
  };

  const removeTask = (taskId) => {
    console.log(`removeTask: Removing task with ID ${taskId}.`);
    setTasks(tasks.filter((task) => task.id !== taskId));
  };

  // Component to display user avatar or initial
  const UserAvatar = () => {
    const usernameInitial = userName ? userName.charAt(0).toUpperCase() : null;

    return (
      <div className="user-avatar" title="profile">
        {profileURL ? (
          <img
            src={profileURL}
            alt="profile-image"
          />
        ) : usernameInitial ? (
          <div style={styles.profile} title={userName}>
            {usernameInitial}
          </div>
        ) : (
          <img
            src='https://discoveries.vanderbilthealth.com/wp-content/uploads/2023/09/test-Headshot.jpg'
            alt='default-profile-image'
          />
        )}
      </div>
    );
  };

  const styles = {
    profile: {
      width: '50px',
      height: '50px',
      borderRadius: '50%',
      backgroundColor: '#dde8ff',
      display: 'flex',
      border:'3px solid #202d48',
      alignItems: 'center',
      justifyContent: 'center',
      color: '#202d48',
      fontSize: '24px',
      fontWeight: 'bold',
    },
  };

  // Filter tasks by their status
  const toStartTasks = tasks.filter((task) => task.statusId === 1);
  const inProgressTasks = tasks.filter((task) => task.statusId === 2);
  const completedTasks = tasks.filter((task) => task.statusId === 3);

  console.log('Dashboard: Rendering with tasks:', tasks);

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
            <UserAvatar />
          </div>
        </header>

        {/* Search div */}
        <div className="dashboard-header-container-2">
          <div className="search">
          <input 
            type="text" 
            placeholder="Search" 
            value={searchQuery}
            onChange={(e) => {
              setSearchQuery(e.target.value);
              console.log(`Search input updated: ${e.target.value}`);
              search(); // Call the search function after updating the query
            }}
          />
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#000000" fill="none" className="search-icon">
                <path d="M17.5 17.5L22 22" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
                <path d="M20 11C20 6.02944 15.9706 2 11 2C6.02944 2 2 6.02944 2 11C2 15.9706 6.02944 20 11 20C15.9706 20 20 15.9706 20 11Z" stroke="currentColor" strokeWidth="1.5" strokeLinejoin="round" />
              </svg>
          </div>
          {/* <div className="date-filter">
            <input 
              placeholder={inputTypeDate === 'text' ? 'From' : ''} 
              onFocus={() => {
                console.log("Date input (From) focused, changing input type to date.");
                setInputTypeDate('date');
              }} 
              type={inputTypeDate} 
            />
            <input 
              placeholder={inputTypeDate === 'text' ? 'To' : ''} 
              onFocus={() => {
                console.log("Date input (To) focused, changing input type to date.");
                setInputTypeDate('date');
              }} 
              type={inputTypeDate} 
            />
          </div> */}
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
            id="1"
            showButton={true}
            taskCards={toStartTasks}
            onDrop={(taskId) => handleDrop(taskId, 1)} 
            removeTask={removeTask} 
            dataUpdate={callBackFuncForDataUpdate}
          />
          <TaskColumnInProgress
            title="In Progress"
            color="yellow"
            id="2"
            showButton={false}
            taskCards={inProgressTasks}
            onDrop={(taskId) => handleDrop(taskId, 2)} 
            removeTask={removeTask} 
            dataUpdate={callBackFuncForDataUpdate}
          />
          <TaskColumnCompleted
            title="Completed"
            color="green"
            id="3"
            showButton={false}
            taskCards={completedTasks}
            onDrop={(taskId) => handleDrop(taskId, 3)}
            removeTask={removeTask} 
            dataUpdate={callBackFuncForDataUpdate}
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
