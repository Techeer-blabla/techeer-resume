import React from "react";
import { CommentType } from "../../mockApi";
import CommentCard from "./CommentCard";

interface CommentListProps {
  comments: CommentType[];
  onDelete: (id: number) => void;
  onEdit: (id: number, newText: string) => void;
}

function CommentList({
  comments,
  onDelete,
  onEdit,
}: CommentListProps): React.ReactElement {
  return (
    <ul className="space-y-2">
      {comments.map((comment) => (
        <li key={comment.id}>
          <CommentCard
            id={comment.id}
            text={comment.text}
            timestamp={comment.timestamp}
            modified={comment.modified}
            onDelete={onDelete}
            onEdit={onEdit}
          />
        </li>
      ))}
    </ul>
  );
}

export default CommentList;
