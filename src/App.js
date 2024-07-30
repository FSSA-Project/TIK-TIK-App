import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { DragDropContext } from 'react-beautiful-dnd';
import './styles/Register.css';
import Dashboard from './components/Dashboard';
import Register from './components/Register';
import Login from './components/Login';

const initialData = {
  tasks: {
    'task-1': { id: 'task-1', title: 'Title of the Task', description: 'Lorem Ipsum...', date: '17/07/2024' },
    'task-2': { id: 'task-2', title: 'Title of the Task', description: 'Lorem Ipsum...', date: '17/07/2024' },
    'task-3': { id: 'task-3', title: 'Title of the Task', description: 'Lorem Ipsum...', date: '17/07/2024' },
  },
  columns: {
    'column-1': { id: 'column-1', title: 'To Do', taskIds: ['task-1', 'task-2', 'task-3'] },
    // Add more columns as needed
  },
  columnOrder: ['column-1']
};

const App = () => {
  const [data, setData] = useState(initialData);

  const onDragEnd = (result) => {
    const { destination, source, draggableId } = result;
    if (!destination) {
      return;
    }

    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) {
      return;
    }

    const start = data.columns[source.droppableId];
    const finish = data.columns[destination.droppableId];

    if (start === finish) {
      const newTaskIds = Array.from(start.taskIds);
      newTaskIds.splice(source.index, 1);
      newTaskIds.splice(destination.index, 0, draggableId);

      const newColumn = {
        ...start,
        taskIds: newTaskIds
      };

      const newState = {
        ...data,
        columns: {
          ...data.columns,
          [newColumn.id]: newColumn
        }
      };

      setData(newState);
      return;
    }

    const startTaskIds = Array.from(start.taskIds);
    startTaskIds.splice(source.index, 1);
    const newStart = {
      ...start,
      taskIds: startTaskIds
    };

    const finishTaskIds = Array.from(finish.taskIds);
    finishTaskIds.splice(destination.index, 0, draggableId);
    const newFinish = {
      ...finish,
      taskIds: finishTaskIds
    };

    const newState = {
      ...data,
      columns: {
        ...data.columns,
        [newStart.id]: newStart,
        [newFinish.id]: newFinish
      }
    };

    setData(newState);
  };

  return (
    <DragDropContext onDragEnd={onDragEnd}>
      <Router>
        <Routes>
          <Route path="/" element={<Dashboard data={data} />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </Router>
    </DragDropContext>
  );
};

export default App;


// deploy app by these cmd 

// npm run build
// netlify deploy --dir=build



//for icon
//npm install --save @fortawesome/fontawesome-svg-core @fortawesome/free-solid-svg-icons @fortawesome/react-fontawesome
