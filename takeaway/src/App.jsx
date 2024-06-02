import React, { useState } from 'react'
import Navbar from './components/Navbar';
import Jumbotron from './components/Jumbotron';
import Menu from './components/Menu';
import Footer from './components/Footer';
import { useEffect } from 'react';



function App() {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = React.useState([]);
  const [categories, setCategories] = useState([]);


  useEffect(() => {
    fetch("http://localhost:8080/api/products/getAllProducts")
      .then((response) => response.json())
      .then((data) => setProducts(data)); // Set categories from API response
  }
  , []); 

  useEffect(() => {
    fetch("http://localhost:8080/api/category/getAllCategories")
      .then((response) => response.json())
      .then((data) => setCategories(data)); // Set categories from API response
    }
  , []); 



  const handleAddToCart = (food, selectedItems) => { // Accept price as a parameter
    const updatedCart = [...cart];

    const existingItem = updatedCart.find((item) => item.foodId === food.productId);
    if (existingItem) {
      existingItem.quantity += 1;
    } else {
      updatedCart.push({ "name": food.name ,"foodId": food.productId , "quantity": 1, "price": food.price , "orderDetails" : selectedItems }); // Use the passed price
    }
    
    console.log(updatedCart);
    
    setCart(updatedCart);
  };

  const handleQuantityChange = (id, quantity) => {
    if (quantity === 0) {
      setCart(cart.filter((item) => item.foodId !== id)); // Remove item from the cart
    } else {
      setCart(
        cart.map((item) =>
          item.foodId === id ? { ...item, quantity } : item
        )
      ); // Update the quantity in the cart
    }
  }

  const handleRemoveItem = (id) => {
    setCart(cart.filter((item) => item.foodId !== id)); // Remove item from the cart
  }

  const calculateTotal = () => {
    return cart.reduce((total, item) => total + item.price * item.quantity, 0).toFixed(2); // Calculate total price
  }


  

  return (
    <>
    <Navbar />
    <Jumbotron />
    <Menu categories={categories} products={products} handleAddToCart={handleAddToCart}/>
    <Footer cart={cart} handleQuantityChange={handleQuantityChange} handleRemoveItem={handleRemoveItem} calculateTotal={calculateTotal} />
    </>
  )
}

export default App
