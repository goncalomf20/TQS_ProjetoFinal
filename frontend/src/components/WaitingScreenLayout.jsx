import React from 'react';
import WaitingScreen from './WaitingScreen';

function WaitingScreenLayout() {
  return (
    <div className="flex min-h-screen relative">
      <div className="flex-1 p-4">
        <WaitingScreen />
      </div>
    </div>
  );
}

export default WaitingScreenLayout;