import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

const Kitchen = () => {
  const [orders, setOrders] = useState([]);
  const [client, setClient] = useState(null);
  const [connected, setConnected] = useState(false);

  useEffect(() => {
    if (!connected) {
      const socket = new SockJS('http://localhost:8080/ws');
      const stompClient = Stomp.over(socket);

      stompClient.connect({}, () => {
        console.log('Connected to the WebSocket server');
        stompClient.subscribe('/topic/orders', (message) => {
          const order = JSON.parse(message.body);
          console.log('Order received:', order);
          if (order.status === 'PREPARING') {
          setOrders((prev) => {
            // Deduplicate orders based on orderId
            if (!prev.some(existingOrder => existingOrder.orderId === order.orderId)) {
              return [...prev, order];
            }
            return prev;
          });
        } else if (order.status === 'READY') {
          setOrders((prev) => prev.filter(existingOrder => existingOrder.orderId !== order.orderId));
        }
        });
        setClient(stompClient);
        setConnected(true);
      }, (error) => {
        console.error('Error connecting to WebSocket', error);
      });

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

  const handleOrderReady = (orderId) => {
    console.log(`Order ${orderId} is ready`);

    const updatedOrder = orders.find(order => order.orderId === orderId);
    if (updatedOrder) {
      updatedOrder.status = 'READY';
      client.send('/app/wsorder', {}, JSON.stringify(updatedOrder));
      setOrders(prevOrders => prevOrders.filter(order => order.orderId !== orderId));
    }
  };

  return (
    <div className="kitchen-screen bg-black h-screen flex flex-col items-center">
      <h2 style={{ color: 'white', textAlign: 'center', marginTop: '0' }} className="text-3xl font-bold mb-8">Kitchen Orders</h2>
      <div className="orders flex flex-wrap justify-center text-white text-center">
        {orders.map((order) => (
          <div key={order.orderId} className="bg-white rounded-lg shadow-md p-4 m-4 w-72">
            <div style={{ color: 'black' }} className="text-lg mb-2">{order.orderId}</div>
            <div style={{ color: 'black' }} className="text-sm mb-2">
            </div>
            <button className="bg-gray-200 text-black py-2 px-4 rounded-md hover:bg-gray-300" onClick={() => handleOrderReady(order.orderId)}>Mark as Ready</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Kitchen;
