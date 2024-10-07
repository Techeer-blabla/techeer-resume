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
  resumeId: number;
  userName: string;
  position: string;
  career: number;
  techStack: string[];
  fileUrl: string;
  feedbacks: FeedbackPoint[];
};

export type AddFeedbackPoint = {
  content: string;
  xCoordinate: number;
  yCoordinate: number;
  pageNumber: number;
};
