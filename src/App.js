import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './styles/Register.css'
import Login from './components/Login';
import RegisterForm from './components/Register';

function App() {
  return (
      <>
    <Router>
    <Routes>
      <Route path='/' element={<Login />} />
      <Route path='/register' element={<RegisterForm />} />
    </Routes>
    </Router>
    </>
  );
}

export default App;
