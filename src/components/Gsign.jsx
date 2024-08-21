import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { initializeApp } from "firebase/app";
import { getAuth, signInWithPopup, GoogleAuthProvider } from "firebase/auth";

// Firebase configuration using environment variables
const firebaseConfig = {
    apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
    authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
    projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
    storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
    messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
    appId: process.env.REACT_APP_FIREBASE_APP_ID,
    measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID
};

// Initialize Firebase and authentication services
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const provider = new GoogleAuthProvider();

const GoogleSignInButton = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);

    const handleGoogleSignIn = async () => {
        setLoading(true);

        try {
            const result = await signInWithPopup(auth, provider);
            const user = result.user;
            console.log(user);
            // Extract user information
            const userName = user.displayName || '';
            const photoURL = user.photoURL || '';
            const userEmail = user.email || '';

            // Get the ID token from Firebase
            // const idToken = await getIdToken(user, true);
            const idToken =user.accessToken ||'';
            console.log(idToken);

            // Create an object to store in sessionStorage
            const userProfile = {
                userName,
                photoURL,
                userEmail,
                idToken,
            };

            // Store the user profile object in sessionStorage
            sessionStorage.setItem('userProfile', JSON.stringify(userProfile));

            // Send the ID token and user information to the backend
            const response = await fetch('https://todo-app-wpbz.onrender.com/api/v1/user/auth/google', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userProfile),
            });

            const data = await response.json();
            sessionStorage.setItem('usertoken', JSON.stringify(data.data));
            console.log('Backend response:', data);
            console.log('Login Success');

            navigate('/dashboard');
        } catch (error) {
            console.error('Error during sign-in:', error.code, error.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <button onClick={handleGoogleSignIn} className="social-button google" disabled={loading}>
            {loading ? 'Signing in...' : (
                <>
                    <span>
                        <svg version="1.1" xmlns="http://www.w3.org/2000/svg" height="25" width="60" viewBox="0 0 25 60" className="LgbsSe-Bz112c">
                            <g>
                                <path fill="#EA4335" d="M24 9.5c3.54 0 6.71 1.22 9.21 3.6l6.85-6.85C35.9 2.38 30.47 0 24 0 14.62 0 6.51 5.38 2.56 13.22l7.98 6.19C12.43 13.72 17.74 9.5 24 9.5z"></path>
                                <path fill="#4285F4" d="M46.98 24.55c0-1.57-.15-3.09-.38-4.55H24v9.02h12.94c-.58 2.96-2.26 5.48-4.78 7.18l7.73 6c4.51-4.18 7.09-10.36 7.09-17.65z"></path>
                                <path fill="#FBBC05" d="M10.53 28.59c-.48-1.45-.76-2.99-.76-4.59s.27-3.14.76-4.59l-7.98-6.19C.92 16.46 0 20.12 0 24c0 3.88.92 7.54 2.56 10.78l7.97-6.19z"></path>
                                <path fill="#34A853" d="M24 48c6.48 0 11.93-2.13 15.89-5.81l-7.73-6c-2.15 1.45-4.92 2.3-8.16 2.3-6.26 0-11.57-4.22-13.47-9.91l-7.98 6.19C6.51 42.62 14.62 48 24 48z"></path>
                                <path fill="none" d="M0 0h48v48H0z"></path>
                            </g>
                        </svg>
                    </span>
                    Sign in with Google
                </>
            )}
        </button>
    );
};

export default GoogleSignInButton;
