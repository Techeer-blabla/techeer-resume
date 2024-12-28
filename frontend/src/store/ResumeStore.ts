import { create } from "zustand";

interface ResumeStore {
  resumeId: number;
  userId: number; // userId를 추가하여 전역 상태로 관리
  ResumeUrl: string;
  setResumeId: (id: number) => void;
  setUserId: (id: number) => void;
  setResumeUrl: (url: string) => void;
}

const useResumeStore = create<ResumeStore>((set) => ({
  resumeId: 0,
  userId: 0, // 초기값으로 0을 설정
  ResumeUrl: "",
  setResumeId: (id) => set({ resumeId: id }),
  setUserId: (id) => set({ userId: id }),
  setResumeUrl: (url) => set({ ResumeUrl: url }),
}));

export default useResumeStore;
