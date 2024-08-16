import React, { useEffect, useState } from 'react';
import useSessionStorage from './useSessionStorage';
import {
  TaskColumnToStart,
  TaskColumnInProgress,
  TaskColumnCompleted
} from './Taskcolumn'; 
 

const TaskList = () => {
  const [tasks, setTasks] = useState([]);
  const [token] = useSessionStorage('token');

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/task/tasks', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data = await response.json();
        console.log(data);
        setTasks(data);
      } catch (error) {
        console.error('Error fetching tasks:', error);
      }
    };

    if (token) {
      fetchTasks();
    }
  }, [token]);

  const handleDrop = (taskId, newStatus) => {
    setTasks(prevTasks =>
      prevTasks.map(task =>
        task.id === taskId ? { ...task, status: newStatus } : task
      )
    );
  };

  const toStartTasks = tasks.filter(task => task.status === 'toStart');
  const inProgressTasks = tasks.filter(task => task.status === 'inProgress');
  const completedTasks = tasks.filter(task => task.status === 'completed');


  return (
    <div>
      <h1>Task List</h1>
      <div className="task-columns">
        <TaskColumnToStart
          title="To Start"
          color="lightblue"
          showButton={true}
          taskCards={toStartTasks}
          onDrop={(taskId) => handleDrop(taskId, 'toStart')}
          createTask={createTask}
        />
        <TaskColumnInProgress
          title="In Progress"
          color="lightyellow"
          showButton={false}
          taskCards={inProgressTasks}
          onDrop={(taskId) => handleDrop(taskId, 'inProgress')}
          createTask={createTask}
        />
        <TaskColumnCompleted
          title="Completed"
          color="lightgreen"
          showButton={false}
          taskCards={completedTasks}
          onDrop={(taskId) => handleDrop(taskId, 'completed')}
          createTask={createTask}
        />
      </div>
    </div>
  );
};

export default TaskList;
