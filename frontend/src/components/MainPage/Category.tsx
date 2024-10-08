import { useState, useEffect, useRef } from "react";
import ArrowDown from "../../assets/ArrowDown.svg";

type CategoryProps = {
  title: string;
  onClick?: () => void; // 모달 열기 위한 함수
};

function Category({ title, onClick }: CategoryProps) {
  const [isOpen, setIsOpen] = useState(false); // 드롭다운 상태
  const dropdownRef = useRef<HTMLDivElement>(null);

  const toggleDropdown = () => setIsOpen((prev) => !prev);

  // 외부 클릭 시 드롭다운 창 닫기
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(event.target as Node)
      ) {
        setIsOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <div className="relative flex flex-col px-2" ref={dropdownRef}>
      {/* 카테고리 버튼 */}
      <button
        onClick={() => {
          toggleDropdown();
          if (onClick) {
            onClick(); // 모달 열기
          }
        }}
        className="w-28 h-9 bg-white rounded-xl border-[1.5px] border-[#abbdec] flex justify-between items-center px-4"
        aria-expanded={isOpen} // 접근성 속성 추가
        aria-haspopup="listbox"
      >
        <span className="text-[#535353] text-base font-normal px-1">
          {title}
        </span>
        <img
          src={ArrowDown}
          alt="ArrowDown"
          className={`transition-transform duration-200 w-4 h-4 ${
            isOpen ? "rotate-180" : ""
          }`}
        />
      </button>
    </div>
  );
}

export default Category;
