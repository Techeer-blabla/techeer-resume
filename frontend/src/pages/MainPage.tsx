import { useState } from "react";
import Navbar from "../components/Navbar";
import BannerCard from "../components/MainPage/BannerCard";
import Category from "../components/MainPage/Category";
import PostCard from "../components/PostCard";
import PositionModal from "../components/Search/PositionModal";
import CareerModal from "../components/Search/CareerModal";
import man1 from "../assets/man1.png";
import man2 from "../assets/man2.png";
import { postFilter } from "../api/resumeApi";

interface FilterResult {
  name: string;
  role: string;
  experience: string;
  education: string;
  skills: string[];
}

function MainPage() {
  // 포지션, 경력 모달 상태 관리
  const [isPositionOpen, setIsPositionOpen] = useState<boolean>(false);
  const [isCareerOpen, setIsCareerOpen] = useState<boolean>(false);
  const [filterResults, setFilterResults] = useState<FilterResult[]>([]);

  const openPositionModal = async () => {
    // async 추가
    setIsPositionOpen(true);

    const filterData = {
      dto: {
        positions: ["BACKEND"], // 예시로 BACKEND 포지션 설정
        minCareer: 0,
        maxCareer: 0,
        techStacks: ["string"],
      },
      pageable: {
        page: 0,
        size: 1,
        sort: ["string"],
      },
    };

    try {
      const response = await postFilter(filterData); // await 사용
      setFilterResults(response.data); // 응답 결과 설정
      console.log("포지션 필터링 결과:", response);
    } catch (error) {
      console.error("포지션 필터링 오류:", error);
    }
  };

  const closePositionModal = () => setIsPositionOpen(false);

  // 경력 모달 열기/닫기 및 API 호출
  const openCareerModal = async () => {
    // async 추가
    setIsCareerOpen(true);

    const filterData = {
      dto: {
        positions: [], // 경력 필터이므로 positions는 빈 배열로 설정
        minCareer: 0,
        maxCareer: 5, // 예시로 경력 0-5년 설정
        techStacks: ["string"],
      },
      pageable: {
        page: 0,
        size: 1,
        sort: ["string"],
      },
    };

    try {
      const response = await postFilter(filterData); // await 사용
      setFilterResults(response.data); // 응답 결과 설정
      console.log("경력 필터링 결과:", response);
    } catch (error) {
      console.error("경력 필터링 오류:", error);
    }
  };

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

      <div className="w-full bg-white relative">
        <div className="p-6">
          {/* 카테고리 */}
          <div className="max-w-screen-xl mx-auto py-6 relative">
            <div className="flex justify-start space-x-4">
              <Category title="포지션" onClick={openPositionModal} />
              <Category title="경력" onClick={openCareerModal} />
            </div>

            {/* 포지션 모달 */}
            {isPositionOpen && (
              <div className="absolute top-full left-0 w-full mt-2 z-50">
                <PositionModal
                  isOpen={isPositionOpen}
                  onClose={closePositionModal}
                />
              </div>
            )}

            {/* 경력 모달 */}
            {isCareerOpen && (
              <div className="absolute top-full left-0 w-full mt-2 z-50">
                <CareerModal isOpen={isCareerOpen} onClose={closeCareerModal} />
              </div>
            )}
          </div>

          {/* 포스트 카드 */}
          <div className="flex justify-center">
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-5 z-10">
              {filterResults.map((result, index) => (
                <PostCard
                  key={index} // index를 key로 사용 (가능하다면 고유 ID 사용 권장)
                  name={result.name}
                  role={result.role}
                  experience={result.experience}
                  education={result.education}
                  skills={result.skills}
                />
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MainPage;
