import React, { useState } from 'react';

const Checkout = ({ toggleModal , cart }) => {
  const [name, setName] = useState('');
  const [time, setTime] = useState('');
  const [paymentMethod, setPaymentMethod] = useState('');
  const [showVisaForm, setShowVisaForm] = useState(false);

  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handleTimeChange = (event) => {
    setTime(event.target.value);
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

  const handlePaymentMethodChange = (event) => {
    setPaymentMethod(event.target.value);
    if (event.target.value === 'online') {
      setShowVisaForm(true);
    } else {
      setShowVisaForm(false);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Here you can handle the submission of the form, like sending the data to the server
    console.log('Name:', name);
    console.log('Time:', time);
    console.log('Payment Method:', paymentMethod);
  };

  return (
    <div>
      {/* Main modal */}
      <div 
        id="crud-modal" 
        tabIndex="-1" 
        aria-hidden="true" 
        className="fixed top-0 left-0 right-0 z-50 flex justify-center items-center w-full h-screen bg-gray-800 bg-opacity-50"
      >
        <div className="relative p-4 w-full max-w-md max-h-full">
          {/* Modal content */}
          <div className="relative bg-white rounded-lg shadow dark:bg-gray-700">
            {/* Modal header */}
            <div className="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
              <h3 className="text-lg font-semibold text-gray-900 dark:text-white">
                Checkout
              </h3>
              <button 
                type="button" 
                className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" 
                onClick={toggleModal}
              >
                <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                  <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                </svg>
                <span className="sr-only">Close modal</span>
              </button>
            </div>
            {/* Modal body */}
            <form className="p-4 md:p-5" onSubmit={handleSubmit}>
              <div className="grid gap-4 mb-4">
                <div>
                  <label htmlFor="name" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Name</label>
                  <input 
                    type="text" 
                    name="name" 
                    id="name" 
                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                    placeholder="Your name" 
                    value={name}
                    onChange={handleNameChange}
                    required 
                  />
                </div>
                <div>
                  <label htmlFor="time" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Checkout Time</label>
                  <input 
                    type="time" 
                    name="time" 
                    id="time" 
                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                    value={time}
                    onChange={handleTimeChange}
                    required 
                  />
                </div>
                <div>
                  <label htmlFor="paymentMethod" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Payment Method</label>
                  <select 
                    id="paymentMethod" 
                    name="paymentMethod" 
                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                    value={paymentMethod}
                    onChange={handlePaymentMethodChange}
                    required
                  >
                    <option value="" disabled>Select payment method</option>
                    <option value="cash">Cash</option>
                    <option value="online">Pay Now</option>
                  </select>
                </div>
              </div>
              {showVisaForm && (
                <div>
                    {/* Visa Form */}
                    <h4 className="text-lg font-semibold text-gray-900 dark:text-white">Visa Payment Form</h4>
                    <form>
                    <div className="mb-4">
                        <label htmlFor="cardNumber" className="block text-sm font-medium text-gray-900 dark:text-white">Card Number</label>
                        <input 
                        type="text" 
                        id="cardNumber" 
                        name="cardNumber" 
                        className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                        placeholder="Enter card number" 
                        required 
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="expiryDate" className="block text-sm font-medium text-gray-900 dark:text-white">Expiry Date</label>
                        <input 
                        type="text" 
                        id="expiryDate" 
                        name="expiryDate" 
                        className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                        placeholder="MM/YY" 
                        required 
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="cvv" className="block text-sm font-medium text-gray-900 dark:text-white">CVV</label>
                        <input 
                        type="text" 
                        id="cvv" 
                        name="cvv" 
                        className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                        placeholder="Enter CVV" 
                        required 
                        />
                    </div>
                    </form>
                </div>
                )}

                <button 
                type="submit" 
                onClick={postCart}
                disabled={!name || !time || !paymentMethod || (paymentMethod === 'online' && (!cardNumber || !expiryDate || !cvv))}
                className="text-white inline-flex justify-center items-center bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                >
                Confirm Checkout
                </button>

            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Checkout;
