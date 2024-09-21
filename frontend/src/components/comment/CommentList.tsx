import Comment from "./Comment.tsx";
import { CommentType } from "../../mockApi.ts";

interface CommentListProps {
  comments: CommentType[];
  onDelete: (id: number) => void;
  onEdit: (id: number, newText: string) => void;
}

function CommentList({ comments, onDelete, onEdit }: CommentListProps) {
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
}

export default CommentList;
