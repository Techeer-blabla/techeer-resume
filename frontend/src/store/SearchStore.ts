// store/SearchStore.js
import { create } from "zustand";

const useSearchStore = create((set) => ({
  searchResults: [],
  setSearchResults: (results: string[]) => set({ searchResults: results }),
}));

export default useSearchStore;
