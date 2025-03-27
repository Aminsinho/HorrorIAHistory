import React, { useState, useEffect } from "react";
import './App.css';

function App() {
  const [input, setInput] = useState("");
  const [output, setOutput] = useState([]);
  const [loading, setLoading] = useState(false);
  const [partialResponse, setPartialResponse] = useState("");
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

  // Enviar comando a la API de Ollama
  const sendCommandToOllama = async (command) => {
    if (!token) {
      setOutput((prevOutput) => [
        ...prevOutput,
        <div key={prevOutput.length} className="error">
          You need to log in first.
        </div>,
      ]);
      return;
    }

    setLoading(true);
    setPartialResponse("");
    try {
      const response = await fetch("http://localhost:11434/api/generate", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`, // Agregar el token JWT a la cabecera
        },
        body: JSON.stringify({
          model: "mistral",
          prompt: command,
        }),
      });

      const reader = response.body.getReader();
      const decoder = new TextDecoder("utf-8");
      let done = false;
      let fullResponse = "";

      while (!done) {
        const { value, done: doneReading } = await reader.read();
        done = doneReading;
        const chunk = decoder.decode(value, { stream: true });
        const jsonChunks = chunk.split("\n").filter(Boolean).map(JSON.parse);

        jsonChunks.forEach((jsonChunk) => {
          fullResponse += jsonChunk.response;
        });
      }

      // Simular efecto de escritura
      simulateTyping(fullResponse, command);
    } catch (error) {
      setOutput((prevOutput) => [
        ...prevOutput,
        <div key={prevOutput.length} className="command">{`> ${command}`}</div>,
        <div key={prevOutput.length + 1} className="error">{`Error al interactuar con Ollama: ${error.message}`}</div>
      ]);
    }
    setLoading(false);
  };

  // Función para simular la escritura del texto
  const simulateTyping = (fullResponse, command) => {
    let index = 0;
    setPartialResponse(""); // Limpiar la respuesta parcial anterior

    const typingInterval = setInterval(() => {
      setPartialResponse((prev) => prev + fullResponse[index]);
      index++;

      if (index === fullResponse.length) {
        clearInterval(typingInterval); // Detener la escritura una vez que termine
        setOutput((prevOutput) => [
          ...prevOutput,
          <div key={prevOutput.length} className="command">{`> ${command}`}</div>,
          <div key={prevOutput.length + 1} className="typing">{partialResponse + fullResponse.slice(0, index)}</div>
        ]);
      }
    }, 150); // Cambiar este valor a 150ms entre cada letra escrita (puedes ajustarlo para hacerlo más lento)
  };

  // Manejador de envío de comando
  const handleSendCommand = () => {
    if (input.trim()) {
      sendCommandToOllama(input);
      setInput(""); // Limpiar la caja de texto
    }
  };

  const handleLogin = (token) => {
    setToken(token);
    setIsLoggedIn(true);
  };

  return (
    <div className="App">
      {!isLoggedIn ? (
        <div>Please log in first.</div>
      ) : (
        <div className="terminal">
          <div className="output">
            {output.map((line, index) => (
              <div key={index}>{line}</div>
            ))}
            {loading && <div className="loading">Loading...</div>}
          </div>
          <div className="input-container">
            <input
              type="text"
              value={input}
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && handleSendCommand()}
              placeholder="Type your command here..."
            />
            <button onClick={handleSendCommand}>Send</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
