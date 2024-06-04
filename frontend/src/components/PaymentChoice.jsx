import React from 'react';
import { useNavigate } from 'react-router-dom';
import cashImage from '../assets/cash.jpg'; // Correct the path based on your project structure
import cardImage from '../assets/credit-card.jpg'; // Correct the path based on your project structure
import Receipt from './Receipt';
import CardPayment from './CardPayment';

export function PaymentChoice({ total_price , orderId , setCart}) {
  const navigate = useNavigate();
  const [showReceipt, setShowReceipt] = React.useState(false);
  const [showCardPayment, setShowCardPayment] = React.useState(false);
  const handleCashPayment = () => {
    alert('Payed with cash');
    setShowReceipt(true);
    setTimeout(() => {
      setShowReceipt(false);
      setCart([]); // Clear the cart after payment
      navigate('/');
    }, 15000);
  };

  const handleCardPayment = () => {
    setShowCardPayment(true);
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100 dark:bg-gray-900">
      {(!showReceipt && !showCardPayment) && (
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8 p-4">
        {/* Card for Cash Payment */}
        <div
          className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer"
          onClick={handleCashPayment}
        >
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white">Pay with Cash</h2>
          <img
            src={cashImage}
            alt="Stack of cash"
            className="w-full h-48 object-cover mt-4 rounded"
          />
          <p className="text-gray-700 dark:text-gray-300 mt-4">
            Click here to proceed with cash payment.
          </p>
        </div>

        {/* Card for Card Payment */}
        <div
          className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer"
          onClick={handleCardPayment}
        >
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white">Pay with Card</h2>
          <img
            src={cardImage}
            alt="Credit card"
            className="w-full h-48 object-cover mt-4 rounded"
          />
          <p className="text-gray-700 dark:text-gray-300 mt-4">
            Click here to proceed with credit card payment.
          </p>
        </div>
      </div>
      )}
      {showReceipt && (
        <Receipt total_price={total_price} orderId={orderId}></Receipt>
      )}
      {showCardPayment && (
        <CardPayment setCart={setCart} total_price={total_price} orderId={orderId}></CardPayment>  
      )}
    </div>
  );
}

export default PaymentChoice;
