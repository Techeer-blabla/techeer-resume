import { useState } from "react";
import Navbar from "../components/Navbar";
import BannerCard from "../components/MainPage/BannerCard";
import Category from "../components/MainPage/Category";
import PostCard from "../components/PostCard";
import PositionModal from "../components/Search/PositionModal";
import CareerModal from "../components/Search/CareerModal";
import man1 from "../assets/man1.png";
import man2 from "../assets/man2.png";

function MainPage() {
  // 포지션, 경력 모달 상태 관리
  const [isPositionOpen, setIsPositionOpen] = useState<boolean>(false);
  const [isCareerOpen, setIsCareerOpen] = useState<boolean>(false);

  // 포지션 모달 열기/닫기
  const openPositionModal = () => setIsPositionOpen(true);
  const closePositionModal = () => setIsPositionOpen(false);

  // 경력 모달 열기/닫기
  const openCareerModal = () => setIsCareerOpen(true);
  const closeCareerModal = () => setIsCareerOpen(false);

  return (
    <div className="w-full bg-[#D7E1F5]">
      <div className="pt-5">
        <Navbar />

        {/* 배너 */}
        <div className="flex justify-between items-center p-5 space-x-4 max-w-screen-xl mx-auto">
          <BannerCard
            title="내가 지원할 기업은?"
            comment="채용 공고를 한 번에 볼 수 있습니다."
            btncomment="지금 확인하기"
            imgurl={man1}
          />
          <BannerCard
            title={
              <>
                이력서 피드백이 <br />
                필요할때?
              </>
            }
            comment="이력서를 등록하고 피드백을 받을 수 있습니다."
            btncomment="등록하러 가기"
            imgurl={man2}
          />
        </div>
      </div>

      <div className="w-full bg-white">
        <div className="p-6">
          {/* 카테고리 */}
          <div className="max-w-screen-xl mx-auto flex justify-start py-6">
            {/* <Category title="조회순" options={["인기순", "최신순"]} /> */}

            <Category title="포지션" onClick={openPositionModal} />

            <Category title="경력" onClick={openCareerModal} />
          </div>

          {/* 포지션 모달 */}
          {isPositionOpen && (
            <div className="absolute top-10 left-10 z-50">
              <PositionModal
                isOpen={isPositionOpen}
                onClose={closePositionModal}
              />
            </div>
          )}

          {/* 경력 모달 */}
          {isCareerOpen && (
            <div className="absolute top-10 left-10 z-50">
              <CareerModal isOpen={isCareerOpen} onClose={closeCareerModal} />
            </div>
          )}

          {/* 포스트 카드 */}
          <div className="flex justify-center">
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-5">
              <PostCard
                name="김테커"
                role="프론트엔드"
                experience="신입"
                education="전공자"
                skills={["React", "Next", "Vue"]}
              />
              <PostCard
                name="김테커"
                role="프론트엔드"
                experience="신입"
                education="전공자"
                skills={["React", "Next", "Vue"]}
              />
              <PostCard
                name="김테커"
                role="프론트엔드"
                experience="신입"
                education="전공자"
                skills={["React", "Next", "Vue"]}
              />
              <PostCard
                name="김테커"
                role="프론트엔드"
                experience="신입"
                education="전공자"
                skills={["React", "Next", "Vue"]}
              />
              <PostCard
                name="김테커"
                role="프론트엔드"
                experience="신입"
                education="전공자"
                skills={["React", "Next", "Vue"]}
              />
              <PostCard
                name="김테커"
                role="프론트엔드"
                experience="신입"
                education="전공자"
                skills={["React", "Next", "Vue"]}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MainPage;
