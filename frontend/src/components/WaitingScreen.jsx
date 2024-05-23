import React, { useEffect, useState } from 'react';
import '../css/WaitingScreen.css';
import { io } from 'socket.io-client';

export function WaitingScreen() {
  const [pendingOrders, setPendingOrders] = useState([]);
  const [readyOrders, setReadyOrders] = useState([]);

  useEffect(() => {
    // Altere o endpoint para o seu endpoint WebSocket correto
    const socket = io('http://localhost:8080/websocket');

    socket.on('connect', () => {
      console.log('Conectado ao servidor');
      socket.emit('subscribe', '/topic/orders'); // Subscrever-se ao tópico
    });

    socket.on('/topic/orders', (data) => {
      console.log('Dados recebidos:', data);
      // Supondo que 'data' contenha informações sobre a ordem, como status e ID
      if (data.status === 'preparing') {
        setPendingOrders(prevOrders => [...prevOrders, data.id]);
      } else if (data.status === 'ready') {
        setReadyOrders(prevOrders => [...prevOrders, data.id]);
      }
    });

    return () => {
      socket.disconnect();
    };
  }, []);

  return (
    <div className="waiting-screen">
      <div className="pending-orders">
        <h2 className="title">Preparing</h2>
        <div className="order-container">
          {pendingOrders.map((order, index) => (
            <div key={index} className="order">{order}</div>
          ))}
        </div>
      </div>
      <div className="divider"></div>
      <div className="ready-orders">
        <h2 className="title">Ready</h2>
        <div className="order-container">
          {readyOrders.map((order, index) => (
            <div key={index} className="order">{order}</div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default WaitingScreen;
