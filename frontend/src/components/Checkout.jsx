import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FaCheckCircle, FaTimesCircle } from 'react-icons/fa';

export function Checkout() {
  const navigate = useNavigate(); // Correctly use useNavigate for routing

  const [cart, setCart] = React.useState([]);

  // Initialize cart items (ensuring consistent IDs and quantities)
  React.useEffect(() => {
    const initialCart = [
      {
        id: 1,
        quantity: 2,
        name: 'Pizza',
        image: '/src/assets/pizza.jpeg',
        price: 11.99,
        ingredients: {
          cheese: 'yes',
          tomato_sauce: 'yes',
          flour: 'yes',
        },
      },
      {
        id: 5,
        quantity: 1,
        name: 'Ham and Cheese Croissant',
        image: '/src/assets/ham_and_cheese.jpeg',
        price: 3.99,
        ingredients: {
          croissant: 'yes',
          ham: 'yes',
          cheese: 'yes',
        },
      },
      {
        id: 6,
        quantity: 1,
        name: 'Large Coffee',
        image: '/src/assets/large_coffee.jpg',
        price: 1.99,
        ingredients: {
          coffee: 'yes',
          water: 'yes',
          sugar: 'no',
        },
      },
      {
        id: 7,
        quantity: 1,
        name: 'Tuna Sandwich',
        image: '/src/assets/tuna_sandwich.jpg',
        price: 1.99,
        ingredients: {
          bread: 'yes',
          tuna: 'yes',
          lettuce: 'yes',
          mayo: 'yes',
          tomato: 'no',
        },
      },
    ];
    setCart(initialCart); // Set the cart state
  }, []); // Run once on component mount

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
                    {Object.entries(item.ingredients).map(([key, value]) => (
                      <li key={key} className="flex items-center mr-4">
                        {value === 'yes' ? (
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
        onClick={() => navigate('/payment-choice')} // Navigate to payment
      >
        Proceed to Payment
      </button>
    </div>
  );
}

export default Checkout;
