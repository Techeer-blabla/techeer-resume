import React, { useState, ChangeEvent, FormEvent } from "react";

interface CommentFormProps {
  onAdd: (comment: string) => void;
  disabled?: boolean;
}

function CommentForm({ onAdd, disabled = false }: CommentFormProps) {
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
    <div className="w-full flex flex-col justify-start items-start mt-4">
      <form
        onSubmit={handleSubmit}
        className="w-full border-2 border-primary-lightBlue rounded-[14px]"
      >
        <div className="w-full h-[90px] relative">
          <div className="w-full h-full absolute rounded-[14px]"></div>
          <textarea
            placeholder="댓글 추가.."
            className="w-full h-full px-4 pt-2 pb-1 text-[#333333] text-sm font-normal font-Pretendard bg-white rounded-[14px] focus:outline-none relative z-10 text-left placeholder-[#333333] resize-none"
            value={comment}
            onChange={handleChange}
            disabled={disabled}
          />
        </div>
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

export default CommentForm;
