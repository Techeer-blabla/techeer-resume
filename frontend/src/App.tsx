// src/App.tsx
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import MainPage from './pages/MainPage';
import ResumeFeedbackPage from './pages/ResumeFeedbackPage';

const App: React.FC = () => {
  return (
    <BrowserRouter>
      <div className="flex flex-col min-h-screen">
        <Navbar />
        <div className="flex-grow">
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/resume-feedback" element={<ResumeFeedbackPage />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;
