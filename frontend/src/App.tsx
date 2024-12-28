import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import ResumeFeedbackPage from "./pages/ResumeFeedbackPage";
import SearchPage from "./pages/SearchPage";
import Upload from "./pages/Upload";
import Login from "./pages/LoginPage";
import ProtectedRoute from "./utils/Token";
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
              {/* resumeId와 userId는 전역 상태에서 관리되므로 URL로 넘길 필요 없음 */}
              <Route path="/feedback/:resumeId" element={<ResumeFeedbackPage />} />
              <Route path="/search" element={<SearchPage />} />
              <Route path="/login" element={<Login />} />
              <Route element={<ProtectedRoute />}>
                <Route path="/upload" element={<Upload />} />
                {/* <Route path="/myInfo" element={<MyInfoPage />} /> */}
              </Route>
            </Routes>
          </div>
        </div>
      </BrowserRouter>
    </QueryClientProvider>
  );
}
