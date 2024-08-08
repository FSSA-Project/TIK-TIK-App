import React, { useState } from 'react';
import '../styles/Taskcolumn.css';
import TaskCard from '../components/Taskcard.jsx';
import { useDrop } from 'react-dnd';
import CreateTask from './CreateTask.jsx';

const TaskColumn = ({ title, color, showButton, taskCards, onDrop, createTask, removeTask }) => {
  const [{ isOver }, drop] = useDrop({
    accept: 'task',
    drop: (item) => onDrop(item.taskId),
    collect: (monitor) => ({
      isOver: !!monitor.isOver(),
    }),
  });

  const [tasks, setTasks] = useState(taskCards || []);
  const [showInputFields, setShowInputFields] = useState(false);

  const addTask = async (newTask) => {
    const createdTask = await createTask(newTask);
    setTasks([...tasks, createdTask]);
    setShowInputFields(false);
  };

  return (
    <div
      ref={drop}
      className="task-column"
      style={{ backgroundColor: isOver ? '#e0e0e0' : color }}
    >
      
      {taskCards.map((card) => (
        <TaskCard key={card.id} {...card} title={card.title} description={card.description} removeTask={removeTask} />
      ))}

      {showInputFields && (
          <CreateTask  createTask={addTask} />
      )}

      {showButton && (
        <button className="add-new-card"  onClick={() => setShowInputFields(true)}>
          <div className="add-button"></div>
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#000000" fill="none">
            <path d="M12 8V16M16 12L8 12" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
            <path d="M22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22C17.5228 22 22 17.5228 22 12Z" stroke="currentColor" strokeWidth="1.5" />
          </svg>
          Add New
        </button>
      )}
    </div>
  );
};

/**
 * This component represents the status column in the task board.
 */

export const TaskColumnToStart = (props) => <TaskColumn {...props} />;
export const TaskColumnInProgress = (props) => <TaskColumn {...props} />;
export const TaskColumnCompleted = (props) => <TaskColumn {...props} />;