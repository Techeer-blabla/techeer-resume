import { useState, useEffect, useRef } from "react";
import Slider from "./Slider"; // 'Silder' 오타 수정
import { postFilter } from "../../api/resumeApi";

interface PositionModalProps {
  isOpen: boolean;
  onClose: () => void;
}

const PositionModal = ({ isOpen, onClose }: PositionModalProps) => {
  const [minCareer, setMinCareer] = useState(0); // 최소 경력
  const [maxCareer, setMaxCareer] = useState(10); // 최대 경력
  const [, setFilterResults] = useState<any>(null); // 필터링된 결과를 저장할 상태
  const [, setIsCareerOpen] = useState(false); // 경력 모달 열기/닫기 상태
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

  // 슬라이더에서 경력 범위 변경시 호출되는 함수
  const handleSliderChange = (newMin: number, newMax: number) => {
    setMinCareer(newMin);
    setMaxCareer(newMax);
  };

  // 경력 필터링 API 호출 함수
  const openCareerModal = async () => {
    setIsCareerOpen(true);

    // 필터링 데이터 설정
    const filterData = {
      dto: {
        positions: [], // 포지션 필드 빈 배열로 설정 (필요에 따라 수정 가능)
        minCareer: minCareer, // 선택된 최소 경력
        maxCareer: maxCareer, // 선택된 최대 경력
        techStacks: [], // 기술 스택 필드 빈 배열로 설정 (필요에 따라 수정 가능)
      },
      pageable: {
        page: 0,
        size: 1,
        sort: ["string"], // 정렬 기준 (예시)
      },
    };

    try {
      const response = await postFilter(filterData); // API 호출
      setFilterResults(response.data); // 필터링된 결과 설정
      console.log("경력 필터링 결과:", response);
    } catch (error) {
      console.error("경력 필터링 오류:", error);
    }
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

      {/* 경력 타이틀 */}
      <div className="text-black text-2xl font-semibold mb-6">경력</div>
      <div className="p-3 my-3 ml-3">
        <Slider
          minCareer={minCareer}
          maxCareer={maxCareer}
          onChange={handleSliderChange} // 슬라이더 값 변경시 호출
        />
      </div>

      {/* 하단 버튼 */}
      <div className="flex justify-between">
        <button className="w-24 h-10 border border-gray-300 rounded-lg flex items-center justify-center text-black text-base font-medium">
          초기화
        </button>
        <button
          onClick={openCareerModal} // 필터링된 값을 기반으로 API 호출
          className="w-24 h-10 bg-blue-500 rounded-lg flex items-center justify-center text-white text-base font-medium"
        >
          적용
        </button>
      </div>
    </div>
  );
};

export default PositionModal;
