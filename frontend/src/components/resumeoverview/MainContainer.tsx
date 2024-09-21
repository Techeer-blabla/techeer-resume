import ResumePageGroup from "./ResumePageGroup";
import Footer from "./Footer";

function MainContainer() {
  return (
    <div className="w-full h-screen flex flex-col bg-white">
      {/* Scrollable Content with space for the fixed NavBar and Footer */}
      <div className="flex-grow overflow-y-auto mt-16 mb-16 px-6">
        {" "}
        {/* Adjusted margin */}
        <ResumePageGroup pages={3} />{" "}
        {/* Adjust 'pages' to the number of resume pages */}
      </div>

      {/* Footer */}
      <Footer />
    </div>
  );
}

export default MainContainer;
