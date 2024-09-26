import { create } from "zustand";

const CategoryStore = create((set) => ({
  positionCategory: [],
  careerCategory: [],
  setPositionCategory: (position: string[]) =>
    set({ positionCategory: position }),
  setCareerCategory: (career: string) => set({ careerCategory: career }),
  deleteCareer: () => set({ careerCategory: [] }),
  deletePosition: () => set({ positionCategory: [] }),
}));

export default CategoryStore;
