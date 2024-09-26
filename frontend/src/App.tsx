import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import ResumeFeedbackPage from "./pages/ResumeFeedbackPage";
import Upload from "./pages/Upload";

export default function App() {
  return (
    <BrowserRouter>
      <div className="flex flex-col min-h-screen">
        <Navbar />
        <div className="flex-grow">
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/resume-feedback" element={<ResumeFeedbackPage />} />
            <Route path="/upload" element={<Upload />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}
