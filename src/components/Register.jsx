import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const RegisterForm = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    terms: false,
  });

  const [message,setMessage]=useState('');

  const handleChange=(e)=>{
    const {name,value,type,checked}=e.target;
    setFormData({
        ...formData,
        [name]:type==='checkbox' ? checked:value,
    });
  };

  const handleSubmit = async(e) => {
    e.preventDefault();

    const {username,email,password}=formData;

    const response =await fetch('https://66a47db25dc27a3c190905c9.mockapi.io/Fssa-TodoList/users',{
        method:'POST',
        headers:{
            'Content-Type':'application/json',
        },
        body:JSON.stringify({username,email,password}),
    });

    const data=await response.json();

    if(response.ok){
        setMessage('Register successfully');
    }else{
        setMessage('Register failed: ${data.message}');
    }
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
      {message && <p>{message}</p>}
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
