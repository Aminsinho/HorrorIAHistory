import React, { useState, useEffect } from "react";
import "./Game.css";
import clickSound from "./sounds/click.wav";
import errorSound from "./sounds/click.wav";
import typeSound from "./sounds/type.wav";

const Game = ({ userId }) => {
  const [story, setStory] = useState("");
  const [loading, setLoading] = useState(false);
  const [input, setInput] = useState("");
  const [decisions, setDecisions] = useState([]);

  useEffect(() => {
    startGame();
    fetchMessages();
  }, []);

  const playSound = (sound) => {
    new Audio(sound).play();
  };

  const startGame = async () => {
    setLoading(true);
    try {
      const response = await fetch("http://localhost:8080/game/start", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
        }
      });
      const data = await response.json();
      simulateTyping(data.story);
      setDecisions(data.decisions || []);
    } catch (error) {
      playSound(errorSound);
      console.error("Error starting game:", error);
    }
    setLoading(false);
  };

  const fetchMessages = async () => {
    try {
      const response = await fetch("http://localhost:8080/game/messages", {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
        }
      });
      const messages = await response.json();
      const combinedMessages = messages.map(msg => msg.message).join("\n");
      setStory(combinedMessages);
    } catch (error) {
      console.error("Error fetching messages:", error);
    }
  };

  const makeDecision = async (decision) => {
    playSound(clickSound);
    setLoading(true);
    try {
      const response = await fetch("http://localhost:8080/game/decision", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
        },
        body: JSON.stringify({ decision })
      });
      const data = await response.json();
      simulateTyping(data.story);
      setDecisions(data.decisions || []);
    } catch (error) {
      playSound(errorSound);
      console.error("Error making decision:", error);
    }
    setLoading(false);
  };

  const rollback = async () => {
    playSound(clickSound);
    setLoading(true);
    try {
      const response = await fetch("http://localhost:8080/game/rollback", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
        }
      });
      const data = await response.json();
      simulateTyping(data.story);
      setDecisions(data.decisions || []);
    } catch (error) {
      playSound(errorSound);
      console.error("Error rolling back:", error);
    }
    setLoading(false);
  };

  const sendMessage = async () => {
    if (input.trim() === "") return;
    playSound(clickSound);
    setLoading(true);
    try {
      const response = await fetch("http://localhost:8080/game/message", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
        },
        body: JSON.stringify({ message: input })
      });
      const data = await response.json();
      simulateTyping(data.story);
      setDecisions(data.decisions || []);
      setInput("");
      fetchMessages(); // Actualizar los mensajes después de enviar uno nuevo
    } catch (error) {
      playSound(errorSound);
      console.error("Error sending message:", error);
    }
    setLoading(false);
  };

  const handleKeyPress = (event) => {
    if (event.key === "Enter") {
      sendMessage();
    }
  };

  const simulateTyping = (text) => {
    if (!text) {
      console.error("Text is undefined or null");
      return;
    }

    setStory("");
    let index = 0;
    const interval = setInterval(() => {
      setStory((prev) => prev + text[index]);
      playSound(typeSound);
      index++;
      if (index === text.length) clearInterval(interval);
    }, 500);
  };

  return (
    <div className="game-container">
      <div className="story-box">
        <p className="typing-text">{story}<span className="cursor">|</span></p>
      </div>
      <div className="options">
        {decisions.map((decision, index) => (
          <button key={index} onClick={() => makeDecision(decision)}>{decision}</button>
        ))}
        <button onClick={rollback} className="rollback">⏪ Rollback</button>
      </div>
      <div className="chat-input">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyPress={handleKeyPress}
          placeholder="Escribe tu mensaje..."
        />
        <button onClick={sendMessage}>Enviar</button>
      </div>
    </div>
  );
};

export default Game;