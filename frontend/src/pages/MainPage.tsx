import { useState, useEffect, useRef, useCallback } from "react";
import { useInfiniteQuery } from "@tanstack/react-query";
import { getResumeList, postFilter } from "../api/resumeApi";
import Navbar from "../components/common/Navbar";
import BannerCard from "../components/MainPage/BannerCard";
import Category from "../components/MainPage/Category";
import PostCard from "../components/common/PostCard";
import man1 from "../assets/man1.png";
import man2 from "../assets/man2.png";
import PositionModal from "../components/Search/PositionModal";
import CareerModal from "../components/Search/CareerModal";
import useFilterStore from "../store/useFilterStore";
import { PostCardsType } from "../dataType.ts";

function MainPage() {
  const [isPositionOpen, setIsPositionOpen] = useState(false);
  const [isCareerOpen, setIsCareerOpen] = useState(false);
  const { positions, min_career, max_career, setCareerRange } =
    useFilterStore();
  const [, setFilterResults] = useState<unknown>(null);

  const fetchPostCards = async (page: number, size = 8) => {
    try {
      const resumeList = await getResumeList(page, size);
      return resumeList;
    } catch (error) {
      console.error("포스트카드 조회 오류:", error);
      throw error;
    }
  };

  // useCallback으로 handleApplyCareerFilter 함수 메모이제이션
  const handleApplyCareerFilter = useCallback(
    async (min: number, max: number) => {
      console.log("handleApplyCareerFilter called with:", { min, max }); // 호출 확인
      setCareerRange(min, max);
      const filterData = {
        dto: {
          positions,
          min_career: min,
          max_career: max,
          tech_stack_names: [], // 필터링에서 tech_stack_names 제외
          company_names: [],
        },
        pageable: {
          page: 1,
          size: 3,
        },
      };
      console.log("Filter data to be sent:", filterData); // API 호출 데이터 확인

      try {
        const response = await postFilter(filterData);
        console.log("Filter API response:", response); // 응답 데이터 확인
        setFilterResults(response);
      } catch (error) {
        console.error("필터링 오류:", error);
      }
    },
    [positions, setCareerRange] // 의존성 배열에 positions, setCareerRange 추가
  );

  const loadMoreRef = useRef<HTMLDivElement | null>(null);
  const { data, fetchNextPage, hasNextPage, isFetchingNextPage } =
    useInfiniteQuery({
      queryKey: ["postCards", positions, min_career, max_career],
      queryFn: async ({ pageParam = 0 }) => fetchPostCards(pageParam),
      getNextPageParam: (lastPage) =>
        lastPage[0].current_page + 1 < lastPage[0].total_page
          ? lastPage[0].current_page + 1
          : undefined,
      initialPageParam: 0,
    });

  // 필터가 변경될 때마다 handleApplyCareerFilter 호출
  useEffect(() => {
    handleApplyCareerFilter(min_career, max_career);
  }, [positions, min_career, max_career, handleApplyCareerFilter]); // handleApplyCareerFilter 추가

  useEffect(() => {
    if (!hasNextPage || isFetchingNextPage) return;
    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          fetchNextPage();
        }
      },
      { root: null, rootMargin: "0px", threshold: 0.1 }
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
          <div className="max-w-screen-xl mx-auto py-6 relative">
            <div className="flex space-x-4">
              <Category title="조회순" options={["인기순", "최신순"]} />
              <Category
                title="포지션"
                onClick={() => setIsPositionOpen(true)}
              />
              <Category title="경력" onClick={() => setIsCareerOpen(true)} />
            </div>

            {/* 포지션, 경력 모달 */}
            {isPositionOpen && (
              <PositionModal
                isOpen={isPositionOpen}
                onClose={() => setIsPositionOpen(false)}
              />
            )}
            {isCareerOpen && (
              <CareerModal
                isOpen={isCareerOpen}
                onClose={() => setIsCareerOpen(false)}
              />
            )}
          </div>

          {/* 포스트 카드 */}
          <div className="flex justify-center">
            <div className="grid grid-cols-1 min-[700px]:grid-cols-1 md:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4 gap-6 p-5">
              {data?.pages && data.pages.length > 0 ? (
                data.pages.map((page) =>
                  page.map((post: PostCardsType) => (
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
