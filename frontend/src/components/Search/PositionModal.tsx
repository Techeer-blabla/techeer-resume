// components/Search/PositionModal.tsx
import { useEffect, useRef, useState } from "react";
import useFilterStore from "../../store/useFilterStore";

interface PositionModalProps {
  isOpen: boolean;
  onClose: () => void;
}

const PositionModal = ({ isOpen, onClose }: PositionModalProps) => {
  const { positions, setPositions } = useFilterStore();
  const [localSelectedPosition, setLocalSelectedPosition] = useState<
    string | null
  >(positions[0] || null);
  const dropdownRef = useRef<HTMLDivElement>(null);

  const applyPosition = () => {
    if (localSelectedPosition) {
      setPositions([localSelectedPosition]);
    } else {
      setPositions([]);
    }
    onClose();
  };

  // 사전 정의된 포지션 목록
  const availablePositions = [
    "Frontend",
    "Backend",
    "FullStack",
    "DevOps",
    "Designer",
    "AI",
    "Android",
    "IOS",
    "Data",
  ];

  // 포지션 선택 처리 (하나만 선택 가능)
  const selectPosition = (position: string) => {
    setLocalSelectedPosition(
      position === localSelectedPosition ? null : position
    ); // 같은 포지션 클릭 시 해제
  };

  // 모달 외부 클릭 시 닫기
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(event.target as Node)
      ) {
        onClose();
      }
    };

    if (isOpen) {
      document.addEventListener("mousedown", handleClickOutside);
    }

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [onClose, isOpen]);

  // 모달이 열려있을 때만 렌더링
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

      <div className="text-black text-2xl font-semibold mb-6">포지션</div>

      <div className="grid grid-cols-2 gap-4 mb-6">
        {availablePositions.map((position, index) => (
          <div
            key={index}
            onClick={() => selectPosition(position)}
            className={`flex items-center justify-between p-2 cursor-pointer rounded-lg hover:bg-gray-200 ${
              localSelectedPosition === position ? "bg-gray-100" : "bg-white"
            }`}
          >
            <span className="text-black text-base font-normal ml-2">
              {position}
            </span>
            <div
              className={`w-6 h-6 rounded-full border-[1.5px] flex items-center justify-center mr-2 ${
                localSelectedPosition === position
                  ? "border-blue-500 bg-blue-500"
                  : "border-gray-300"
              }`}
            >
              {localSelectedPosition === position && (
                <div className="w-3 h-3 bg-white rounded-full" />
              )}
            </div>
          </div>
        ))}
      </div>

      <div className="flex justify-between">
        <button
          onClick={() => setLocalSelectedPosition(null)} // 초기화 버튼
          className="text-black bg-white px-4 py-2 rounded-lg shadow-sm border border-gray-300"
        >
          초기화
        </button>
        <button
          onClick={applyPosition} // 적용 버튼
          className="text-white bg-blue-500 px-4 py-2 rounded-lg shadow-sm"
        >
          적용
        </button>
      </div>
    </div>
  );
};

export default PositionModal;
