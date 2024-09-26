export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      width: {
        // 22: "5.5rem",
        // 23: "5.8rem",
        // 29: "7.25rem",
        // 50: "12.5rem",
        // 70: "17.5rem",
        // 96: "24rem",
        // 108: "27rem",
        // 112: "28rem",
        125: "31.25rem",
        140: "35rem",
        160: "40rem",
      },
      fontFamily: {
        Pretendard: ["Pretendard", "sans-serif"],
      },
      colors: {
        primary: {
          DEFAULT: "#2bca43",
          black: "#000000",
          grayDark: "#333333",
          grayMedium: "#9c9c9c",
          gray: "#6d6d6d",
          lightBlue: "#abbdec",
          white: "#ffffff",
        },
      },
    },
  },
  plugins: [],
};
