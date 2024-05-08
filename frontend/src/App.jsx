import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Correct import
import Kiosk from './components/Kiosk';
import { Checkout } from './components/Checkout';
import { SidebarDC } from './components/Sidebar';
import { CartButton } from './components/CartButton';
import { PaymentChoice } from './components/PaymentChoice';
import { WaitingScreen } from './components/WaitingScreen';
import KioskSide from './KioskSide';


function App() {
  const [cart, setCart] = React.useState([]);

  return (
    <Router>
          <Routes> 
            <Route path="/" element={<KioskSide  />} /> {/* Use element prop */}
            <Route path="/waiting-screen" element={<WaitingScreen />} />
            <Route path="/checkout" element={<Checkout cart={cart} />} />
            <Route path="/payment-choice" element={<PaymentChoice />} />
          </Routes>
    </Router>
  );
}

export default App;
