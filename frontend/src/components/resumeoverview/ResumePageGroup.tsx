import ResumePage from "./ResumePage";
import { FeedbackPoint, CommentItem } from "../../types";

type ResumePageGroupProps = {
  pages: number;
  feedbackPoints: FeedbackPoint[];
  addFeedbackPoint: (point: Omit<FeedbackPoint, "id" | "type">) => void;
  deleteCommentItem: (id: string) => void;
  editCommentItem: (item: CommentItem) => void;
  hoveredCommentId: string | null;
  setHoveredCommentId: (id: string | null) => void;
};

function ResumePageGroup({
  pages,
  feedbackPoints,
  addFeedbackPoint,
  deleteCommentItem,
  editCommentItem,
  hoveredCommentId,
  setHoveredCommentId,
}: ResumePageGroupProps) {
  const pageComponents = [];

  for (let i = 1; i <= pages; i++) {
    const pageFeedbackPoints = feedbackPoints.filter(
      (point) => point.pageNumber === i
    );
    pageComponents.push(
      <ResumePage
        key={i}
        pageNumber={i}
        feedbackPoints={pageFeedbackPoints}
        addFeedbackPoint={addFeedbackPoint}
        deleteCommentItem={deleteCommentItem}
        editCommentItem={editCommentItem}
        hoveredCommentId={hoveredCommentId}
        setHoveredCommentId={setHoveredCommentId}
      />
    );
  }

  return <div>{pageComponents}</div>;
}

export default ResumePageGroup;
