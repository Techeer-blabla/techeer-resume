import { jsonAxios } from "./axios.config.ts";

// 북마크 추가
export const postBookmark = async (resumeId: number) => {
  try {
    const requestData = {
      resume_id: resumeId,
    };

    const response = await jsonAxios.post(`/bookmarks/`, requestData);
    // 응답에서 bookmark_id를 받아옵니다
    const bookmarkId = response.data.bookmark_id; // 예시로 bookmark_id가 응답에 포함된다고 가정
    return bookmarkId;
  } catch (error) {
    console.error("북마크 추가 오류:", error);
    throw error;
  }
};

// 단일 북마크 조회
export const getBookmarkById = async (userId: number) => {
  try {
    const response = await jsonAxios.get(`/bookmarks/users/${userId}`);
    return response.data;
  } catch (error) {
    console.error("단일 북마크 조회 오류:", error);
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
