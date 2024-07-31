import React from 'react';
import '../styles/Dashboard.css';
import TaskColumn from './Taskcolumn';
import Sidebar from './Sidebar';

const Dashboard = () => {
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
         <path d="M12 8V16M16 12L8 12" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
         <path d="M22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22C17.5228 22 22 17.5228 22 12Z" stroke="currentColor" stroke-width="1.5" />
        </svg>
          New task</button>
        <div className="user-avatar">
          <img src='https://discoveries.vanderbilthealth.com/wp-content/uploads/2023/09/test-Headshot.jpg' alt='profile-imag'/>
        </div>
        </div>
      </header>
        {/* Search div */}
        <div className="dashboard-header-container-2">
        <div className="search">
          <input type="text" placeholder="Search" />
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#000000" fill="none" class="search-icon">
            <path d="M17.5 17.5L22 22" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
            <path d="M20 11C20 6.02944 15.9706 2 11 2C6.02944 2 2 6.02944 2 11C2 15.9706 6.02944 20 11 20C15.9706 20 20 15.9706 20 11Z" stroke="currentColor" stroke-width="1.5" stroke-linejoin="round" />
          </svg>
        </div>
        <div className="date-filter">
          <input type="date" />
          <input type="date" />
        </div>
        </div>

      {/* <div className="task-title">
        <p className="task-title-1">To Start</p>
        <p className="task-title-2">In Progress</p>
        <p className="task-title-3">Completed</p>
      </div> */}

      <div className="task-columns">
        <div className='to-start'><p className='to-start-color'></p>To Start</div>
        <div className='in-progress'><p className='in-progress-color'></p>In Progress</div>
        <div className='completed'><p className='completed-color'></p>Completed</div>
      </div>

      <div className="task-columns">
        <TaskColumn title="To Start" color="blue" showButton={true} />
        <TaskColumn title="In Progress" color="yellow" showButton={false} />
        <TaskColumn title="Completed" color="green"showButton={false} />
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
        <path d="M11 6L21 6" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
        <path d="M11 12L21 12" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
        <path d="M11 18L21 18" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" />
        <path d="M3 7.39286C3 7.39286 4 8.04466 4.5 9C4.5 9 6 5.25 8 4" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
        <path d="M3 18.3929C3 18.3929 4 19.0447 4.5 20C4.5 20 6 16.25 8 15" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
      </svg>
      <div className='arrow-icon'>
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#a6a6a8" fill="none">
        <path d="M9.00005 6C9.00005 6 15 10.4189 15 12C15 13.5812 9 18 9 18" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
      </svg>
      </div>
      <div className="dashboard-title">Dashboard</div>
      <div className='arrow-icon'>
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#a6a6a8" fill="none">
        <path d="M9.00005 6C9.00005 6 15 10.4189 15 12C15 13.5812 9 18 9 18" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
      </svg>
      </div>
      <div className="overview-title">Overview</div>
    </div>
    <hr className="separator-line"/>
    </>
  );
}

export default Dashboard;
