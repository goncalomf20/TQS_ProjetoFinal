import React, { useState, useEffect } from 'react'; // Import useState and useEffect
import { io } from 'socket.io-client'; 

const Kitchen = () => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const socket = io('http://localhost:8080'); // Adjust the URL as necessary

    socket.on('orderPosted', (newOrder) => {
      setOrders((prevOrders) => [...prevOrders, newOrder]);
    });

    return () => {
      socket.off('orderPosted');
    };
  }, []);

  const handleOrderReady = (orderId) => {
    console.log(`Order ${orderId} is ready`);
    // Handle marking order as ready
  };

  return (
    <div className="kitchen-screen bg-black h-screen flex flex-col items-center">
      <h2 style={{ color: 'white', textAlign: 'center', marginTop: '0' }} className="text-3xl font-bold mb-8">Kitchen Orders</h2>
      <div className="orders flex flex-wrap justify-center text-white text-center">
        {orders.map((order) => (
          <div key={order.id} className="bg-white rounded-lg shadow-md p-4 m-4 w-72">
            <div style={{ color: 'black' }} className="text-lg mb-2">{order.id}</div>
            <div style={{ color: 'black' }} className="text-sm mb-2">
              {order.details.map((detail, index) => (
                <p key={index}>{detail}</p>
              ))}
            </div>
            <button className="bg-gray-200 text-black py-2 px-4 rounded-md hover:bg-gray-300" onClick={() => handleOrderReady(order.id)}>Mark as Ready</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Kitchen;
