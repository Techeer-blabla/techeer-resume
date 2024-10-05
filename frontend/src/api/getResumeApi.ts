import { axiosInstance } from "./axios.config.ts";
import { ResumeData } from "../types.ts";

/**
 * 이력서 데이터 조회 API
 * @param resumeId - 이력서 ID
 * @returns Resume 데이터
 */
export const getResumeApi = async (resumeId: number): Promise<ResumeData> => {
  const response = await axiosInstance.get(`/resumes/${resumeId}`);
  return response.data;
};

/**
 * 피드백 삭제 API
 * @param resumeId - 이력서 ID
 * @param feedbackId - 피드백 ID
 * @returns DeleteFeedbackResponse 데이터
 */
export const deleteFeedbackApi = async (
  resumeId: number,
  feedbackId: number
) => {
  const response = await axiosInstance.delete(
    `/resumes/${resumeId}/feedbacks/${feedbackId}`
  );
  return response.data;
};
