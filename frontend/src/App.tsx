import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import ResumeFeedbackPage from "./pages/ResumeFeedbackPage";
import SearchPage from "./pages/SearchPage";
import Upload from "./pages/Upload";
import MyInfoPage from "./pages/MyInfoPage";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

// QueryClient 생성
const queryClient = new QueryClient();

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <div className="flex flex-col min-h-screen">
          <div className="flex-grow">
            <Routes>
              <Route path="/" element={<MainPage />} />
              <Route
                path="/feedback/resumeId"
                element={<ResumeFeedbackPage />}
              />

              <Route path="/search" element={<SearchPage />} />
              <Route path="/search" element={<SearchPage />} />
              <Route path="/upload" element={<Upload />} />
              <Route path="/myInfo" element={<MyInfoPage />} />
            </Routes>
          </div>
        </div>
      </BrowserRouter>
    </QueryClientProvider>
  );
}
