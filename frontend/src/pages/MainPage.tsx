import { useEffect, useRef } from "react";
import { useInfiniteQuery } from "@tanstack/react-query";
import api from "../baseURL/baseURL";
import Navbar from "../components/common/Navbar.tsx";
import BannerCard from "../components/MainPage/BannerCard";
import Category from "../components/MainPage/Category";
import PostCard from "../components/common/PostCard.tsx";
import man1 from "../assets/man1.png";
import man2 from "../assets/man2.png";
import { PostCardsType } from "../dataType.ts";
import { postFilter } from "../api/resumeApi";

interface FilterResult {
  name: string;
  role: string;
  experience: string;
  education: string;
  skills: string[];
}

function MainPage() {
  // 포스트카드 GET API 요청 함수
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
  const loadMoreRef = useRef<HTMLDivElement | null>(null);

  const { data, fetchNextPage, hasNextPage } = useInfiniteQuery({
    queryKey: ["postCards"],
    queryFn: async ({ pageParam = 0 }) => {
      return fetchPostCards(pageParam);
    },
    getNextPageParam: (lastPage) => {
      // 마지막 페이지인지 확인하고, 페이지 넘버를 반환
      if (lastPage.currentPage + 1 < lastPage.totalPage) {
        return lastPage.currentPage + 1;
      } else {
        return undefined;
      }
    },
    initialPageParam: 0,
  });

  console.log("data: ", data);
  useEffect(() => {
    if (!hasNextPage) return;

    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          fetchNextPage(); // 마지막 요소에 닿으면 다음 페이지 데이터 가져오기
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
  }, [hasNextPage, fetchNextPage, loadMoreRef.current]);

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
          {/* 카테고리 */}
          <div className="max-w-screen-xl mx-auto py-6 relative">
            <Category title="조회순" options={["인기순", "최신순"]} />
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
                  <p>데이터 불러오는 중</p>
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
