import ResumePageGroup from "./ResumePageGroup";
import { FeedbackPoint, CommentItem } from "../../types";
import Footer from "./Footer.tsx";

type MainContainerProps = {
  comments: CommentItem[];
  addFeedbackPoint: (point: Omit<FeedbackPoint, "id" | "type">) => void;
  deleteCommentItem: (id: string) => void;
  editCommentItem: (item: CommentItem) => void;
  hoveredCommentId: string | null;
  setHoveredCommentId: (id: string | null) => void;
};

function MainContainer({
  comments,
  addFeedbackPoint,
  deleteCommentItem,
  editCommentItem,
  hoveredCommentId,
  setHoveredCommentId,
}: MainContainerProps) {
  const feedbackPoints = comments.filter(
    (item) => item.type === "feedback"
  ) as FeedbackPoint[];

  return (
    <div className="w-full flex flex-col bg-white h-[90vh] ">
      {/* Scrollable Content with space for the fixed NavBar and Footer */}
      <div className="flex-grow overflow-y-auto mt-16 mb-6 px-6">
        {/* Adjusted margin */}
        <ResumePageGroup
          pages={3} // Adjust 'pages' to the number of resume pages
          feedbackPoints={feedbackPoints}
          addFeedbackPoint={addFeedbackPoint}
          deleteCommentItem={deleteCommentItem}
          editCommentItem={editCommentItem}
          hoveredCommentId={hoveredCommentId}
          setHoveredCommentId={setHoveredCommentId}
        />
      </div>
      <Footer />
    </div>
  );
}

export default MainContainer;
