import React from "react";
import { GoogleLogin, GithubLogin } from "../api/auth.ts";

const LoginPage: React.FC = () => {
  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="bg-white p-6 rounded-lg shadow-md w-80">
        <h2 className="text-lg font-bold mb-4 text-gray-700">SNS 로그인</h2>
        <button
          className="flex items-center justify-center w-full py-2 mb-3 text-white bg-red-500 rounded hover:bg-red-600 transition"
          onClick={GoogleLogin}
        >
          <svg
            className="w-5 h-5 mr-2"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 48 48"
          >
            <path
              fill="#34A853"
              d="M46.6 24.3c0-1.4-.1-2.8-.4-4.1H24v8.1h12.8c-.6 3.1-2.4 5.7-5 7.4l8 6.3C43.9 37.6 46.6 31.4 46.6 24.3z"
            />
            <path
              fill="#4A90E2"
              d="M3.2 14.4c-1.3 2.4-2 5.1-2 7.9 0 2.7.7 5.4 2 7.9l8.3-6.4c-.7-2.1-1.1-4.4-1.1-6.7s.4-4.6 1.1-6.7l-8.3-6.4z"
            />
            <path
              fill="#FBBC05"
              d="M24 46.9c6.1 0 11.7-2.2 15.6-5.8l-8-6.3c-2.2 1.5-5.1 2.4-7.6 2.4-5.8 0-10.8-4.1-12.6-9.6l-8.3 6.4c3.6 8.2 11.6 13.9 20.9 13.9z"
            />
          </svg>
          Google Login
        </button>

        <button
          className="flex items-center justify-center w-full py-2 text-white bg-gray-800 rounded hover:bg-gray-900 transition"
          onClick={GithubLogin}
        >
          <svg
            className="w-5 h-5 mr-2"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
          >
            <path
              fill="currentColor"
              d="M12 0C5.37 0 0 5.37 0 12c0 5.3 3.438 9.8 8.207 11.39.6.11.793-.258.793-.575v-2.022c-3.338.723-4.037-1.61-4.037-1.61-.547-1.391-1.336-1.759-1.336-1.759-1.091-.747.083-.732.083-.732 1.206.083 1.838 1.236 1.838 1.236 1.072 1.835 2.81 1.305 3.492.997.109-.774.42-1.305.763-1.605-2.665-.304-5.465-1.333-5.465-5.93 0-1.309.467-2.38 1.236-3.22-.125-.303-.536-1.526.117-3.176 0 0 1.01-.323 3.31 1.232a11.5 11.5 0 013.013-.405c1.021.005 2.046.138 3.013.405 2.297-1.555 3.31-1.232 3.31-1.232.656 1.65.245 2.873.12 3.176.772.84 1.235 1.911 1.235 3.22 0 4.608-2.804 5.623-5.476 5.92.432.372.816 1.1.816 2.22v3.293c0 .32.192.69.8.574C20.565 21.796 24 17.3 24 12c0-6.63-5.37-12-12-12z"
            />
          </svg>
          GitHub Login
        </button>
      </div>
    </div>
  );
};

export default LoginPage;
