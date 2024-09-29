// src/components/resumeoverview/ResumePage.tsx
import React, { useRef, useState } from "react";
import FeedbackPopup from "./FeedbackPopup";
import { CommentItem, FeedbackPoint } from "../../pages/ResumeFeedbackPage.tsx";

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
  const [selectedPoint, setSelectedPoint] = useState<FeedbackPoint | null>(
    null
  );
  const [newComment, setNewComment] = useState<string>("");

  const handleClick = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (!pageRef.current) return;

    const rect = pageRef.current.getBoundingClientRect();
    const x = ((e.clientX - rect.left) / rect.width) * 100; // 백분율
    const y = ((e.clientY - rect.top) / rect.height) * 100; // 백분율

    const comment = prompt("Enter your feedback:");
    if (comment) {
      const newPoint: Omit<FeedbackPoint, "id" | "type"> = {
        pageNumber,
        x,
        y,
        comment,
      };
      addFeedbackPoint(newPoint);
    }
  };

  const handleMarkerClick = (point: FeedbackPoint) => {
    setSelectedPoint(point);
    setNewComment(point.comment);
  };

  const handlePopupSubmit = () => {
    if (selectedPoint && newComment.trim() !== "") {
      const updatedPoint: FeedbackPoint = {
        ...selectedPoint,
        comment: newComment,
      };
      editCommentItem(updatedPoint);
      setSelectedPoint(null);
      setNewComment("");
    }
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
      </div>

      {/* 피드백 팝업 */}
      {selectedPoint && (
        <FeedbackPopup
          point={selectedPoint}
          onClose={() => setSelectedPoint(null)}
          newComment={newComment}
          setNewComment={setNewComment}
          onSubmit={handlePopupSubmit}
        />
      )}
    </div>
  );
}

export default ResumePage;
