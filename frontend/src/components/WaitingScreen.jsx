import React from 'react';
import '../css/WaitingScreen.css';

export function WaitingScreen() {

  const pendingOrders = ['101', '102', '103'];
  const readyOrders = ['104', '105'];

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