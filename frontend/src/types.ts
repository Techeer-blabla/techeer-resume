// src/types/index.ts

export interface BaseCommentItem {
  id: string;
  type: "comment" | "feedback";
  text?: string; // 일반 댓글의 경우
  comment?: string; // 피드백의 경우
  timestamp: Date;
  modified?: boolean;
}

export interface FeedbackPoint extends BaseCommentItem {
  type: "feedback";
  pageNumber: number;
  x: number;
  y: number;
}

export interface Comment extends BaseCommentItem {
  type: "comment";
  // 필요한 추가 필드가 있으면 추가
}

export interface ResumeData {
  resume_id: number;
  user_name: string;
  position: string;
  career: number;
  tech_stack: string[];
  file_url: string;
  feedbacks: CommentItem[]; // 피드백 및 댓글을 포함
}

export type CommentItem = Comment | FeedbackPoint;
