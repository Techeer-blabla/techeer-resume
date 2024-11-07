import { useState, useEffect, useRef } from "react";
import Slider from "./Slider";

interface CareerModalProps {
  isOpen: boolean;
  onClose: () => void;
  onApply: (minCareer: number, maxCareer: number) => void;
}

const CareerModal = ({ isOpen, onClose, onApply }: CareerModalProps) => {
  const [min_career, set_min_career] = useState(0); // 최소 경력
  const [max_career, set_max_career] = useState(10); // 최대 경력
  const dropdownRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(event.target as Node)
      ) {
        onClose();
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [onClose]);

  const handleSliderChange = (newMin: number, newMax: number) => {
    set_min_career(newMin);
    set_max_career(newMax);
  };

  const applyFilter = () => {
    onApply(min_career, max_career);
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div
      className="w-125 p-6 bg-white rounded-lg border shadow-xl relative"
      ref={dropdownRef}
    >
      <button
        onClick={onClose}
        className="absolute top-4 right-4 text-black text-2xl"
      >
        &times;
      </button>
      <div className="text-black text-2xl font-semibold mb-6">경력</div>
      <div className="p-3 my-3 ml-3">
        <Slider
          minCareer={min_career}
          maxCareer={max_career}
          onChange={handleSliderChange}
        />
      </div>
      <div className="flex justify-between">
        <button className="w-24 h-10 border border-gray-300 rounded-lg flex items-center justify-center text-black text-base font-medium">
          초기화
        </button>
        <button
          onClick={applyFilter}
          className="w-24 h-10 bg-blue-500 rounded-lg flex items-center justify-center text-white text-base font-medium"
        >
          적용
        </button>
      </div>
    </div>
  );
};

export default CareerModal;
