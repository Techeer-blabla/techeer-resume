import { jsonAxios } from "./axios.config.ts";

// 북마크 추가
export const postBookmark = async (userId: number, resumeId: number) => {
  try {
    const requestData = {
      user_id: userId,
      resume_id: resumeId,
    };

    const response = await jsonAxios.post(`/bookmarks/`, requestData);
    return response.data;
  } catch (error) {
    console.error("북마크 추가 오류:", error);
    throw error;
  }
};

// 단일 북마크 조회
export const getBookmarkById = async (bookmarkId: number) => {
  try {
    const response = await jsonAxios.get(`/bookmarks/${bookmarkId}`);
    return response.data;
  } catch (error) {
    console.error("단일 북마크 조회 오류:", error);
    throw error;
  }
};

// 사용자의 모든 북마크 조회
export const getAllBookmarksByUserId = async (userId: number) => {
  try {
    const response = await jsonAxios.get(`/bookmarks/users/${userId}`);
    return response.data;
  } catch (error) {
    console.error("사용자의 모든 북마크 조회 오류:", error);
    throw error;
  }
};

// 북마크 삭제
export const deleteBookmarkById = async (bookmarkId: number) => {
  try {
    const response = await jsonAxios.delete(`/bookmarks/${bookmarkId}`);
    console.log("북마크 삭제 성공:", response.data);
    return response.data;
  } catch (error) {
    console.error("북마크 삭제 오류:", error);
    throw error;
  }
};
