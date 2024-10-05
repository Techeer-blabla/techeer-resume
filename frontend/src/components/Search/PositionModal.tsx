import { useEffect, useRef, useState } from "react";
import CategoryStore from "../../store/CategoryStore";
import { useStore } from "zustand";

interface PositionModalProps {
  isOpen: boolean;
  onClose: () => void;
}

const positions = [
  "개발 전체",
  "소프트웨어 엔지니어",
  "웹 개발자",
  "서버 개발자",
  "DevOps",
  "프론트엔드 개발자",
  "안드로이드 개발자",
  "IOS 개발자",
  "임베디드 개발자",
  "데이터 엔지니어",
];

const PositionModal = ({ onClose }: PositionModalProps) => {
  const [selectedPositions, setSelectedPositions] = useState<string[]>([]);
  const dropdownRef = useRef<HTMLDivElement>(null);

  const { positionCategory, setPositionCategory } = useStore(CategoryStore);

  // 포지션 선택 시 상태 업데이트
  const togglePosition = (position: string) => {
    setSelectedPositions((prev) =>
      prev.includes(position)
        ? prev.filter((pos) => pos !== position)
        : [...prev, position]
    );
  };

  // 선택된 포지션을 zustand 스토어에 저장
  const applyPositions = () => {
    setPositionCategory(selectedPositions); // 스토어에 선택된 포지션 저장
    onClose();
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

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [onClose]);

  // 선택된 포지션을 동기화
  useEffect(() => {
    setSelectedPositions(positionCategory); // 스토어의 선택된 포지션을 로컬 상태에 반영
  }, [positionCategory]);

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

      {/* 포지션 타이틀 */}
      <div className="text-black text-2xl font-semibold mb-6">포지션</div>

      {/* 포지션 목록 */}
      <div className="grid grid-cols-2 gap-4 mb-6">
        {positions.map((position, index) => (
          <div
            key={index}
            onClick={() => togglePosition(position)}
            className={`flex items-center justify-between p-2 cursor-pointer rounded-lg hover:bg-gray-200 ${
              selectedPositions.includes(position) ? "bg-gray-100" : "bg-white"
            }`}
          >
            <span className="text-black text-base font-normal ml-2">
              {position}
            </span>
            <div
              className={`w-6 h-6 rounded-full border-[1.5px] flex items-center justify-center mr-2 ${
                selectedPositions.includes(position)
                  ? "border-blue-500 bg-blue-500"
                  : "border-gray-300"
              }`}
            >
              {selectedPositions.includes(position) && (
                <div className="w-3 h-3 bg-white rounded-full" />
              )}
            </div>
          </div>
        ))}
      </div>

      {/* 하단 버튼 */}
      <div className="flex justify-between">
        <button
          onClick={() => setSelectedPositions([])} // 초기화 버튼
          className="text-black bg-white px-4 py-2 rounded-lg shadow-sm border border-gray-300"
        >
          초기화
        </button>
        <button
          onClick={applyPositions} // 적용 버튼
          className="text-white bg-blue-500 px-4 py-2 rounded-lg shadow-sm"
        >
          적용
        </button>
      </div>
    </div>
  );
};

export default PositionModal;
