import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Receipt from './Receipt';

const CardPayment = ({ total_price , orderId , setCart}) => {
  const [cardDetails, setCardDetails] = useState({
    cardholderName: '',
    cardNumber: '',
    expiryDate: '',
    cvv: '',
  });
  const [showReceipt, setShowReceipt] = React.useState(false);

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCardDetails((prevDetails) => ({
      ...prevDetails,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('Payed with cash');
    setShowReceipt(true);
    setTimeout(() => {
      setShowReceipt(false);
      setCart([]);
      navigate('/');
    }, 15000);

  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100 dark:bg-gray-900">
    {!showReceipt && (
      <div className="bg-white dark:bg-gray-800 p-8 rounded-lg shadow-lg w-full max-w-md">
        <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">Card Payment</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="cardholderName" className="block text-gray-700 dark:text-gray-300 mb-2">Cardholder Name</label>
            <input
              type="text"
              id="cardholderName"
              name="cardholderName"
              value={cardDetails.cardholderName}
              onChange={handleChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          <div className="mb-4">
            <label htmlFor="cardNumber" className="block text-gray-700 dark:text-gray-300 mb-2">Card Number</label>
            <input
              type="text"
              id="cardNumber"
              name="cardNumber"
              value={cardDetails.cardNumber}
              onChange={handleChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          <div className="flex mb-4">
            <div className="w-1/2 pr-2">
              <label htmlFor="expiryDate" className="block text-gray-700 dark:text-gray-300 mb-2">Expiry Date</label>
              <input
                type="text"
                id="expiryDate"
                name="expiryDate"
                value={cardDetails.expiryDate}
                onChange={handleChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="MM/YY"
                required
              />
            </div>
            <div className="w-1/2 pl-2">
              <label htmlFor="cvv" className="block text-gray-700 dark:text-gray-300 mb-2">CVV</label>
              <input
                type="text"
                id="cvv"
                name="cvv"
                value={cardDetails.cvv}
                onChange={handleChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              />
            </div>
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 transition-colors"
          >
            Pay Now
          </button>
        </form>
      </div>)}
      {showReceipt && (
        <Receipt total_price={total_price} orderId={orderId}></Receipt>
      )}
    </div>
    
  );
};

export default CardPayment;
