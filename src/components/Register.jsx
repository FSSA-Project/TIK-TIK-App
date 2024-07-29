import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const RegisterForm = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    terms: false,
  });

  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const { email, username, password } = formData;

    try {
      const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/user/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, name: username, password }),
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
    <div className="form-container">
      <h1>Join our task</h1>
      <p>Stay organized effortlessly: Your tasks, your way, every day.</p>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <input
            type="text"
            id="username"
            name="username"
            placeholder="Username"
            value={formData.username}
            onChange={handleChange}
            pattern="^[a-zA-Z0-9._]+$" title="Username should only contain letters, numbers, dots, and underscores."
            required
          />
        </div>
        <div className="form-group">
          <input
            type="email"
            id="email"
            name="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <input
            type="password"
            id="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label>
            <input
              type="checkbox"
              name="terms"
              checked={formData.terms}
              onChange={handleChange}
              required
            />
            I agree to the terms & conditions
          </label>
        </div>
        <button type="submit">Sign Up</button>
      </form>
      <div className="separator">
        <span>OR</span>
      </div>
      <div className="social-buttons">
        <button className="social-button google"><span>G</span>Sign in with Google</button>
      </div>
      <p className="login-link">
        Already have an account? <Link to="/">Log In</Link>
      </p>
    </div>
  );
};

export default RegisterForm;
