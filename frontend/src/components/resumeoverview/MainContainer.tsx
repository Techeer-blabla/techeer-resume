import ResumePageGroup from "./ResumePageGroup";
import Footer from "./Footer.tsx";
import { AddFeedbackPoint, FeedbackPoint } from "../../types.ts";

type MainContainerProps = {
  feedbackPoints: FeedbackPoint[];
  addFeedbackPoint: (point: Omit<AddFeedbackPoint, "id">) => void;
  deleteFeedbackPoint: (id: number) => void;
  editFeedbackPoint: (item: AddFeedbackPoint) => void;
  hoveredCommentId: number | null;
  setHoveredCommentId: (id: number | null) => void;
};

function MainContainer({
  feedbackPoints,
  addFeedbackPoint,
  deleteFeedbackPoint,
  editFeedbackPoint,
  hoveredCommentId,
  setHoveredCommentId,
}: MainContainerProps) {
  return (
    <div className="w-full flex flex-col bg-white h-[90vh] ">
      {/* Scrollable Content with space for the fixed NavBar and Footer */}
      <div className="flex-grow overflow-y-auto mt-16 mb-6 px-6">
        {/* Adjusted margin */}
        <ResumePageGroup
          pages={3} // Adjust 'pages' to the number of resume pages
          feedbackPoints={feedbackPoints}
          addFeedbackPoint={addFeedbackPoint}
          deleteFeedbackPoint={deleteFeedbackPoint}
          editFeedbackPoint={editFeedbackPoint}
          hoveredCommentId={hoveredCommentId}
          setHoveredCommentId={setHoveredCommentId}
        />
      </div>
      <Footer />
    </div>
  );
}

export default MainContainer;
