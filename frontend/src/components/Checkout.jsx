import React from 'react';
import { useNavigate } from 'react-router-dom';

export function Checkout({ cart, onUpdateCart, onRemoveItem }) {
  const navigate = useNavigate(); // Use React Router's navigation hook

  const calculateTotal = () => cart.reduce((total, item) => total + item.quantity * item.price, 0).toFixed(2);

  const handleQuantityChange = (food_id, quantity) => {
    const parsedQuantity = Math.max(1, parseInt(quantity, 10));
    if (onUpdateCart) {
      onUpdateCart(food_id, parsedQuantity);
    }
  };

  const handleRemoveItem = (food_id) => {
    if (window.confirm("Are you sure you want to remove this item?")) {
      if (onRemoveItem) {
        onRemoveItem(food_id);
      }
    }
  };

  return (
    <div className="p-6 bg-gray-100 dark:bg-gray-900 min-h-screen">
      <h2 className="text-3xl font-bold text-gray-900 dark:text-white mb-6">Checkout</h2>

      {cart.length === 0 ? (
        <div>No items in the cart.</div>
      ) : (
        <div className="flex flex-col gap-4">
          {cart.map((item) => (
            <div key={item.food_id} className="flex items-center justify-between bg-white dark:bg-gray-800 p-4 rounded-lg shadow">
              <div>
                <h4 className="text-xl font-semibold text-gray-900 dark:text-white">{item.name}</h4>
                <p className="text-gray-700 dark:text-gray-300">
                  Quantity: 
                  <input
                    type="number"
                    min="1"
                    value={item.quantity}
                    onChange={(e) => handleQuantityChange(item.food_id, parseInt(e.target.value))}
                    className="ml-2 w-12 text-center border rounded"
                  />
                </p>
              </div>
              <div className="flex items-center">
                <span className="mr-4 text-lg text-gray-900 dark:text-white">${(item.price * item.quantity).toFixed(2)}</span>
                <button
                  className="bg-red-500 text-white p-2 rounded hover:bg-red-600"
                  onClick={() => handleRemoveItem(item.food_id)}
                >
                  Remove
                </button>
              </div>
            </div>
          ))}
        </div>
      )}

      {cart.length > 0 && (
        <div className="flex justify-between items-center mt-6 bg-white dark:bg-gray-800 p-4 rounded-lg shadow">
          <span className="text-2xl font-bold text-gray-900 dark:text-white">Total:</span>
          <span className="text-2xl font-bold">${calculateTotal()}</span>
        </div>
      )}

      <button
        className="mt-6 bg-blue-500 text-white p-3 rounded-lg hover:bg-blue-600"
        onClick={() => navigate('/payment-choice')} // Use React Router to navigate
      >
        Proceed to Payment
      </button>
    </div>
  );
}
