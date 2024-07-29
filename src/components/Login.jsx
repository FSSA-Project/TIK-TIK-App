import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const LoginForm = () => {
  const [formData, setFormData] = useState({ email: '', password: '' });
  const [message, setMessage] = useState('');
  const [errors, setErrors] = useState({});
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();

  const validateForm = () => {
    const newErrors = {};
    if (!formData.email) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Email address is invalid';
    }
    if (!formData.password) {
      newErrors.password = 'Password is required';
    } else if (formData.password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters long';
    }
    return newErrors;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevFormData) => ({ ...prevFormData, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formErrors = validateForm();
    if (Object.keys(formErrors).length > 0) {
      setErrors(formErrors);
      return;
    }

    try {
      const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/user/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email: formData.email, password: formData.password }),
      });

      const data = await response.json();
      if (response.ok) {
        setMessage('Login successful');
        setTimeout(() => navigate('/'), 2000);
      } else {
        setMessage(`Login failed: ${data.message}`);
      }
    } catch (error) {
      setMessage('Login failed: Network error');
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
            placeholder="Email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
          {errors.email && <p className="error">{errors.email}</p>}
        </div>
        <div className="form-group password-input">
          <input
            type={showPassword ? 'text' : 'password'}
            className="sign-in-password"
            placeholder="Password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
          <span
            className="toggle-password"
            onClick={() => setShowPassword(!showPassword)}
          >
            {showPassword ? (
              <svg xmlns="http://www.w3.org/2000/svg" fill="grey" viewBox="0 0 24 24" width="24px" height="24px">
                <path d="M12 4.5C7.30558 4.5 3.40156 7.65747 1.5 12C3.40156 16.3425 7.30558 19.5 12 19.5C16.6944 19.5 20.5984 16.3425 22.5 12C20.5984 7.65747 16.6944 4.5 12 4.5ZM12 17.5C8.59887 17.5 5.68657 15.1835 4.5 12C5.68657 8.81648 8.59887 6.5 12 6.5C15.4011 6.5 18.3134 8.81648 19.5 12C18.3134 15.1835 15.4011 17.5 12 17.5ZM12 9.5C10.6193 9.5 9.5 10.6193 9.5 12C9.5 13.3807 10.6193 14.5 12 14.5C13.3807 14.5 14.5 13.3807 14.5 12C14.5 10.6193 13.3807 9.5 12 9.5ZM12 13.5C11.4477 13.5 11 13.0523 11 12.5C11 11.9477 11.4477 11.5 12 11.5C12.5523 11.5 13 11.9477 13 12.5C13 13.0523 12.5523 13.5 12 13.5Z" />
              </svg>
            ) : (
              <svg xmlns="http://www.w3.org/2000/svg" fill="grey" viewBox="0 0 24 24" width="24px" height="24px">
                <path d="M12 4.5C7.30558 4.5 3.40156 7.65747 1.5 12C3.40156 16.3425 7.30558 19.5 12 19.5C16.6944 19.5 20.5984 16.3425 22.5 12C20.5984 7.65747 16.6944 4.5 12 4.5ZM12 17.5C8.59887 17.5 5.68657 15.1835 4.5 12C5.68657 8.81648 8.59887 6.5 12 6.5C15.4011 6.5 18.3134 8.81648 19.5 12C18.3134 15.1835 15.4011 17.5 12 17.5ZM12 9.5C10.6193 9.5 9.5 10.6193 9.5 12C9.5 13.3807 10.6193 14.5 12 14.5C13.3807 14.5 14.5 13.3807 14.5 12C14.5 10.6193 13.3807 9.5 12 9.5ZM12 13.5C11.4477 13.5 11 13.0523 11 12.5C11 11.9477 11.4477 11.5 12 11.5C12.5523 11.5 13 11.9477 13 12.5C13 13.0523 12.5523 13.5 12 13.5ZM2.5 2.5L21.5 21.5L20.5 22.5L1.5 3.5L2.5 2.5Z" />
              </svg>
            )}
          </span>
          {errors.password && <p className="error">{errors.password}</p>}
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
