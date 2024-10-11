import { ReactNode } from "react";
import { useNavigate } from "react-router-dom";

function BannerCard({
  title,
  comment,
  btncomment,
  imgurl,
  pageurl,
}: {
  title: ReactNode;
  comment: string;
  btncomment: string;
  imgurl: string;
  pageurl: string;
}) {
  const navigate = useNavigate();
  // 메인 페이지로 이동 ('/')
  const movePage = () => {
    navigate(`/${pageurl}`);
  };

  return (
    <div>
      {/* 카드 영역 */}
      <div className="flex flex-row justify-between items-center w-full sm:w-auto w-150 h-75 rounded-3xl bg-[#4177FF] p-8">
        {/* 텍스트 영역 */}
        <div className="flex flex-col space-y-4">
          <p className="flex justify-start text-left font-bold leading-loose text-3xl text-white">
            {title}
          </p>
          <p className="text-white text-sm">{comment}</p>
          <button
            className="bg-white text-[#4177FF] px-6 py-3 rounded-full font-bold text-lg hover:scale-110 transition-transform duration-200"
            onClick={movePage}
          >
            {btncomment}
          </button>
        </div>
        {/* 이미지 영역 */}
        <div className="flex">
          <img src={imgurl} alt="캐릭터 이미지" className="w-70 h-auto" />
        </div>
      </div>
    </div>
  );
}

export default BannerCard;
