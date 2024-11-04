import { create } from "zustand";

interface ResumeStore {
  resumeId: number;
  setResumeId: (id: number) => void;
}

const useResumeStore = create<ResumeStore>((set) => ({
  resumeId: 0,
  setResumeId: (id) => set({ resumeId: id }),
}));

export default useResumeStore;
