import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Correct import
import WaitingScreenLayout from './components/WaitingScreenLayout'; // Correct import



function WaitingApp() {
  return (
    <Router>
      <Routes>
        <Route path="/waiting-screen" element={<WaitingScreenLayout />} />
      </Routes>
    </Router>
  );
}

export default WaitingApp;