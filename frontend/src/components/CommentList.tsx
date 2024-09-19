// src/components/CommentList.tsx
import React from "react";
import Comment from "./Comment";
import { CommentType } from "../mockApi";

interface CommentListProps {
  comments: CommentType[];
  onDelete: (id: number) => void;
  onEdit: (id: number, newText: string) => void;
}

const CommentList: React.FC<CommentListProps> = ({
  comments,
  onDelete,
  onEdit,
}) => {
  return (
    <div className="w-full max-w-[450px] mt-4">
      {comments.map((comment) => (
        <Comment
          key={comment.id}
          id={comment.id}
          text={comment.text}
          timestamp={comment.timestamp}
          modified={comment.modified}
          onDelete={onDelete}
          onEdit={onEdit}
        />
      ))}
    </div>
  );
};

export default CommentList;
