import React, {useState} from "react";

const Login = () => {
    return (
        <div className="form-container">
        <h1>Sign in</h1>
        <form>
      <div className='form-group'>
        <input type='text' className='sign-in-username' placeholder="Username or email" />
      </div>
      <div className='form-group'>
        <input type='password' className='sign-in-password' placeholder="Password" />
      </div>

        <button type='submit'>Login</button>
      </form>
      <br></br>
      <p className="login-link">
        New to account? <a href="/register">Register</a>
      </p>
     </div>
    );
}

export default Login;