import ResumePageGroup from "./ResumePageGroup";
import Footer from "./Footer";

function MainContainer() {
  return (
    <div className="w-full min-h-screen flex flex-col bg-white">
      {/* Scrollable Content with space for the Footer */}
      <div className="flex-grow overflow-y-auto px-6 py-4 pb-16">
        {/* Adjusted padding and bottom padding to prevent overlap with Footer */}
        <ResumePageGroup pages={3} />{" "}
        {/* Adjust 'pages' to the number of resume pages */}
      </div>
      <Footer />
    </div>
  );
}

export default MainContainer;
