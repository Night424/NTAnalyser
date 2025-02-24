import React, { useEffect, useState } from 'react';
import { getStatus } from './api';
import reactLogo from './assets/react.svg';
import viteLogo from '/vite.svg';
import './App.css';
import Dashboard from "./components/Dashboard";

function App() {
  return <Dashboard />;
}

export default App
