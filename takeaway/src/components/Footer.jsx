import React, { useState } from 'react';
import { FaShoppingCart } from 'react-icons/fa';
import { Dialog, Transition } from '@headlessui/react'; // For modal transition effects
import { Fragment } from 'react'; // For rendering transitions

const CartModal = ({ isOpen, onClose }) => {
  return (
    <Transition show={isOpen} as={Fragment}>
      <Dialog as="div" className="relative z-30" onClose={onClose}>
        <Transition.Child
          as={Fragment}
          enter="ease-out duration-300"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="ease-in duration-200"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <div className="fixed inset-0 bg-black bg-opacity-50" /> {/* Background overlay */}
        </Transition.Child>

        <div className="fixed inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-full">
            <Transition.Child
              as={Fragment}
              enter="ease-out duration-300"
              enterFrom="scale-95 opacity-0"
              enterTo="scale-100 opacity-100"
              leave="ease-in duration-200"
              leaveFrom="scale-100 opacity-100"
              leaveTo="scale-95 opacity-0"
            >
              <Dialog.Panel className="w-full max-w-md bg-white rounded-lg shadow-xl dark:bg-gray-800 p-6">
                <Dialog.Title className="text-lg font-medium text-gray-900 dark:text-white">
                  Your Cart
                </Dialog.Title>
                <Dialog.Description className="text-sm text-gray-700 dark:text-gray-300">
                  List of items in your cart.
                </Dialog.Description>
                {/* Modal content */}
                <p>Cart items and details go here.</p>

                <div className="flex justify-end mt-4">
                  <button
                    className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
                    onClick={onClose}
                  >
                    Close
                  </button>
                </div>
              </Dialog.Panel>
            </Transition.Child>
          </div>
        </div>
      </Dialog>
    </Transition>
  );
};

const Footer = () => {
  const [isModalOpen, setIsModalOpen] = useState(false); // State for modal visibility

  const toggleModal = () => {
    setIsModalOpen(!isModalOpen); // Toggle modal state
  };

  return (
    <>
      <footer className="fixed bottom-0 left-0 z-20 w-full p-4 bg-white border-t border-gray-200 shadow md:flex md:items-center md:justify-between md:p-6 dark:bg-gray-800 dark:border-gray-600">
        <span className="text-sm text-gray-500 sm:text-center dark:text-gray-400">
          © 2024 <a href="#" className="hover:underline">DetiCafé</a>
        </span>

        <div className="flex justify-end w-full md:w-auto">
          <button
            onClick={toggleModal} // Open the modal when clicking on the cart icon
            className="text-gray-500 dark:text-blue-400 hover:text-gray-700 dark:hover:text-gray-300"
          >
            <FaShoppingCart size={24} className="text-blue-600" />
          </button>
        </div>
      </footer>

      {/* The modal component */}
      <CartModal isOpen={isModalOpen} onClose={toggleModal} />
    </>
  );
};

export default Footer;
