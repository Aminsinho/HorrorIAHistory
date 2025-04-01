// react-frontend/src/pages/Dashboard.js
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const Dashboard = () => {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);

  const startGame = async () => {
    setIsLoading(true);
    try {
      const response = await fetch("http://localhost:8080/game/start", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
        }
      });
      const data = await response.json();
      console.log("Game started:", data);
      navigate("/game");
    } catch (error) {
      console.error("Error starting game:", error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="dashboard-container">
      <h1>Welcome to your Dashboard!</h1>
      <p>You are now logged in. From here, you can access the game or other features.</p>
      <div className="dashboard-links">
        <button onClick={startGame} disabled={isLoading}>
          {isLoading ? "Generando una historia..." : "Start Game"}
        </button>
        <Link to="/profile">Profile</Link>
        {/* Puedes añadir más enlaces aquí para otras secciones */}
      </div>
    </div>
  );
};

export default Dashboard;