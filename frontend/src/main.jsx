import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import WaitingApp from './WaitingApp.jsx'
import './WaitingScreen.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <WaitingApp />
    <App />
  </React.StrictMode>,
)
