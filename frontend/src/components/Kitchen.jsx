import React from 'react';

const Kitchen = () => {
  const orders = [
    { id: 101, details: ['Chesse and ham croissant', 'Large coffee'], status: 'pending' },
    { id: 102, details: ['Tuna Sandwich(-tomato)', 'Water'], status: 'pending' },
    { id: 103, details: ['Espresso'], status: 'pending' },
  ];

  const handleOrderReady = (orderId) => {
    console.log(`Order ${orderId} is ready`);
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
