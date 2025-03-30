import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const LoginPage = ({ onLogin }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate(); // Para redirigir

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/token", {
        username,
        password,
      });

      if (response.data.token) {
        // Guardar el token en localStorage
        localStorage.setItem("jwtToken", response.data.token);

        onLogin(response.data.token); // Aquí pasamos el token a App.js
        navigate("/"); // Redirigir al dashboard
      }
    } catch (error) {
      setErrorMessage("Login failed. Please check your credentials.");
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
      <p>Don't have an account? <a href="/register">Register here</a></p>
    </div>
  );
};

export default LoginPage;
