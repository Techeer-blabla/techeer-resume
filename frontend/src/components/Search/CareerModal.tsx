import { useEffect, useRef } from "react";
import Slider from "./Slider"; // 'Silder' 오타 수정

interface PositionModalProps {
  isOpen: boolean;
  onClose: () => void;
}

const PositionModal = ({ isOpen, onClose }: PositionModalProps) => {
  const dropdownRef = useRef<HTMLDivElement>(null);

  // 모달 외부 클릭 시 모달 닫기
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

      {/* 경력 타이틀 */}
      <div className="text-black text-2xl font-semibold mb-6">경력</div>
      <div className="p-3 my-3 ml-3">
        <Slider /> {/* 'Silder'가 아닌 'Slider'로 오타 수정 */}
      </div>

      {/* 하단 버튼 */}
      <div className="flex justify-between">
        <button className="w-24 h-10 border border-gray-300 rounded-lg flex items-center justify-center text-black text-base font-medium">
          초기화
        </button>
        <button
          onClick={onClose}
          className="w-24 h-10 bg-blue-500 rounded-lg flex items-center justify-center text-white text-base font-medium"
        >
          적용
        </button>
      </div>
    </div>
  );
};

export default PositionModal;
