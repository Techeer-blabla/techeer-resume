import { useState } from "react";
import Navbar from "../components/Navbar";
import PositionModal from "../components/Search/PositionModal";
import CareerModal from "../components/Search/CareerModal";
import down from "../assets/chevron-down.svg";
import PostCard from "../components/PostCard";
import { useStore } from "zustand";
import CategoryStore from "../store/CategoryStore";

function SearchPage() {
  const [title] = useState<string>("김테커");
  const [isPositionOpen, setIsPositionOpen] = useState<boolean>(false);
  const [isCareerOpen, setIsCareerOpen] = useState<boolean>(false);

  // zustand에서 positionCategory 가져오기
  const { positionCategory } = useStore<string[]>(CategoryStore);

  // 포지션 모달
  const openPositionModal = () => setIsPositionOpen(true);
  const closePositionModal = () => setIsPositionOpen(false);

  // 경력 모달
  const openCareerModal = () => setIsCareerOpen(true);
  const closeCareerModal = () => setIsCareerOpen(false);

  return (
    <div className="bg-white">
      <div className="p-5">
        <Navbar />
        <div className="flex flex-col justify-start p-5 ">
          <div className="w-full max-w-screen-lg mx-auto">
            <div className="flex flex-row justify-start items-center p-10">
              <span className="text-black text-4xl font-extrabold">
                ‘{title}’
              </span>
              <span className="text-black text-3xl font-normal ml-8 mt-2">
                검색 결과
              </span>
            </div>

            {/* 포지션과 경력 선택 버튼 */}
            <div className="flex flex-row items-center px-24 -ml-10 -mt-3 relative">
              <p className="font-medium text-black text-xl">포지션</p>
              <div className="relative">
                <img
                  className="w-6 h-6 border border-[1.5px] rounded-lg p-1 ml-2 cursor-pointer"
                  src={down}
                  alt="down"
                  onClick={openPositionModal}
                />
                {/* 포지션 모달 */}
                {isPositionOpen && (
                  <div className="absolute top-10 -left-14 z-50">
                    <PositionModal
                      isOpen={isPositionOpen}
                      onClose={closePositionModal}
                    />
                  </div>
                )}
              </div>

              <p className="font-medium text-black text-xl ml-8">경력</p>
              <div className="relative">
                <img
                  className="w-6 h-6 border border-[1.5px] rounded-lg p-1 ml-2 cursor-pointer"
                  src={down}
                  alt="down"
                  onClick={openCareerModal}
                />
                {/* 경력 모달 */}
                {isCareerOpen && (
                  <div className="absolute top-10 -left-10 z-50">
                    <CareerModal
                      isOpen={isCareerOpen}
                      onClose={closeCareerModal}
                    />
                  </div>
                )}
              </div>
            </div>

            {/* 선택한 포지션 카테고리 표시 */}
            <div className="flex space-x-2 mt-7 mb-2 ml-10">
              {Array.isArray(positionCategory) &&
                positionCategory.length > 0 &&
                positionCategory.map((category, index) => (
                  <div
                    key={index}
                    className="flex items-center justify-center px-4 py-1 bg-[#618EFF] rounded-xl text-center text-white text-sm font-medium"
                  >
                    {category}
                  </div>
                ))}
            </div>
          </div>
        </div>

        <div className="flex justify-center bg-[#eff4ff] px-10">
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 transform scale-90 gap-6 p-5">
            <PostCard
              name={"김테커"}
              role={"프론트엔드"}
              experience={"신입"}
              education={"전공자"}
              skills={["React", "Next"]}
            />
            <PostCard
              name={"김테커"}
              role={"프론트엔드"}
              experience={"신입"}
              education={"전공자"}
              skills={["React", "Next"]}
            />
            <PostCard
              name={"김테커"}
              role={"프론트엔드"}
              experience={"신입"}
              education={"전공자"}
              skills={["React", "Next"]}
            />
            <PostCard
              name={"김테커"}
              role={"프론트엔드"}
              experience={"신입"}
              education={"전공자"}
              skills={["React", "Next"]}
            />
            <PostCard
              name={"김테커"}
              role={"프론트엔드"}
              experience={"신입"}
              education={"전공자"}
              skills={["React", "Next"]}
            />
            <PostCard
              name={"김테커"}
              role={"프론트엔드"}
              experience={"신입"}
              education={"전공자"}
              skills={["React", "Next"]}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default SearchPage;
