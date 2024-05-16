import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Correct import
import { Checkout } from './components/Checkout';
import { PaymentChoice } from './components/PaymentChoice';
import { WaitingScreen } from './components/WaitingScreen';
import  Kitchen  from './components/Kitchen';
import  Service  from './components/Service';
import KioskSide from './KioskSide';


function App() {
  const [cart, setCart] = React.useState([]);
  const handleAddToCart = (food, selectedItems) => { // Accept price as a parameter
    const updatedCart = [...cart];

    const existingItem = updatedCart.find((item) => item.id === food.id);
    if (existingItem) {
      existingItem.quantity += 1;
    } else {
      updatedCart.push({ "name": food.name ,"foodId": food.id , "quantity": 1, "price": food.price , "orderDetails" : selectedItems }); // Use the passed price
    }
    
    console.log(updatedCart);
    
    setCart(updatedCart);
  };

  return (
    <Router>
      <Routes> 
        <Route path="/" element={<KioskSide cart={cart} addOnCart={handleAddToCart}/>} />
        <Route path="/waiting-screen" element={<WaitingScreen />} />
        <Route path="/checkout" element={<Checkout cart={cart} />} />
        <Route path="/payment-choice" element={<PaymentChoice />} />
        <Route path="/kitchen" element={<Kitchen />} />
        <Route path="/service" element={<Service />} /> {/* Add params */}
      </Routes>
    </Router>
  );
}

export default App;
