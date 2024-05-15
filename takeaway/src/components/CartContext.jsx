import React, { createContext, useState, useContext } from 'react';

// Create the Cart context
const CartContext = createContext();

// Custom hook to use the Cart context
export const useCart = () => {
  return useContext(CartContext);
};

// CartProvider Component
export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState([]);

  // Add an item to the cart
  const addToCart = (product) => {
    const existingItem = cart.find((item) => item.id === product.id);

    if (existingItem) {
      // If the item already exists, update its quantity
      setCart(
        cart.map((item) =>
          item.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        )
      );
    } else {
      // If it's a new item, add it to the cart
      setCart([...cart, { ...product, quantity: 1 }]);
    }
  };

  // Remove an item from the cart
  const removeFromCart = (productId) => {
    setCart(cart.filter((item) => item.id !== productId));
  };

  // Clear all items from the cart
  const clearCart = () => {
    setCart([]);
  };

  return (
    <CartContext.Provider value={{ cart, addToCart, removeFromCart, clearCart }}>
      {children}
    </CartContext.Provider>
  );
};
