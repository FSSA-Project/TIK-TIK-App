import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const RegisterForm = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    terms: false,
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Handle form submission logic here
    console.log(formData);
  };

  return (
    <div className="form-container">
      <h1>Register</h1>
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
            pattern="^[a-zA-Z0-9_@./#&+-]*$"
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
        <button type="submit">Submit</button>
      </form>
      <div className="separator">
        <span>Or</span>
      </div>
      <div className="social-buttons">
        <button className="social-button google">G</button>
        <button className="social-button facebook">f</button>
      </div>
      <p className="login-link">
        Already have an account? <Link to="/">Log In</Link>
      </p>
    </div>
  );
};

export default RegisterForm;
