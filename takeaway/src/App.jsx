import React, { useState } from 'react'
import Navbar from './components/Navbar';
import Jumbotron from './components/Jumbotron';
import Menu from './components/Menu';
import Footer from './components/Footer';



function App() {
  const [products, setProducts] = useState([]);
  const [category, setCategory] = useState();

  React.useEffect(() => {
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
        id: 6,
        name: 'Large Coffee',
        image: '/src/assets/large_coffee.jpg',
        description: 'A large cup of freshly brewed coffee.',
        price: 1.99,
        category: 6,
        ingredients: ['coffee', 'water', 'sugar'],
      },
      {
        id: 7,
        name: 'Tuna Sandwich',
        image: '/src/assets/tuna_sandwich.jpg',
        description: 'A classic tuna sandwich with lettuce and mayo.',
        price: 1.99,
        category: 3,
        ingredients: ['bread', 'tuna', 'lettuce' , 'mayo' , 'tomato'],
      },
    ];
    setProducts(allFoods);
    
  }, []);



  return (
    <>
    <Navbar />
    <Jumbotron />
    <Menu category={"All Food"} products={products}/>
    <Footer cartItems={[]}/>
    </>
  )
}

export default App
