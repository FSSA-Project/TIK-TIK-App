import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const LoginForm = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/user/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email: formData.email, password: formData.password }),
      });

      const data = await response.json();
      console.log(data);

      if (response.ok) {
        setMessage('Register successfully');
        setTimeout(() => {
          navigate('/');
        }, 2000);
      } else {
        setMessage(`Register failed: ${data.message}`);
      }
    } catch (error) {
      setMessage('Register failed: Network error');
      console.error(error);
    }
  };

  return (
    <div className="form-container login">
      <h1>Welcome Back</h1>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <input
            type="text"
            className="sign-in-username"
            placeholder="Username or email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <input
            type="password"
            className="sign-in-password"
            placeholder="Password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
        </div>
        <button type="submit">Login</button>
      </form>
      <div className="separator">
        <span>OR</span>
      </div>
      <div className="social-buttons">
        <button className="social-button google">
          <span>G</span> Sign in with Google
        </button>
      </div>
      <br />
      <p className="login-link">
        Don't have an account? <Link to="/register">Sign Up here</Link>
      </p>
      {message && <p className="message">{message}</p>}
    </div>
  );
};

export default LoginForm;
