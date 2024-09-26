import { useState, useEffect, useRef } from "react";
import ArrowDown from "../../assets/ArrowDown.svg";

function Category({ title, options }: { title: string; options: string[] }) {
  const [isOpen, setIsOpen] = useState(false); // 드롭다운 창 상태
  const dropdownRef = useRef<HTMLDivElement>(null);

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  // 밖에 클릭 경우 창 닫기
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
  }, [dropdownRef]);

  return (
    <div className="relative flex flex-col px-2" ref={dropdownRef}>
      {/* 카테고리 버튼 */}
      <button
        onClick={toggleDropdown}
        className="w-28 h-9 bg-white rounded-xl border-[1.5px] border-[#abbdec] flex justify-between items-center px-4"
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

      {/* 드롭다운 메뉴 */}
      {isOpen && (
        <div className="absolute top-[45px] left-0 w-36 bg-white border-[1.5px] border-[#abbdec] rounded-xl shadow-lg z-10 text-gray-500">
          {options.map((option, index) => (
            <div
              key={index}
              className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
              onClick={() => {
                console.log(`${option} selected`);
                setIsOpen(false);
              }}
            >
              {option}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Category;
