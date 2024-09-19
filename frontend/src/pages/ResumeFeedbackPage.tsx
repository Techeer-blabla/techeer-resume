// src/pages/ResumeFeedbackPage.tsx
import React, { useState, useEffect } from "react";
import AddComment from "../components/AddComment";
import CommentList from "../components/CommentList";
import {
  CommentType,
  getComments,
  addComment,
  deleteComment as apiDeleteComment,
  editComment as apiEditComment,
} from "../mockApi";

const ResumeFeedbackPage: React.FC = () => {
  const [comments, setComments] = useState<CommentType[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");

  // 초기 댓글 불러오기
  useEffect(() => {
    setLoading(true);
    getComments()
      .then((data) => {
        setComments(data);
        setLoading(false);
      })
      .catch((err) => {
        setError("댓글을 불러오는 데 실패했습니다.");
        setLoading(false);
        console.error(err);
      });
  }, []);

  const addNewComment = (newCommentText: string) => {
    setLoading(true);
    addComment(newCommentText)
      .then((newComment) => {
        setComments([newComment, ...comments]);
        setLoading(false);
      })
      .catch((err) => {
        setError("댓글을 추가하는 데 실패했습니다.");
        setLoading(false);
        console.error(err);
      });
  };

  const deleteCommentHandler = (id: number) => {
    setLoading(true);
    apiDeleteComment(id)
      .then(() => {
        setComments(comments.filter((comment) => comment.id !== id));
        setLoading(false);
      })
      .catch((err) => {
        setError("댓글을 삭제하는 데 실패했습니다.");
        setLoading(false);
        console.error(err);
      });
  };

  const editCommentHandler = (id: number, newText: string) => {
    setLoading(true);
    apiEditComment(id, newText)
      .then((updatedComment) => {
        setComments(
          comments.map((comment) =>
            comment.id === id ? updatedComment : comment
          )
        );
        setLoading(false);
      })
      .catch((err) => {
        setError("댓글을 수정하는 데 실패했습니다.");
        setLoading(false);
        console.error(err);
      });
  };

  // 에러 메시지 자동 사라지게 설정
  useEffect(() => {
    if (error) {
      const timer = setTimeout(() => {
        setError("");
      }, 3000); // 3초 후 에러 메시지 사라짐
      return () => clearTimeout(timer);
    }
  }, [error]);

  return (
    <div className="flex flex-col items-center p-4 bg-gray-100 min-h-screen">
      <h1 className="text-2xl font-bold mb-4">이력서 피드백</h1>

      {/* 에러 메시지 표시 */}
      {error && (
        <div className="text-red-500 mb-4 flex items-center">
          {error}
          <button
            onClick={() => setError("")}
            className="ml-2 underline"
            aria-label="에러 메시지 닫기"
          >
            닫기
          </button>
        </div>
      )}

      {/* AddComment 컴포넌트 */}
      <AddComment onAdd={addNewComment} disabled={loading} />

      {/* 댓글 목록 */}
      {loading ? (
        <div className="mt-4 flex items-center">
          <svg
            className="animate-spin h-5 w-5 mr-3 text-blue-500"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
          >
            <circle
              className="opacity-25"
              cx="12"
              cy="12"
              r="10"
              stroke="currentColor"
              strokeWidth="4"
            ></circle>
            <path
              className="opacity-75"
              fill="currentColor"
              d="M4 12a8 8 0 018-8v8H4z"
            ></path>
          </svg>
          로딩 중...
        </div>
      ) : (
        <CommentList
          comments={comments}
          onDelete={deleteCommentHandler}
          onEdit={editCommentHandler}
        />
      )}
    </div>
  );
};

export default ResumeFeedbackPage;
