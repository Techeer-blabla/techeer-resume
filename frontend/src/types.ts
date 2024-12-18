export type FeedbackPoint = {
  id: number;
  content: string;
  xCoordinate: number;
  yCoordinate: number;
  pageNumber: number | 1;
  createdAt: string;
  updatedAt: string;
  deletedAt?: string | null;
};

export type ResumeData = {
  feedbacks: never[];
  bookmarks?: BookmarkData[];
  resumeId: number;
  userName: string;
  position: string;
  career: number;
  techStack: string[];
  fileUrl: string;
  feedbackResponses: FeedbackPoint[];
};

export type BookmarkData = {
  bookmarkId: number; // 북마크의 고유 ID
  userId: number; // 북마크를 생성한 사용자 ID
};
export type AddFeedbackPoint = {
  content: string;
  xCoordinate: number;
  yCoordinate: number;
  pageNumber: number;
};
