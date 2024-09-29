// src/components/Comments/CommentSection.tsx
import React from "react";
import AddComment from "./AddComment";
import CommentList from "./CommentList";
import useComments from "../../hooks/useComments";
import ErrorMessage from "../UI/ErrorMessage";
import LoadingSpinner from "../UI/LoadingSpinner";

function CommentSection(): React.ReactElement {
  const {
    comments,
    loading,
    error,
    addNewComment,
    deleteCommentHandler,
    editCommentHandler,
  } = useComments();

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
            onDelete={deleteCommentHandler}
            onEdit={editCommentHandler}
          />
        )}
      </div>

      {/* 댓글 추가 입력 */}
      <div className="mt-4">
        <AddComment onAdd={addNewComment} disabled={loading} />
      </div>
    </div>
  );
}

export default CommentSection;
