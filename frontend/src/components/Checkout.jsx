import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FaCheckCircle, FaTimesCircle } from 'react-icons/fa';

export function Checkout({cart}) {

  const handleQuantityChange = (id, quantity) => {
    if (quantity === 0) {
      setCart(cart.filter((item) => item.id !== id)); // Remove item from the cart
    } else {
      setCart(
        cart.map((item) =>
          item.id === id ? { ...item, quantity } : item
        )
      ); // Update the quantity in the cart
    }
  };

  const handleRemoveItem = (id) => {
    setCart(cart.filter((item) => item.id !== id)); // Remove item from the cart
  };
  
  const calculateTotal = () => {
    return cart.reduce((total, item) => total + item.price * item.quantity, 0).toFixed(2); // Calculate total price
  };

    const postCart = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/order/createOrder', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(cart),
        });
        if (response.ok) {
          // Cart successfully posted
          console.log('Cart posted successfully');
        } else {
          // Error posting cart
          console.error('Error posting cart');
        }
      } catch (error) {
        console.error('Error posting cart:', error);
      }
    };



  const handleClick = () => {
    postCart();
  }

  return (
    <div className="p-6 bg-gray-100 dark:bg-gray-900 min-h-screen">
      <h2 className="text-3xl font-bold text-gray-900 dark:text-white mb-6">Checkout</h2>

      {cart.length === 0 ? (
        <div>No items in the cart.</div>
      ) : (
        <div className="flex flex-col gap-4"> {/* Layout for cart items */}
          {cart.map((item) => (
            <div key={item.id} className="flex items-center justify-between bg-white dark:bg-gray-800 p-4 rounded-lg shadow">
              <div>
                <h4 className="text-xl font-semibold text-gray-900 dark:text-white">{item.name}</h4>
                <div className="flex items-center text-gray-700 dark:text-gray-300">
                  Quantity: 
                  <input
                    type="number"
                    min="1"
                    value={item.quantity}
                    onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value))}
                    className="ml-2 w-12 text-center border rounded"
                  />
                </div>
                <div className="flex mt-2">
                  <span className="mr-2">Ingredients:</span>
                  <ul className="flex flex-wrap">
                    {Object.entries(item.orderDetails).map(([key, value]) => (
                      <li key={key} className="flex items-center mr-4">
                        {value === true ? (
                          <FaCheckCircle className="text-green-500 mr-2" />
                        ) : (
                          <FaTimesCircle className="text-red-500 mr-2" />
                        )}
                        <span className="text-gray-700 dark:text-gray-300">{key}</span>
                      </li>
                    ))}
                  </ul>
                </div>
              </div>
              <div className="flex items-center">
                <span className="text-lg text-gray-900 dark:text-white mr-4 border-r pr-4"> {/* Added margin, padding, and border */}
                  ${(item.price * item.quantity).toFixed(2)}
                </span>
                <button
                  onClick={() => handleRemoveItem(item.id)}
                  className="bg-red-500 text-white px-3 py-2 rounded hover:bg-red-600"
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
          <span className="text-2xl font-bold">${calculateTotal()}</span> {/* Display total price */}
        </div>
      )}

      <button
        className="mt-6 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        onClick={handleClick} 
              >
        Proceed to Payment
      </button>
    </div>
  );
}

export default Checkout;
