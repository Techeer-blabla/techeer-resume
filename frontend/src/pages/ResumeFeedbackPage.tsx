// src/pages/ResumeFeedbackPage.tsx
import React, { useState } from "react";
import Layout from "../components/Layout/Layout";
import MainContainer from "../components/resumeoverview/MainContainer";
import ResumeOverview from "../components/resumeoverview/ResumeOverview";
import { v4 as uuidv4 } from "uuid";
import CommentSection from "../components/comment/CommentSection.tsx";

export type FeedbackPoint = {
  id: string;
  type: "feedback";
  pageNumber: number;
  x: number;
  y: number;
  comment: string;
};

export type Comment = {
  id: string;
  type: "comment";
  text: string;
};

export type CommentItem = FeedbackPoint | Comment;

function ResumeFeedbackPage(): React.ReactElement {
  const [comments, setComments] = useState<CommentItem[]>([]);
  const [hoveredCommentId, setHoveredCommentId] = useState<string | null>(null);

  // 일반 댓글 추가
  const addComment = (text: string) => {
    const newComment: Comment = { id: uuidv4(), type: "comment", text };
    setComments((prevComments) => [...prevComments, newComment]);
  };

  // 피드백 점 추가
  const addFeedbackPoint = (point: Omit<FeedbackPoint, "id" | "type">) => {
    const newPoint: FeedbackPoint = {
      id: uuidv4(),
      type: "feedback",
      ...point,
    };
    setComments((prevComments) => [...prevComments, newPoint]);
  };

  // 댓글 및 피드백 점 삭제
  const deleteCommentItem = (id: string) => {
    setComments((prevComments) =>
      prevComments.filter((item) => item.id !== id)
    );
  };

  // 댓글 및 피드백 점 수정
  const editCommentItem = (updatedItem: CommentItem) => {
    setComments((prevComments) =>
      prevComments.map((item) =>
        item.id === updatedItem.id ? updatedItem : item
      )
    );
  };

  return (
    <div className="flex flex-col flex-grow pt-16">
      <Layout
        sidebar={
          <div className="flex flex-col h-full bg-white p-2">
            {/* Resume Overview */}
            <ResumeOverview />

            {/* Comment Section */}
            <div className="flex-grow overflow-y-auto mt-2">
              <CommentSection
                comments={comments}
                addComment={addComment}
                addFeedbackPoint={addFeedbackPoint}
                deleteCommentItem={deleteCommentItem}
                editCommentItem={editCommentItem}
                hoveredCommentId={hoveredCommentId}
                setHoveredCommentId={setHoveredCommentId}
              />
            </div>
          </div>
        }
      >
        {/* Main Content */}
        <MainContainer
          comments={comments.filter((item) => item.type === "feedback")}
          addFeedbackPoint={addFeedbackPoint}
          deleteCommentItem={deleteCommentItem}
          editCommentItem={editCommentItem}
          hoveredCommentId={hoveredCommentId}
          setHoveredCommentId={setHoveredCommentId}
        />
      </Layout>
    </div>
  );
}

export default ResumeFeedbackPage;
