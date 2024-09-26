import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import MainPage from "./pages/MainPage";
import ResumeFeedbackPage from "./pages/ResumeFeedbackPage";
import SearchPage from "./pages/SearchPage";

export default function App() {
  return (
    <BrowserRouter>
      <div className="flex flex-col min-h-screen">
        {/* <Navbar /> */}
        <div className="flex-grow">
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/resume-feedback" element={<ResumeFeedbackPage />} />
            <Route path="/search" element={<SearchPage />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}
