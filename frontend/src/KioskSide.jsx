import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Correct import
import Kiosk from './components/Kiosk';
import { Checkout } from './components/Checkout';
import { SidebarDC } from './components/Sidebar';
import { CartButton } from './components/CartButton';
import { PaymentChoice } from './components/PaymentChoice';



function KioskSide({cart , addOnCart}) {
  const [cartQuantity , setCartQuantity] = React.useState(0);

 

  useEffect(() => {
    setCartQuantity(cart.reduce((total, item) => total + item.quantity, 0));
  }, [cart]);
  
return (
    
      <div className="flex min-h-screen relative">
        <SidebarDC />
        <div className="flex-1 p-4">
        <Kiosk onAddToCart={addOnCart} cart={cart} />
        </div>
        <CartButton cartQuantity={cartQuantity} />
      </div>

    
  );
}

export default KioskSide;