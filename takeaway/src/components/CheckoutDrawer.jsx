import React from 'react';
import { FaCheckCircle, FaTimesCircle, FaPlus, FaMinus } from 'react-icons/fa';

const CheckoutDrawer = ({ isOpen, onClose, cart, handleQuantityChange, handleRemoveItem, calculateTotal ,cartQuantity,toggleModal}) => {

   return (
    <div className={`fixed top-0 right-0 z-40 h-screen p-4 overflow-y-auto transition-transform ${isOpen ? 'translate-x-0' : 'translate-x-full'} bg-white w-80 dark:bg-gray-800`} tabIndex="-1" aria-labelledby="drawer-right-label">
      <h5 id="drawer-right-label" className="inline-flex items-center mb-4 text-base font-semibold text-gray-500 dark:text-gray-400">
        <svg className="w-4 h-4 mr-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
          <path d="M7 18c-1.1 0-1.99.9-1.99 2S5.9 22 7 22s2-.9 2-2-.9-2-2-2zm0-2c1.66 0 3-1.34 3-3H4c0 1.66 1.34 3 3 3zm10 2c-1.1 0-1.99.9-1.99 2S15.9 22 17 22s2-.9 2-2-.9-2-2-2zm0-2c1.66 0 3-1.34 3-3h-6c0 1.66 1.34 3 3 3zm-9.83-5h13.66c.77 0 1.47-.53 1.72-1.27L23 7H6.62l-.94-2H1V3h4.42l4.71 10.59-1.45 2.48C8.35 16.16 7.83 17 7 17H5v2h1.73c.27-.6.79-1.07 1.42-1.29L11 14h7v-2H8.41l.95-2H20v-2H8.41L7.17 8H5.83l.34.74L4.96 10H3.6l1.02-2H19.2L17.6 14.73c-.18.29-.41.53-.72.7z"/>
        </svg>
        Cart
      </h5>
      <button type="button" onClick={onClose} aria-controls="drawer-right-example" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 absolute top-2.5 right-2.5 inline-flex items-center justify-center dark:hover:bg-gray-600 dark:hover:text-white">
        <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
          <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
        </svg>
        <span className="sr-only">Close menu</span>
      </button>
      
      {cart.length === 0 ? (
        <div>No items in the cart.</div>
      ) : (
        <div className="flex flex-col gap-4">
          {cart.map((item) => (
            <div key={item.foodId} className="flex items-center justify-between bg-white dark:bg-gray-800 p-4 rounded-lg shadow">
              <div className="flex-1">
                <h4 className="text-xl font-semibold text-gray-900 dark:text-white">{item.name}</h4>
                <div className="flex items-center text-gray-700 dark:text-gray-300">
                  <span>Quantity:</span>
                  <button
                    onClick={() => handleQuantityChange(item.foodId, item.quantity - 1)}
                    disabled={item.quantity <= 1}
                    className="ml-2 p-1 bg-gray-200 rounded hover:bg-gray-300 dark:bg-gray-700 dark:hover:bg-gray-600"
                  >
                    <FaMinus />
                  </button>
                  <span className="mx-2">{item.quantity}</span>
                  <button
                    onClick={() => handleQuantityChange(item.foodId, item.quantity + 1)}
                    className="p-1 bg-gray-200 rounded hover:bg-gray-300 dark:bg-gray-700 dark:hover:bg-gray-600"
                  >
                    <FaPlus />
                  </button>
                </div>
                <div className="flex mt-2">
                  <span className="mr-2">Ingredients:</span>
                  <ul className="flex flex-wrap">
                    {Object.entries(item.orderDetails).map(([key, value], index) => (
                      <li key={`${key}-${index}`} className="flex items-center mr-4">
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
                <div className="flex items-center">
                <span className="text-lg text-gray-900 dark:text-white mr-4 border-r pr-4">
                  ${(item.price * item.quantity).toFixed(2)}
                </span>
                <button
                  onClick={() => handleRemoveItem(item.foodId)}
                  className="bg-red-500 text-white px-3 py-2 rounded hover:bg-red-600"
                >
                  Remove
                </button>
              </div>
              </div>
            </div>
          ))}
        </div>
      )}
      {cartQuantity > 0 && (
        <div className="flex flex-col gap-4 mt-6">
          <div className="flex justify-between items-center bg-white dark:bg-gray-800 p-4 rounded-lg shadow">
            <span className="text-2xl font-bold text-gray-900 dark:text-white">Total:</span>
            <span className="text-2xl font-bold">${calculateTotal()}</span>
          </div>
          <button
          onClick={toggleModal}
          className="w-full py-2 bg-blue-600 text-white text-lg font-semibold rounded hover:bg-blue-700">
            Proceed to Checkout
          </button>
        </div>
      )}
    </div>
  );
};

export default CheckoutDrawer;
