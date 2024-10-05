import { useState, useEffect } from "react";
import AddComment from "../components/comment/AddComment.tsx";
import CommentList from "../components/comment/CommentList.tsx";
import MainContainer from "../components/resumeoverview/MainContainer.tsx";
import {
  CommentType,
  getComments,
  addComment,
  deleteComment as apiDeleteComment,
  editComment as apiEditComment,
} from "../mockApi";
import ResumeOverview from "../components/resumeoverview/ResumeOverview.tsx";
import Navbar from "../components/Navbar.tsx";

function ResumeFeedbackPage() {
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

  function addNewComment(newCommentText: string) {
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
  }

  function deleteCommentHandler(id: number) {
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
  }

  function editCommentHandler(id: number, newText: string) {
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
  }

  // 에러 메시지 자동 사라지게 설정
  useEffect(() => {
    if (error) {
      const timer = setTimeout(() => setError(""), 3000); // Auto clear error after 3 seconds
      return () => clearTimeout(timer);
    }
  }, [error]);

  return (
    <div className="pt-5">
      <Navbar />
      <div className="flex flex-row w-full h-screen bg-gray-100 mt-3">
        {/* Left Column: MainContainer */}
        <div className="w-2/3 h-full">
          <MainContainer />
        </div>

        {/* Right Column: ResumeOverview and Comments */}
        <div className="w-1/3 h-full flex flex-col p-4">
          {/* Resume Overview */}
          <ResumeOverview />

          {/* Comments Section */}
          <div className="flex-grow mt-4 overflow-y-auto">
            {loading ? (
              <div className="flex justify-center items-center">
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
                Loading...
              </div>
            ) : (
              <CommentList
                comments={comments}
                onDelete={deleteCommentHandler}
                onEdit={editCommentHandler}
              />
            )}
          </div>

          {/* Add Comment Input */}
          <AddComment onAdd={addNewComment} disabled={loading} />
        </div>
      </div>
    </div>
  );
}

export default ResumeFeedbackPage;
