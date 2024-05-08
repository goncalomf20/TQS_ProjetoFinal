import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Correct import
import { Checkout } from './components/Checkout';
import { PaymentChoice } from './components/PaymentChoice';
import { WaitingScreen } from './components/WaitingScreen';
import  Kitchen  from './components/Kitchen';
{/*import  Service  from './components/Service'; */}
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
            <Route path="/kitchen" element={<Kitchen />} />
            {/*<Route path="/service" element={<Service />} />*/}
          </Routes>
    </Router>
  );
}

export default App;
