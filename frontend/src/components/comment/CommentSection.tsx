// src/components/Comments/CommentSection.tsx
import React from "react";
import CommentList from "./CommentList";
import CommentForm from "./CommentForm";
import ErrorMessage from "../UI/ErrorMessage";
import LoadingSpinner from "../UI/LoadingSpinner";
import { CommentItem, FeedbackPoint } from "../../pages/ResumeFeedbackPage.tsx";

interface CommentSectionProps {
  comments: CommentItem[];
  addComment: (text: string) => void;
  addFeedbackPoint: (point: Omit<FeedbackPoint, "id" | "type">) => void;
  deleteCommentItem: (id: string) => void;
  editCommentItem: (item: CommentItem) => void;
  hoveredCommentId: string | null;
  setHoveredCommentId: (id: string | null) => void;
  loading?: boolean;
  error?: string;
}

function CommentSection({
  comments,
  addComment,
  deleteCommentItem,
  editCommentItem,
  hoveredCommentId,
  setHoveredCommentId,
  loading = false,
  error = "",
}: CommentSectionProps): React.ReactElement {
  return (
    <div className="flex flex-col h-full">
      {/* 에러 메시지 */}
      {error && <ErrorMessage message={error} />}

      {/* 댓글 목록 */}
      <div className="flex-grow mt-4 overflow-y-auto">
        {loading ? (
          <LoadingSpinner />
        ) : (
          <CommentList
            comments={comments}
            deleteCommentItem={deleteCommentItem}
            editCommentItem={editCommentItem}
            hoveredCommentId={hoveredCommentId}
            setHoveredCommentId={setHoveredCommentId}
          />
        )}
      </div>

      {/* 댓글 추가 입력 */}
      <div className="mt-4">
        <CommentForm onAdd={addComment} disabled={loading} />
      </div>
    </div>
  );
}

export default CommentSection;
