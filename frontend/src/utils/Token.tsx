import { Outlet, Navigate } from "react-router-dom";
import { getCookie } from "../utils/Cookies";

function ProtectedRoute() {

  const accessToken = getCookie("accessToken");
  const refreshToken = getCookie("refreshToken");

  if (accessToken && refreshToken) {
    return <Outlet />;
  } else {
    return <Navigate to="/login" replace />;
  }
}

export default ProtectedRoute;
