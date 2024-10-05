// src/components/resumeoverview/ResumePage.tsx
import React, { useRef, useState } from "react";
import { FeedbackPoint, CommentItem } from "../../types";
import CommentForm from "../comment/CommentForm.tsx";

type ResumePageProps = {
  pageNumber: number;
  feedbackPoints: FeedbackPoint[];
  addFeedbackPoint: (point: Omit<FeedbackPoint, "id" | "type">) => void;
  deleteCommentItem: (id: string) => void;
  editCommentItem: (item: CommentItem) => void;
  hoveredCommentId: string | null;
  setHoveredCommentId: (id: string | null) => void;
};

function ResumePage({
  pageNumber,
  feedbackPoints,
  addFeedbackPoint,
  editCommentItem,
  hoveredCommentId,
  setHoveredCommentId,
}: ResumePageProps) {
  const pageRef = useRef<HTMLDivElement>(null);
  const [addingFeedback, setAddingFeedback] = useState<{
    x: number;
    y: number;
    pageNumber: number;
  } | null>(null);
  const [editingFeedback, setEditingFeedback] = useState<FeedbackPoint | null>(
    null
  );

  const handleClick = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (!pageRef.current) return;

    const rect = pageRef.current.getBoundingClientRect();
    const x = ((e.clientX - rect.left) / rect.width) * 100; // 백분율
    const y = ((e.clientY - rect.top) / rect.height) * 100; // 백분율

    setAddingFeedback({ x, y, pageNumber });
  };

  const handleMarkerClick = (point: FeedbackPoint) => {
    setEditingFeedback(point);
  };

  const handleAddSubmit = (comment: string) => {
    if (addingFeedback) {
      addFeedbackPoint({
        pageNumber: addingFeedback.pageNumber,
        x: addingFeedback.x,
        y: addingFeedback.y,
        comment,
        timestamp: new Date(),
      });
      setAddingFeedback(null);
    }
  };

  const handleEditSubmit = (comment: string) => {
    if (editingFeedback) {
      const updatedPoint: FeedbackPoint = { ...editingFeedback, comment };
      editCommentItem(updatedPoint);
      setEditingFeedback(null);
    }
  };

  const handleCancel = () => {
    setAddingFeedback(null);
    setEditingFeedback(null);
  };

  return (
    <div className="relative mb-8">
      <div
        ref={pageRef}
        className="w-full h-[903px] mx-auto mt-4 bg-[#bbb8b8] flex justify-center items-center relative cursor-pointer"
        onClick={handleClick}
      >
        {/* Placeholder for PDF Image */}
        <p className="text-white">Resume PDF Page {pageNumber}</p>

        {/* 피드백 마커 렌더링 */}
        {feedbackPoints.map((point) => (
          <div
            key={point.id}
            className={`absolute w-4 h-4 rounded-full transform -translate-x-1/2 -translate-y-1/2 cursor-pointer ${
              point.id === hoveredCommentId ? "bg-sky-500" : "bg-red-500"
            }`}
            style={{
              left: `${point.x}%`,
              top: `${point.y}%`,
            }}
            onMouseEnter={() => setHoveredCommentId(point.id)}
            onMouseLeave={() => setHoveredCommentId(null)}
            onClick={(e) => {
              e.stopPropagation(); // 페이지 클릭 방지
              handleMarkerClick(point);
            }}
          ></div>
        ))}

        {/* 피드백 추가 폼 */}
        {addingFeedback && (
          <CommentForm
            position={{ x: addingFeedback.x, y: addingFeedback.y }}
            onSubmit={handleAddSubmit}
            onCancel={handleCancel}
          />
        )}

        {/* 피드백 수정 폼 */}
        {editingFeedback && (
          <CommentForm
            position={{ x: editingFeedback.x, y: editingFeedback.y }}
            initialComment={editingFeedback.comment}
            onSubmit={handleEditSubmit}
            onCancel={handleCancel}
          />
        )}
      </div>
    </div>
  );
}

export default ResumePage;
