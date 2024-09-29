import { useState, useEffect } from "react";
import {
  CommentType,
  getComments,
  addComment,
  deleteComment as apiDeleteComment,
  editComment as apiEditComment,
} from "../mockApi";

function useComments() {
  const [comments, setComments] = useState<CommentType[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");

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
        setComments((prevComments) => [newComment, ...prevComments]);
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
        setComments((prevComments) =>
          prevComments.filter((comment) => comment.id !== id)
        );
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
        setComments((prevComments) =>
          prevComments.map((comment) =>
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
      const timer = setTimeout(() => setError(""), 3000); // 3초 후 에러 메시지 제거
      return () => clearTimeout(timer);
    }
  }, [error]);

  return {
    comments,
    loading,
    error,
    addNewComment,
    deleteCommentHandler,
    editCommentHandler,
  };
}

export default useComments;
