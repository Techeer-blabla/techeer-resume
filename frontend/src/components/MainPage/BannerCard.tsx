import { ReactNode } from "react";

function BannerCard({
  title,
  comment,
  btncomment,
  imgurl,
}: {
  title: ReactNode;
  comment: string;
  btncomment: string;
  imgurl: string;
}) {
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
          <button className="bg-white text-[#4177FF] px-6 py-3 rounded-full font-bold text-lg">
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
