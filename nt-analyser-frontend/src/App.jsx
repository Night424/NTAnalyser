import React, { useEffect, useState } from 'react';
import { getStatus } from './api';
import reactLogo from './assets/react.svg';
import viteLogo from '/vite.svg';
import './App.css';


function App() {
  const [count, setCount] = useState('Loading...')

  useEffect(() => {
    const fetchData = async () => {
        const data = await getStatus();
        setStatus(data.status);
    };
    fetchData();
  }, []);

  return (
    <div className="App">
        <h1>Network Traffic Analyser</h1>
        <p>Status: {status}</p>
    </div>
  );
}

export default App
