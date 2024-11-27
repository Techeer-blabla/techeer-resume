import chevronLeft from "../../assets/chevron-left.png";
import chevronRight from "../../assets/chevron-right.png";

function Footer() {
  return (
    <footer className="w-full h-[49px] bg-white border-t border-[#e0e0e0] flex justify-between items-center px-4">
      {/* Previous Resume */}
      <div className="flex items-center space-x-2 mt-3 -mb-5">
        <img src={chevronLeft} alt="Previous Resume" className="w-6 h-6" />{" "}
        {/* Left Chevron */}
        <div className="text-[#6e6e6e] text-[15px] font-medium font-pretendard">
          이전 이력서
        </div>
      </div>

      {/* Next Resume */}
      <div className="flex items-center space-x-2 mt-3 -mb-5">
        <div className="text-[#6e6e6e] text-[15px] font-medium font-pretendard">
          다음 이력서
        </div>
        <img src={chevronRight} alt="Next Resume" className="w-6 h-6" />{" "}
        {/* Right Chevron */}
      </div>
    </footer>
  );
}

export default Footer;
