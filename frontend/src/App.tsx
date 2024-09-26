import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import ResumeFeedbackPage from "./pages/ResumeFeedbackPage";

export default function App() {
  return (
    <BrowserRouter>
      <div className="flex-grow">
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/resume-feedback" element={<ResumeFeedbackPage />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}
