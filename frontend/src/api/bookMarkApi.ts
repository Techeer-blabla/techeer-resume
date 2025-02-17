import { jsonAxios } from "./axios.config.ts";

// 북마크 추가
export const postBookmark = async (resumeId: number) => {
  try {
    console.log("아이디", resumeId);
    const response = await jsonAxios.post(`/bookmarks/${resumeId}`);
    // 응답에서 bookmark_id를 받아옵니다
    const bookmarkId = response.data.result.bookmark_id;
    return bookmarkId;
  } catch (error) {
    console.error("북마크 추가 오류:", error);
    throw error;
  }
};

// 북마크 조회
export const getBookmarkById = async (userId: number) => {
  try {
    const response = await jsonAxios.get(`/bookmarks/users/${userId}`);
    return response.data;
  } catch (error) {
    console.error("북마크 조회 오류:", error);
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
