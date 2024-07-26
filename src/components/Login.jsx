import React, {useState} from "react";

const Login = () => {

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        console.log('Username:', username);
        console.log('Password:', password);
    };



    return (
        <div className="form-container">
        <h1>Sign in</h1>
        <form onSubmit={handleSubmit}>
      <div className='form-group'>
        <input type='text' className='sign-in-username' placeholder="Username or email" value={username} onChange={(e) => setUsername(e.target.value)} />
      </div>
      <div className='form-group'>
        <input type='password' className='sign-in-password' placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
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