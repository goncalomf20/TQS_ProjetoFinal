import React from 'react';
import { useNavigate } from 'react-router-dom';

export function PaymentChoice() {
  const navigate = useNavigate();

  const handleCashPayment = () => {
    // Simulate action for cash payment
    alert('You chose to pay with cash.');
  };

  const handleCardPayment = () => {
    // Navigate to a different page for card payment
    navigate('/pay-with-card');
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100 dark:bg-gray-900">
      <div className="grid grid-cols-2 gap-8">
        {/* Card for Cash Payment */}
        <div
          className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer"
          onClick={handleCashPayment} // Action on click
        >
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white">Pay with Cash</h2>
          <img
            src="src/assets/cash.jpg" // Path to the cash image
            alt="Stack of cash"
            className="w-full h-48 object-cover mt-4 rounded" // Styling the image
          />
          <p className="text-gray-700 dark:text-gray-300 mt-4">
            Click here to proceed with cash payment.
          </p>
        </div>

        {/* Card for Card Payment */}
        <div
          className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer"
          onClick={handleCardPayment} // Navigate to card payment
        >
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white">Pay with Card</h2>
          <img
            src="src/assets/credit-card.jpg" // Path to the credit card image
            alt="Credit card"
            className="w-full h-48 object-cover mt-4 rounded"
          />
          <p className="text-gray-700 dark:text-gray-300 mt-4">
            Click here to proceed with credit card payment.
          </p>
        </div>
      </div>
    </div>
  );
}
