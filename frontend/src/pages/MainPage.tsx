import { useState, useEffect, useRef } from "react";
import { useInfiniteQuery } from "@tanstack/react-query";
import api from "../baseURL/baseURL";
import Navbar from "../components/common/Navbar.tsx";
import BannerCard from "../components/MainPage/BannerCard";
import Category from "../components/MainPage/Category";
import PostCard from "../components/common/PostCard.tsx";
import man1 from "../assets/man1.png";
import man2 from "../assets/man2.png";
import PositionModal from "../components/Search/PositionModal"; // 수정된 import
import CareerModal from "../components/Search/CareerModal";
import { PostCardsType } from "../dataType.ts";
import { postFilter } from "../api/resumeApi";

function MainPage() {
  const [selectedPositions, setSelectedPositions] = useState<string[]>([]);
  const [minCareer] = useState(0); // 최소 경력
  const [maxCareer] = useState(5); // 최대 경력
  const [, setFilterResults] = useState<unknown>(null); // 필터링된 데이터

  const loadMoreRef = useRef<HTMLDivElement | null>(null);

  // 포스트 카드 GET API 요청 함수
  const fetchPostCards = async (pageParam: number) => {
    try {
      const response = await api.get(
        `/resumes?page=${pageParam}&size=10&sort=`
      );
      return response.data;
    } catch (e) {
      alert(e);
      throw e;
    }
  };

  // useInfiniteQuery 사용하여 데이터 가져오기
  const { data, fetchNextPage, hasNextPage } = useInfiniteQuery({
    queryKey: ["postCards", selectedPositions, minCareer, maxCareer], // 필터 값도 의존성 배열에 추가
    queryFn: async ({ pageParam = 0 }) => {
      // 필터된 데이터와 함께 요청 보내기
      return fetchPostCards(pageParam);
    },
    getNextPageParam: (lastPage) => {
      if (lastPage.currentPage + 1 < lastPage.totalPage) {
        return lastPage.currentPage + 1;
      } else {
        return undefined;
      }
    },
    initialPageParam: 0,
  });

  useEffect(() => {
    if (!hasNextPage) return;

    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          fetchNextPage();
        }
      },
      {
        root: null,
        rootMargin: "0px",
        threshold: 0.1, // 10% 보였을 때 트리거
      }
    );

    if (loadMoreRef.current) observer.observe(loadMoreRef.current);

    return () => {
      if (loadMoreRef.current) observer.disconnect();
    };
  }, [hasNextPage, fetchNextPage]);

  // 포지션, 경력 모달 상태 관리
  const [isPositionOpen, setIsPositionOpen] = useState(false);
  const [isCareerOpen, setIsCareerOpen] = useState(false);

  const openPositionModal = () => setIsPositionOpen(true);
  const closePositionModal = () => setIsPositionOpen(false);

  const openCareerModal = () => {
    setIsCareerOpen(true);
    // 경력 필터링 API 호출
    const filterData = {
      dto: {
        positions: selectedPositions,
        minCareer: minCareer,
        maxCareer: maxCareer,
        techStacks: ["string"], // 기술 스택 필터링 필요 시 추가
      },
      pageable: {
        page: 0,
        size: 1,
        sort: ["string"], // 정렬 기준 필요 시 추가
      },
    };

    postFilter(filterData)
      .then((response) => {
        setFilterResults(response.data); // 필터링된 결과 설정
      })
      .catch((error) => {
        console.error("경력 필터링 오류:", error);
      });
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
            pageurl=""
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
            pageurl="upload"
          />
        </div>
      </div>

      <div className="w-full bg-white relative">
        <div className="p-6">
          {/* 카테고리: 조회순, 포지션, 경력 */}
          <div className="max-w-screen-xl mx-auto py-6 relative">
            <div className="flex space-x-4">
              <Category title="조회순" options={["인기순", "최신순"]} />
              <Category title="포지션" onClick={openPositionModal} />
              <Category title="경력" onClick={openCareerModal} />
            </div>

            {/* 포지션 모달 */}
            {isPositionOpen && (
              <div className="absolute top-full left-0 w-full mt-2 z-50">
                <PositionModal
                  isOpen={isPositionOpen}
                  onClose={closePositionModal}
                  setSelectedPositions={setSelectedPositions} // 포지션 선택 후 상태 업데이트
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
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-5">
              {data?.pages && data.pages.length > 0 ? (
                data.pages.map((page) =>
                  page.element_list?.map((post: PostCardsType) => (
                    <PostCard
                      key={post.resume_id}
                      name={post.user_name}
                      role={post.position}
                      experience={post.career}
                      education="전공자"
                      skills={post.tech_stack}
                    />
                  ))
                )
              ) : (
                <div className="flex justify-center w-screen mt-10">
                  <p>데이터 불러오는 중...</p>
                </div>
              )}
            </div>
          </div>

          {/* 무한스크롤 로딩 트리거 */}
          <div ref={loadMoreRef} className="w-1" />
        </div>
      </div>
    </div>
  );
}

export default MainPage;
