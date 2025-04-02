// react-frontend/src/pages/Game.js
import React, { useState, useEffect, useRef } from "react";
import "./Game.css";
import clickSound from "./sounds/click.wav";
import errorSound from "./sounds/click.wav";
import typeSound from "./sounds/type.wav";

const Game = ({ userId }) => {
  const [story, setStory] = useState("");
  const [loading, setLoading] = useState(false);
  const [input, setInput] = useState("");
  const [decisions, setDecisions] = useState([]);
  const [messages, setMessages] = useState([]);
  const [isTyping, setIsTyping] = useState(false);
  const messagesEndRef = useRef(null);

  useEffect(() => {
    fetchMessagesAndResponses();
  }, []);

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const playSound = (sound) => {
    new Audio(sound).play();
  };

  const fetchMessagesAndResponses = async () => {
    try {
      const [messagesResponse, responsesResponse] = await Promise.all([
        fetch("http://localhost:8080/game/messages", {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
          }
        }),
        fetch("http://localhost:8080/game/responses", {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
          }
        })
      ]);

      const messages = await messagesResponse.json();
      const responses = await responsesResponse.json();

      const combinedMessages = [
        ...messages.map(msg => ({ id: "message", text: escapeText(msg.message), sender: "Tú", timestamp: new Date(msg.timestamp) })),
        ...responses.map(res => ({ id: "response", text: escapeText(res.response), sender: "IA", timestamp: new Date(res.timestamp) }))
      ];

      combinedMessages.sort((a, b) => a.timestamp - b.timestamp);

      setMessages(combinedMessages);
    } catch (error) {
      console.error("Error fetching messages and responses:", error);
    }
  };


  const sendMessage = async () => {
    if (input.trim() === "") return;
    playSound(clickSound);
    setLoading(true);
    setMessages(prevMessages => [...prevMessages, { text: escapeText(input), sender: "Tú", timestamp: new Date().toISOString(), isTemporary: true }]);
    setInput("");
    setIsTyping(true);
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
      fetchMessagesAndResponses();
      scrollToBottom();
    } catch (error) {
      playSound(errorSound);
      console.error("Error sending message:", error);
    }
    setLoading(false);
    setIsTyping(false);
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
    setIsTyping(true);
    const interval = setInterval(() => {
      setStory((prev) => prev + text[index]);
      playSound(typeSound);
      index++;
      if (index === text.length) {
        clearInterval(interval);
        setIsTyping(false);
      }
    }, 500);
  };

  const escapeText = (text) => {
    return text
      .replace(/\\n/g, "<br>")
      .replace(/\\/g, "\\\\")
      .replace(/\*\*(.*?)\*\*/g, "<b>$1</b>");
  };

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  return (
    <div className="game-container">
      <div className="story-box">
        {messages.map((msg, index) => (
          <div key={index} className={`message ${msg.sender === "Tú" ? "user-message" : "ia-message"} ${msg.isTemporary ? "temporary" : ""} ${msg.id}`}>
            <p className="sender">{msg.sender}</p>
            <p className="text" dangerouslySetInnerHTML={{ __html: msg.text }}></p>
            {msg.id === "response" && <hr className="response-separator" />}
          </div>
        ))}
        {isTyping && <div className="typing-indicator">Escribiendo...</div>}
        <div ref={messagesEndRef} />
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