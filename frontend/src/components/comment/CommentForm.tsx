// src/components/Comments/CommentForm.tsx
import React, { useState, ChangeEvent, FormEvent } from "react";

interface Position {
  x: number; // 백분율
  y: number; // 백분율
}

interface CommentFormProps {
  onAdd?: (comment: string) => void; // 사이드바용
  onSubmit?: (comment: string) => void; // 메인 영역용
  onCancel?: () => void; // 메인 영역용
  position?: Position; // 메인 영역용
  initialComment?: string; // 메인 영역용 (수정 시)
  disabled?: boolean;
}

function CommentForm({
  onAdd,
  onSubmit,
  onCancel,
  position,
  initialComment = "",
  disabled = false,
}: CommentFormProps) {
  const [comment, setComment] = useState<string>(initialComment);

  const handleChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setComment(e.target.value);
  };

  const handleSubmit = (
    e: FormEvent<HTMLFormElement> | React.MouseEvent<HTMLButtonElement>
  ) => {
    e.preventDefault();
    if (comment.trim() === "") return;

    if (onAdd) {
      onAdd(comment);
    }

    if (onSubmit) {
      onSubmit(comment);
    }

    setComment("");
  };

  // 폼이 메인 영역에 위치할 경우 스타일 적용
  const formStyles: React.CSSProperties = position
    ? {
        position: "absolute",
        left: `${position.x}%`,
        top: `${position.y}%`,
        transform: "translate(0%, -100%)", // 왼쪽 아래쪽으로 이동
        width: "200px",
        zIndex: 30,
      }
    : {};

  return (
    <div
      className="bg-white border rounded shadow-lg p-2"
      style={formStyles}
      onClick={(e) => e.stopPropagation()} // 이벤트 전파 중단
    >
      <form onSubmit={handleSubmit} className="flex flex-col">
        <textarea
          placeholder={
            onAdd ? "댓글을 입력하세요..." : "피드백을 입력하세요..."
          }
          className="w-full h-24 p-2 border rounded resize-none"
          value={comment}
          onChange={handleChange}
          disabled={disabled}
        />
        <div className="flex justify-end mt-2 space-x-2">
          {/* 메인 영역용 취소 버튼 */}
          {onCancel && (
            <button
              type="button"
              className="px-3 py-1 bg-gray-300 text-gray-700 rounded"
              onClick={onCancel}
              disabled={disabled}
            >
              취소
            </button>
          )}
          <button
            type="submit"
            className={`px-3 py-1 bg-blue-500 text-white rounded hover:bg-blue-600 ${
              disabled ? "opacity-50 cursor-not-allowed" : ""
            }`}
            disabled={disabled}
          >
            {onSubmit ? (initialComment ? "수정" : "추가") : "댓글 추가"}
          </button>
        </div>
      </form>
    </div>
  );
}

export default CommentForm;
