// src/components/Comments/CommentList.tsx

import { CommentItem } from "../../pages/ResumeFeedbackPage.tsx";

type CommentListProps = {
  comments: CommentItem[];
  deleteCommentItem: (id: string) => void;
  editCommentItem: (item: CommentItem) => void;
  hoveredCommentId: string | null;
  setHoveredCommentId: (id: string | null) => void;
};

function CommentList({
  comments,
  deleteCommentItem,
  editCommentItem,
  hoveredCommentId,
  setHoveredCommentId,
}: CommentListProps) {
  return (
    <ul>
      {comments.map((item) => (
        <li
          key={item.id}
          className={`mb-2 p-2 border rounded flex justify-between items-center ${
            item.type === "feedback" && item.id === hoveredCommentId
              ? "bg-blue-100"
              : ""
          }`}
          onMouseEnter={() => {
            if (item.type === "feedback") setHoveredCommentId(item.id);
          }}
          onMouseLeave={() => {
            if (item.type === "feedback") setHoveredCommentId(null);
          }}
        >
          <div>
            {item.type === "comment" ? (
              <p>{item.text}</p>
            ) : (
              <div>
                <p>
                  <strong>피드백:</strong> {item.comment}
                </p>
                <p className="text-sm text-gray-500">
                  Page {item.pageNumber}, 좌표: ({item.x.toFixed(2)}%,{" "}
                  {item.y.toFixed(2)}%)
                </p>
              </div>
            )}
          </div>
          <div>
            <button
              className="mr-2 px-2 py-1 bg-yellow-500 text-white rounded"
              onClick={() => editCommentItem(item)}
            >
              Edit
            </button>
            <button
              className="px-2 py-1 bg-red-500 text-white rounded"
              onClick={() => deleteCommentItem(item.id)}
            >
              Delete
            </button>
          </div>
        </li>
      ))}
    </ul>
  );
}

export default CommentList;
