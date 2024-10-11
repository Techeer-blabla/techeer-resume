import { create } from "zustand";

interface SearchStore {
  searchResults: string[];
  searchName: string;
  setSearchResults: (results: string[]) => void;
  setSearchName: (name: string) => void;
}

const useSearchStore = create<SearchStore>((set) => ({
  searchResults: [],
  searchName: "",
  setSearchResults: (results) => set({ searchResults: results }),
  setSearchName: (name) => set({ searchName: name }),
}));

export default useSearchStore;
