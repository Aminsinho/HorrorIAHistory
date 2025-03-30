import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const RegisterPage = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate(); // Para redirigir después del registro

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/users/register", {
        username,
        email,
        password,
      });

      if (response.status === 200) {
        setMessage("User registered successfully");
        setTimeout(() => navigate("/login"), 2000); // Redirigir al login después de 2s
      }
    } catch (error) {
      setMessage("Error during registration: " + error.response?.data || "Unknown error");
    }
  };

  return (
    <div>
      <h1>Register</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Username</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Register</button>
      </form>
      {message && <p>{message}</p>}
      <p>Already have an account? <a href="/login">Login here</a></p>
    </div>
  );
};

export default RegisterPage;
