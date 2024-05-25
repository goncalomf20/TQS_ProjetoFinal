import React, { useEffect, useState } from 'react';
import '../css/WaitingScreen.css';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export function WaitingScreen() {
  const [pendingOrders, setPendingOrders] = useState([]);
  const [readyOrders, setReadyOrders] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/order/getAllOrders')
      .then(response => response.json())
      .then(data => {
        // Add all orders to the pendingOrders array
        const pending = data.map(order => order.name);
        setPendingOrders(pending);
      })
      .catch(error => {
        console.error('Error fetching orders:', error);
      });
  
    const socket = new SockJS('http://localhost:8080/ws');
    const client = Stomp.over(socket);
  
    client.connect({}, () => {
      console.log('Connected to the WebSocket server');
      client.subscribe('/topic/orders', (message) => {
        const order = JSON.parse(message.body);
        console.log('Order received:', order);
  
        // Always add the order to the pendingOrders array
        setPendingOrders((prev) => [...prev, order.name]);
      });
    }, (error) => {
      console.error('Error connecting to WebSocket', error);
    });
  
    // Cleanup WebSocket connection on component unmount
    return () => {
      if (client) {
        client.disconnect(() => {
          console.log('Disconnected from WebSocket');
        });
      }
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
}

export default WaitingScreen;
