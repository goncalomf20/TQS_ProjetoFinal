import React from 'react';

export function FoodCard({ food, onAddToCart }) {
  const addToCheckout = () => {
    if (onAddToCart) {
      onAddToCart(food.id, food.price); // Pass the food_id and price
    }
  };

  return (
    <div className="max-w-sm rounded-lg shadow-md overflow-hidden bg-white dark:bg-gray-800 hover:shadow-lg transition-shadow">
      <img
        src={food.image}
        alt={food.name}
        className="w-full h-48 object-cover"
      />
      <div className="p-4">
        <h3 className="text-xl font-semibold text-gray-900 dark:text-white">
          {food.name}
        </h3>
        <p className="text-gray-700 dark:text-gray-400">
          {food.description}
        </p>
        {/* Display the price */}
        <div className="mt-2 text-lg text-gray-900 dark:text-white">
          Price: ${food.price.toFixed(2)}
        </div>
        <button
          onClick={addToCheckout}
          className="mt-4 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        >
          Add to Checkout
        </button>
      </div>
    </div>
  );
}
