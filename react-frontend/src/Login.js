// src/Login.js

import React, { useState } from 'react';
import axios from 'axios';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        const credentials = {
            username,
            password,
        };

        try {
            const response = await axios.post('http://localhost:8080/token', credentials);

            if (response.data.token) {
                // Guardar el token en localStorage o sessionStorage
                localStorage.setItem('jwtToken', response.data.token);
                console.log('Login successful');
                // Redirigir al dashboard o a la siguiente parte del juego
                window.location.href = '/dashboard'; // Redirigir a otra página
            }
        } catch (error) {
            setErrorMessage('Login failed. Please check your credentials.');
            console.error('Error during login:', error);
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">Login</button>
            </form>
            {errorMessage && <p>{errorMessage}</p>}
        </div>
    );
};

export default Login;
