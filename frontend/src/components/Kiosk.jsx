import React, { useState , useEffect} from 'react';
import FoodCardsList from './FoodList';
import { useSearchParams } from 'react-router-dom';

function Kiosk({ onAddToCart, cart }) {  

  const allFoods = [
    {
      id: 1,
      name: 'Pizza',
      image: '/src/assets/pizza.jpeg',
      description: 'A delicious cheese pizza with a crispy crust.',
      price: 11.99,
      category: 2,
      ingredients: ['cheese', 'tomato sauce', 'flour'],
    },
    {
      id: 2,
      name: 'Sushi',
      image: '/src/assets/sushi.jpg',
      description: 'Fresh sushi with salmon and avocado.',
      price: 12.99,
      category: 2,
      ingredients: ['salmon', 'rice', 'avocado'],
    },
    {
      id: 3,
      name: 'Burger',
      image: '/src/assets/burguer.jpg',
      description: 'Juicy beef patty with fresh vegetables and cheese.',
      price: 9.99,
      category: 2,
      ingredients: ['beef', 'lettuce', 'tomato'],
    },
    {
      id: 4,
      name: 'Pasta',
      image: '/src/assets/pasta.jpeg',
      description: 'Spaghetti with tomato sauce and meatballs.',
      price: 8.99,
      category: 2,
      ingredients: ['spaghetti', 'tomato sauce', 'meatballs'],
    },
    {
      id: 5,
      name: 'Ham and Cheese Croissant',
      image: '/src/assets/ham_and_cheese.jpeg',
      description: 'Freshly baked croissant with ham and cheese.',
      price: 3.99,
      category: 5,
      ingredients: ['croissant', 'ham', 'cheese'],
    },
    {
      id: 5,
      name: 'Large Coffee',
      image: '/src/assets/large_coffee.jpg',
      description: 'A large cup of freshly brewed coffee.',
      price: 1.99,
      category: 6,
      ingredients: ['coffee', 'water', 'sugar'],
    },
    {
      id: 5,
      name: 'Tuna Sandwich',
      image: '/src/assets/tuna_sandwich.jpg',
      description: 'A classic tuna sandwich with lettuce and mayo.',
      price: 1.99,
      category: 3,
      ingredients: ['bread', 'tuna', 'lettuce'],
    },
  ];

  const [foods, setFoods] = useState(allFoods);

  const [searchParams] = useSearchParams(); // Get search params from the URL

  useEffect(() => {
    const category = searchParams.get('category'); // Retrieve the 'category' query parameter
    if (category) {
      const filteredFoods = allFoods.filter(
        (food) => food.category === parseInt(category)
      );
      setFoods(filteredFoods); // Update state with filtered foods
    } else {
      setFoods(allFoods); // If no category, show all foods
    }
  }, [searchParams]); // Re-run effect when search params change

  return (
    <div className="min-h-screen bg-gray-100 dark:bg-gray-900 p-6">
      <h2 className="text-center text-3xl font-bold text-gray-900 dark:text-white py-8">
        Food Menu
      </h2>
      <FoodCardsList foods={foods} onAddToCart={onAddToCart} cart={cart} /> {/* Pass filtered foods */}
    </div>
  );
}

export default Kiosk;