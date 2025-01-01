import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";

export default defineConfig({
  plugins: [react()],
  server: {
    host: "0.0.0.0",  // 외부 네트워크에서 접근 가능
    port: 5173,  // Vite 서버 포트 설정
  },
});
