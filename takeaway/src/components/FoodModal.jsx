import React, { useState, useEffect } from 'react';
import { FaTimes } from 'react-icons/fa';

const FoodModal = ({ food, isOpen, onClose, addToCart }) => {
  const [selectedIngredients, setSelectedIngredients] = useState([]);

  useEffect(() => {
    if (food) {
      setSelectedIngredients(food.ingredients || []);
    }
  }, [food]);

  const toggleIngredient = (ingredient) => {
    setSelectedIngredients((prevIngredients) =>
      prevIngredients.includes(ingredient)
        ? prevIngredients.filter((item) => item !== ingredient) // Remove if selected
        : [...prevIngredients, ingredient] // Add if not selected
    );
  };

  if (!isOpen || !food) {
    return null; // Don't render if modal isn't open or no food
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg max-w-md w-full">
        <div className="flex justify-between items-center p-4 border-b border-gray-200 dark:border-gray-600">
          <h3 className="text-xl font-semibold text-gray-900 dark:text-white">{food.name}</h3>
          <button onClick={onClose} className="text-gray-500 hover:text-gray-700 dark:text-gray-300 dark:hover:text-white">
            <FaTimes />
          </button>
        </div>

        <div className="p-4">
          <img src={food.image} alt={food.name} className="w-full h-48 object-cover rounded-lg" />
          <p className="mt-2 text-gray-700 dark:text-gray-300">{food.description}</p>
          <div className="mt-4 text-lg font-bold text-gray-900 dark:text-white">
            Price: ${food.price.toFixed(2)}
          </div>

          <div className="mt-4">
            <h4 className="text-lg font-semibold text-gray-900 dark:text-white">Ingredients</h4>
            <ul className="mt-2">
              {food.ingredients.map((ingredient, index) => (
                <li key={index} className="flex items-center my-2">
                  <input
                    type="checkbox"
                    checked={selectedIngredients.includes(ingredient)}
                    onChange={() => toggleIngredient(ingredient)} // Toggle selection
                    className="mr-2"
                  />
                  <span className="text-gray-700 dark:text-gray-300">{ingredient}</span>
                </li>
              ))}
            </ul>
          </div>
        </div>

        <div className="flex justify-end p-4 border-t border-gray-200 dark:border-gray-600">
          <button
            onClick={onClose}
            className="bg-gray-200 text-gray-800 px-4 py-2 rounded hover:bg-gray-300 dark:bg-gray-600 dark:text-white"
          >
            Close
          </button>
          <button
            onClick={() => {
              addToCart({ ...food, selectedIngredients });
              onClose(); // Close the modal after adding to cart
            }}
            className="bg-blue-500 text-white px-4 py-2 rounded ml-2 hover:bg-blue-600"
          >
            Add to Cart
          </button>
        </div>
      </div>
    </div>
  );
};

export default FoodModal;
