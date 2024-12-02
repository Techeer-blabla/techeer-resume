import { Outlet, Navigate } from "react-router-dom";
import axios from "./axiosInstance";
import { useEffect, useState } from "react";

const BASE_URL = import.meta.env.VITE_API_BASE_URL;

function ProtectedRoute() {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await axios.get(`${BASE_URL}/user`, {
          withCredentials: true, // HTTP-Only 쿠키 포함
        });

        console.log("응답 데이터: ", response.data);

        // 응답 코드가 USER_200일 경우 인증 성공
        if (response.data?.message === "USER_200") {
          console.log("성공: ", response.data?.message);
          setIsAuthenticated(true);
        } else {
          console.log("실패: ", response.data?.message);
          setIsAuthenticated(false);
        }
      } catch (error) {
        console.error("인증 요청 실패: ", error);
        setIsAuthenticated(false);
      }
    };

    checkAuth();
  }, []);

  if (isAuthenticated === null) {
    return <div>Loading...</div>; // 상태 확인 중 로딩 UI
  }

  return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
}

export default ProtectedRoute;
