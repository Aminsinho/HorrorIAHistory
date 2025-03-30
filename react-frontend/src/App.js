import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css';
import Login from './pages/Login'; // Importa la página de login
import Register from './pages/Register'; // Importa la página de registro
import GamePage from './pages/Game'; // Importa la página del juego
import Dashboard from './pages/Dashboard'; // Importa la página de dashboard

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [token, setToken] = useState(null);

  // Verificar si ya hay un token guardado
  useEffect(() => {
    const savedToken = localStorage.getItem("jwtToken");
    if (savedToken) {
      setToken(savedToken);
      setIsLoggedIn(true);
    }
  }, []);

  return (
    <Router>
      <div className="App">
        <Routes>
          {/* Definir las rutas de las páginas */}
          <Route path="/login" element={<Login onLogin={setToken} />} />
          <Route path="/register" element={<Register />} />
          {/* Mostrar el juego o el dashboard si está logueado */}
          <Route path="/game" element={isLoggedIn ? <GamePage userId={token} /> : <div>Please log in first.</div>} />
          <Route path="/" element={isLoggedIn ? <Dashboard /> : <div>Please log in first.</div>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
