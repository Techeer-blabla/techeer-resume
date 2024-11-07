import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import ResumeFeedbackPage from "./pages/ResumeFeedbackPage";
import SearchPage from "./pages/SearchPage";
import Upload from "./pages/Upload";
import PDF from "./pages/PDFPage";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import React from "react";

// QueryClient 생성
const queryClient = new QueryClient();

export default function App() {
  // const pdfUrl =
  //   "https://rexume.s3.ap-northeast-2.amazonaws.com/resume/a10d20c9-3_%E1%84%8C%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%B3%E1%86%BC%E1%84%86%E1%85%A7%E1%86%BC%E1%84%89%E1%85%A5%20%E1%84%8E%E1%85%AC%E1%84%89%E1%85%B5%E1%86%AB.pdf";

  return (
    <QueryClientProvider client={queryClient}>
      <React.StrictMode>
        <BrowserRouter>
          <div className="flex flex-col min-h-screen">
            <div className="flex-grow">
              <Routes>
                <Route path="/" element={<MainPage />} />
                <Route path="/feedback" element={<ResumeFeedbackPage />} />
                <Route path="/search" element={<SearchPage />} />
                <Route path="/upload" element={<Upload />} />
                <Route path="/pdf" element={<PDF />} />
              </Routes>
            </div>
          </div>
        </BrowserRouter>
      </React.StrictMode>
    </QueryClientProvider>
  );
}
