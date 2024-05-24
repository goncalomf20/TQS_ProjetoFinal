import React, { useState } from 'react';
import { HiShoppingCart } from 'react-icons/hi';
import CheckoutDrawer from './CheckoutDrawer';
import Checkout from './Checkout';

const Footer = ({ cart, handleQuantityChange, handleRemoveItem, calculateTotal }) => {
  const [isCheckoutOpen, setIsCheckoutOpen] = useState(false); // State for modal visibility
  const [cartQuantity, setCartQuantity] = React.useState(0);
  const [isCheckoutModalOpen, setIsCheckoutModalOpen] = useState(false);

  const toggleModal = () => {
    setIsCheckoutModalOpen(!isCheckoutModalOpen);
  };

  React.useEffect(() => {
    setCartQuantity(cart.reduce((total, item) => total + item.quantity, 0));
  }, [cart]);

  const toggleCheckout = () => {
    setIsCheckoutOpen(!isCheckoutOpen);
  };

  return (
    <>
      <footer className="fixed bottom-0 left-0 z-20 w-full p-4 bg-white border-t border-gray-200 shadow md:flex md:items-center md:justify-between md:p-6 dark:bg-gray-800 dark:border-gray-600">
        <span className="text-sm text-gray-500 sm:text-center dark:text-gray-400">
          © 2024 <a href="#" className="hover:underline">DetiCafé</a>
        </span>

        <div className="flex justify-end w-full md:w-auto">
          <button
            onClick={toggleCheckout}
            className="fixed bottom-4 right-4 bg-blue-500 text-white p-3 rounded-full shadow-lg hover:bg-blue-600 flex items-center justify-center"
          >
            <HiShoppingCart className="w-6 h-6" />
            {cartQuantity > 0 && ( /* Display badge if there's at least one item in the cart */
              <span className="ml-2 bg-red-500 text-white text-xs rounded-full px-2 py-1">
                {cartQuantity}
              </span>
            )}
          </button>
        </div>
      </footer>
      <CheckoutDrawer
        isOpen={isCheckoutOpen}
        onClose={toggleCheckout}
        cart={cart}
        handleQuantityChange={handleQuantityChange}
        handleRemoveItem={handleRemoveItem}
        calculateTotal={calculateTotal}
        cartQuantity={cartQuantity}
        toggleModal={toggleModal}
      />
      
    {isCheckoutModalOpen && (
      <Checkout toggleModal={toggleModal} cart={cart}  />
    )};
    </>
  );
};

export default Footer;
