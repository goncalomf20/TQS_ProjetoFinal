import React, { useState } from 'react';
import FoodCardsList from './FoodList';

function Kiosk({ onAddToCart, cart }) {
  // Example data for different foods
  const foods = [
    {
      id: 1,
      name: 'Pizza',
      image: 'https://example.com/pizza.jpg',
      description: 'A delicious cheese pizza with a crispy crust.',
      price: 11.99,
    },
    {
      id: 2,
      name: 'Sushi',
      image: 'https://example.com/sushi.jpg',
      description: 'Fresh sushi with salmon and avocado.',
      price: 12.99,
    },
    // Additional food items...
  ];

  return (
    <div className="min-h-screen bg-gray-100 dark:bg-gray-900 p-6">
      <h2 className="text-center text-3xl font-bold text-gray-900 dark:text-white py-8">
        Food Menu
      </h2>
      <FoodCardsList foods={foods} onAddToCart={onAddToCart} cart={cart} />
    </div>
  );
}

export default Kiosk;
