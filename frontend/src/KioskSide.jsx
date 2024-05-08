import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Correct import
import Kiosk from './components/Kiosk';
import { Checkout } from './components/Checkout';
import { SidebarDC } from './components/Sidebar';
import { CartButton } from './components/CartButton';
import { PaymentChoice } from './components/PaymentChoice';

function KioskSide() {
    const [cart, setCart] = React.useState([]);

    const handleAddToCart = (food_id, price) => { // Accept price as a parameter
      const updatedCart = [...cart];
  
      const existingItem = updatedCart.find((item) => item.food_id === food_id);
      if (existingItem) {
        existingItem.quantity += 1;
      } else {
        updatedCart.push({ food_id, quantity: 1, price }); // Use the passed price
      }
  
      setCart(updatedCart);
    };
  
    const cartQuantity = cart.reduce((total, item) => total + item.quantity, 0);
return (
    
      <div className="flex min-h-screen relative">
        <SidebarDC />
        <div className="flex-1 p-4">
        <Kiosk onAddToCart={handleAddToCart} cart={cart} />
        </div>
        <CartButton cartQuantity={cartQuantity} />
      </div>

    
  );
}

export default KioskSide;