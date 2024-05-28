import React, { useEffect, useState } from 'react';
import '../css/WaitingScreen.css';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export function WaitingScreen() {
  const [pendingOrders, setPendingOrders] = useState([]);
  const [readyOrders, setReadyOrders] = useState([]);
  const [client, setClient] = useState(null);
  const [connected, setConnected] = useState(false);

  // useEffect(() => {
  //   fetch('http://localhost:8080/api/order/getAllOrders')
  //     .then(response => response.json())
  //     .then(data => {
  //       // Add all orders to the pendingOrders array
  //       const pending = data.map(order => order.orderId);
  //       setPendingOrders(pending);
  //     })
  //     .catch(error => {
  //       console.error('Error fetching orders:', error);
  //     });
  // }, []);

  useEffect(() => {
    if (!connected) {
      const socket = new SockJS('http://localhost:8080/ws');
      const stompClient = Stomp.over(socket);

      stompClient.connect({}, () => {
        console.log('Connected to the WebSocket server');
        stompClient.subscribe('/topic/orders', (message) => {
          const order = JSON.parse(message.body);
          console.log('Order received:', order.orderId);

          setPendingOrders((prev) => {
            // Deduplicate orders
            if (!prev.includes(order.orderId)) {
              return [...prev, order.orderId];
            }
            return prev;
          });
        });
        setClient(stompClient);
        setConnected(true);
      }, (error) => {
        console.error('Error connecting to WebSocket', error);
      });

      // Cleanup WebSocket connection on component unmount
      return () => {
        if (client) {
          client.disconnect(() => {
            console.log('Disconnected from WebSocket');
          }, (error) => {
            console.error('Error disconnecting from WebSocket', error);
          });
        }
      };
    }
  }, [connected, client]);

  useEffect(() => {
    console.log('Pending orders:', pendingOrders);
  }, [pendingOrders]);

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
