import React from 'react';
import { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';


const Service = () => {
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
          setOrders((prev) => {
            // Deduplicate orders based on orderId
            if (!prev.some(existingOrder => existingOrder.orderId === order.orderId)) {
              return [...prev, order];
            }
            return prev;
          });
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

    const getColorByStatus = (status) => {
        switch (status) {
            case 'in progress':
                return 'bg-yellow-500';
            case 'canceled':
                return 'bg-red-500';
            case 'ready':
                return 'bg-green-500';
            default:
                return 'bg-white'; // Default for any unknown status
        }
    };

    

    return (
        <>
            <div className="flex items-center justify-center pt-10">    
                <h1 className="text-4xl font-bold">Orders</h1>
            </div>
            <div className='grid p-20 gap-10 grid-cols-5'>
                {orders.map((order) => (
                    <a
                        key={order.order_id}
                        href="#"
                        className={`block max-w-sm p-6 border border-gray-200 rounded-lg shadow hover:bg-gray-100 ${getColorByStatus(order.status)}`}
                    >
                        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                            Order #{order.order_id}
                        </h5>
                        <p className="font-normal text-gray-700 dark:text-gray-400">
                            Status: {order.status}
                        </p>
                    </a>
                ))}
            </div>
        </>
    );
};

export default Service;
