import React, { useState, useEffect } from 'react';
import './App.css';

// Función para simular el efecto de escribir en la terminal
const typeWriter = (text, setText, speed = 50) => {
  let i = 0;
  const interval = setInterval(() => {
    setText((prev) => prev + text[i]);
    i++;
    if (i === text.length) {
      clearInterval(interval);
    }
  }, speed);
};

function App() {
  const [command, setCommand] = useState('');
  const [output, setOutput] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // Aquí puedes simular un mensaje inicial o una bienvenida
    typeWriter('Welcome to the Interactive Command Line!', setOutput, 100);
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!command.trim()) return;

    setLoading(true);
    setOutput((prev) => prev + '\n' + '> ' + command); // Muestra el comando que el usuario escribió

    // Aquí es donde realizamos la llamada a la API de Ollama
    try {
      const response = await fetch('https://api.ollama.com/v1/generate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer YOUR_API_KEY`,  // Reemplaza con tu clave de API
        },
        body: JSON.stringify({
          prompt: command,  // El comando del usuario es el prompt que le pasamos a la API
          model: 'mistral', // O cualquier otro modelo que quieras usar
        }),
      });

      const data = await response.json();
      const apiResponse = data?.response || 'No response from API';

      // Mostrar la respuesta de la API en la terminal
      typeWriter('\n' + apiResponse, setOutput, 50);
    } catch (error) {
      typeWriter('\nError: ' + error.message, setOutput, 50);
    }

    setLoading(false);
    setCommand('');
  };

  return (
    <div className="App">
      <div className="terminal">
        <pre>{output}</pre>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            value={command}
            onChange={(e) => setCommand(e.target.value)}
            placeholder="Type a command..."
            disabled={loading}
          />
          <button type="submit" disabled={loading}>Send</button>
        </form>
      </div>
    </div>
  );
}

export default App;
