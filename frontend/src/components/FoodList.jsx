import React from 'react';
import { FoodCard } from './Food'; // Ensure this is the correct import for FoodCard

const FoodCardsList = ({ foods, onAddToCart, cart }) => {

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 p-6">
      {foods.map((food) => (
        <FoodCard
          key={food.productId} // Ensure unique key (e.g., food.id)
          food={food}
          onAddToCart={onAddToCart} // Pass the callback to add to cart
          cart={cart} // Pass the current cart state (if needed)
        />
      ))}
    </div>
  );
};

export default FoodCardsList;
