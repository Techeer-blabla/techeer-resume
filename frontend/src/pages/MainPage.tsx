import { useState, useEffect, useRef } from "react";
import { useInfiniteQuery } from "@tanstack/react-query";
import { getResumeList } from "../api/resumeApi.ts";
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
      const resumeList = await getResumeList(page, size);
      return resumeList;
    } catch (error) {
      console.error("포스트카드 조회 오류:", error);
      throw error;
    }
  };

  // useInfiniteQuery 사용하여 데이터 가져오기
  const loadMoreRef = useRef<HTMLDivElement | null>(null);

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage } =
    useInfiniteQuery({
      queryKey: ["postCards"],
      queryFn: async ({ pageParam = 0 }) => {
        return fetchPostCards(pageParam);
      },
      getNextPageParam: (lastPage) => {
        console.log(
          "Current page and total pages:",
          lastPage[0].current_page,
          lastPage[0].total_page
        );
        if (lastPage[0].current_page + 1 < lastPage[0].total_page) {
          return lastPage[0].current_page + 1;
        } else {
          return undefined;
        }
      },
      initialPageParam: 0,
    });

  console.log("resumeList: ", data);

  useEffect(() => {
    if (!hasNextPage || isFetchingNextPage) return;

    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          console.log("Observer triggered, fetching next page...");
          fetchNextPage();
        }
      },
      {
        root: null,
        rootMargin: "0px",
        threshold: 0.1,
      }
    );

    const loadMoreCurrent = loadMoreRef.current;
    if (loadMoreCurrent) observer.observe(loadMoreCurrent);

    return () => {
      if (loadMoreCurrent) observer.unobserve(loadMoreCurrent);
    };
  }, [hasNextPage, fetchNextPage, isFetchingNextPage]);

  return (
    <div className="w-full bg-[#D7E1F5]">
      <div className="pt-5">
        <Navbar />

        {/* 배너 */}
        <div className="flex justify-center p-5 space-x-4 max-w-screen-xl mx-auto">
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
            <div className="grid grid-cols-1 min-[700px]:grid-cols-1 md:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4 gap-6 p-5">
              {data?.pages && data.pages.length > 0 ? (
                data.pages.map((page) =>
                  page?.map((post: PostCardsType) => (
                    <PostCard
                      key={post.resume_id}
                      name={post.user_name}
                      role={post.position}
                      experience={post.career}
                      education="전공자"
                      skills={post.tech_stack_names}
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
          <div ref={loadMoreRef} className="h-1" />
        </div>
      </div>
    </div>
  );
}

export default MainPage;
