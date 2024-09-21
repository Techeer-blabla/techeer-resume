import React, { useState, ChangeEvent, FormEvent } from "react";

interface AddCommentProps {
  onAdd: (comment: string) => void;
  disabled?: boolean;
}

function AddComment({ onAdd, disabled = false }: AddCommentProps) {
  const [comment, setComment] = useState<string>("");

  const handleChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setComment(e.target.value);
  };

  const handleSubmit = (
    e: FormEvent<HTMLFormElement> | React.MouseEvent<HTMLButtonElement>
  ) => {
    e.preventDefault();
    if (comment.trim() === "") return;
    onAdd(comment);
    setComment("");
  };

  return (
    <div className="w-full max-w-[445px] flex flex-col justify-start items-start mt-4">
      <form onSubmit={handleSubmit} className="w-full">
        <div className="w-full h-[90px] relative">
          {/* Rounded Border */}
          <div className="w-full h-full absolute rounded-[14px] border-2 border-[#abbdec]"></div>

          {/* Textarea Field */}
          <textarea
            placeholder="댓글 추가.."
            className="w-full h-full px-4 pt-2 pb-1 text-[#333333] text-sm font-normal font-Pretendard bg-white rounded-[14px] focus:outline-none relative z-10 text-left placeholder-[#333333] resize-none"
            value={comment}
            onChange={handleChange}
            disabled={disabled}
          />
        </div>

        {/* Submit Button */}
        <button
          type="submit"
          className={`mt-2 px-4 py-1 bg-[#abbdec] text-white text-sm font-normal font-Pretendard rounded ${
            disabled ? "opacity-50 cursor-not-allowed" : "hover:bg-blue-600"
          }`}
          disabled={disabled}
        >
          댓글 추가
        </button>
      </form>
    </div>
  );
}

export default AddComment;
