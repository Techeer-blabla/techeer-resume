// src/mockApi.ts
export interface CommentType {
  id: number;
  text: string;
  timestamp: Date;
  modified?: boolean;
}

let comments: CommentType[] = [
  {
    id: 1,
    text: "프론트엔드 개발자인데도 백엔드 경험이 있네요 👍",
    timestamp: new Date(Date.now() - 86400000), // 1일 전
    modified: true,
  },
  // 초기 댓글을 추가할 수 있습니다.
];

/**
 * 댓글 목록을 가져오는 Mock API 함수
 */
export const getComments = (): Promise<CommentType[]> => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve([...comments]); // 불변성을 위해 복사본 반환
    }, 500); // 500ms 지연
  });
};

/**
 * 새로운 댓글을 추가하는 Mock API 함수
 * @param text - 댓글 내용
 */
export const addComment = (text: string): Promise<CommentType> => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const newComment: CommentType = {
        id: Date.now(),
        text,
        timestamp: new Date(),
        modified: false,
      };
      comments = [newComment, ...comments];
      resolve(newComment);
    }, 500);
  });
};

/**
 * 댓글을 삭제하는 Mock API 함수
 * @param id - 삭제할 댓글의 ID
 */
export const deleteComment = (id: number): Promise<void> => {
  return new Promise((resolve) => {
    setTimeout(() => {
      comments = comments.filter((comment) => comment.id !== id);
      resolve();
    }, 500);
  });
};

/**
 * 댓글을 수정하는 Mock API 함수
 * @param id - 수정할 댓글의 ID
 * @param newText - 새로운 댓글 내용
 */
export const editComment = (
  id: number,
  newText: string
): Promise<CommentType> => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const commentIndex = comments.findIndex((comment) => comment.id === id);
      if (commentIndex === -1) {
        reject(new Error("댓글을 찾을 수 없습니다."));
        return;
      }
      comments[commentIndex] = {
        ...comments[commentIndex],
        text: newText,
        modified: true,
      };
      resolve(comments[commentIndex]);
    }, 500);
  });
};
