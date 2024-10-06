import ResumePage from "./ResumePage";
import { AddFeedbackPoint, FeedbackPoint } from "../../types.ts";

type ResumePageGroupProps = {
  pages: number;
  feedbackPoints: FeedbackPoint[];
  addFeedbackPoint: (point: Omit<AddFeedbackPoint, "id" | "type">) => void;
  deleteFeedbackPoint: (id: number) => void;
  editFeedbackPoint: (item: AddFeedbackPoint) => void;
  hoveredCommentId: number | null;
  setHoveredCommentId: (id: number | null) => void;
};

function ResumePageGroup({
  pages,
  feedbackPoints,
  addFeedbackPoint,
  deleteFeedbackPoint,
  editFeedbackPoint,
  hoveredCommentId,
  setHoveredCommentId,
}: ResumePageGroupProps) {
  const pageComponents = [];

  for (let i = 1; i <= pages; i++) {
    pageComponents.push(
      <ResumePage
        key={i}
        pageNumber={i}
        feedbackPoints={feedbackPoints}
        addFeedbackPoint={addFeedbackPoint}
        deleteFeedbackPoint={deleteFeedbackPoint}
        editFeedbackPoint={editFeedbackPoint}
        hoveredCommentId={hoveredCommentId}
        setHoveredCommentId={setHoveredCommentId}
      />
    );
  }

  return <div>{pageComponents}</div>;
}

export default ResumePageGroup;
