// src/pages/Dashboard.js
import React from "react";
import { Link } from "react-router-dom";

const Dashboard = () => {
  return (
    <div className="dashboard-container">
      <h1>Welcome to your Dashboard!</h1>
      <p>You are now logged in. From here, you can access the game or other features.</p>
      <div className="dashboard-links">
        <Link to="/game">Start Game</Link>
        <Link to="/profile">Profile</Link>
        {/* Puedes añadir más enlaces aquí para otras secciones */}
      </div>
    </div>
  );
};

export default Dashboard;
