import React, { useState } from 'react';
import FoodModal from './FoodModal';

const Menu = ({ category, products, addToCart }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);

  const openModal = (product) => {
    setSelectedProduct(product);
    setIsModalOpen(true); // Open the modal
  };

  const closeModal = () => {
    setSelectedProduct(null); // Clear the selected product
    setIsModalOpen(false); // Close the modal
  };

  return (
    <div className="container mx-auto p-6">
      <h2 className="text-3xl font-bold text-gray-900 dark:text-white mb-6">{category}</h2>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {products.map((product, index) => (
          <div
            key={index}
            className="bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700"
          >
            <img
              className="rounded-t-lg w-full h-48 object-cover"
              src={product.image}
              alt={product.name}
            />
            <div className="p-5">
              <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                {product.name}
              </h5>
              <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
                {product.description}
              </p>
              <p className="mb-3 font-bold text-gray-900 dark:text-white">
                Price: ${product.price.toFixed(2)}
              </p>
              <button
                onClick={() => openModal(product)} // Open modal with selected product
                className="inline-flex items-center px-3 py-2 text-sm font-medium text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
              >
                View Details
              </button>
            </div>
          </div>
        ))}
      </div>

      {isModalOpen && (
        <FoodModal
          food={selectedProduct}
          isOpen={isModalOpen}
          onClose={closeModal}
          addToCart={addToCart} // Pass addToCart function for cart handling
        />
      )}
    </div>
  );
};

export default Menu;
