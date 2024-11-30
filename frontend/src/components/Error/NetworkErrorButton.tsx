import React, { useState, useEffect } from "react";

const NetworkErrorButton = () => {
  const [throwError, setThrowError] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setThrowError(true); // Error will be triggered after 3 seconds
    }, 3000); // Delay for 3 seconds

    return () => clearTimeout(timer); // Clean up the timer on component unmount
  }, []);

  if (throwError) {
    throw new Error("An unexpected error occurred!"); // Throw an error when the state is true
  }

  return (
    <div>
      <h2>에러 발생!!</h2>
    </div>
  );
};

export default NetworkErrorButton;
