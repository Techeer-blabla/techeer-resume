// store/useFilterStore.ts
import { create } from "zustand";

interface FilterState {
  positions: string[];
  min_career: number;
  max_career: number;
  tech_stack_names: string[]; // 추가하여 상태에서 관리
  setCareerRange: (min: number, max: number) => void;
  setPositions: (positions: string[]) => void;
  setTechStackNames: (techStack: string[]) => void;
}

const useFilterStore = create<FilterState>((set) => ({
  positions: [],
  min_career: 0,
  max_career: 10,
  tech_stack_names: [],

  setCareerRange: (min, max) => set({ min_career: min, max_career: max }),
  setPositions: (positions) => set({ positions }),
  setTechStackNames: (techStack) => set({ tech_stack_names: techStack }),
}));

export default useFilterStore;
