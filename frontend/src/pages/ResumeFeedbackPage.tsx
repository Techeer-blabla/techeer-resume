import { useEffect, useState } from "react";
import Layout from "../components/Layout/Layout";
import MainContainer from "../components/resumeoverview/MainContainer";
import ResumeOverview from "../components/resumeoverview/ResumeOverview";
import CommentSection from "../components/comment/CommentSection.tsx";
import LoadingSpinner from "../components/UI/LoadingSpinner";
import ErrorMessage from "../components/UI/ErrorMessage";
import {
  addFeedbackApi,
  deleteFeedbackApi,
  getResumeApi,
  postAiFeedback,
} from "../api/feedbackApi.ts";
import { AddFeedbackPoint, FeedbackPoint, ResumeData } from "../types.ts";
import { Bookmark, BookmarkMinus } from "lucide-react";
import { postBookmark, deleteBookmarkById } from "../api/bookMarkApi.ts";
import { useParams } from "react-router-dom";
import { useResumeStore } from "../store/ResumeStore.ts";

function ResumeFeedbackPage() {
  // const { resumeId } = useResumeStore(); // 전역 상태에서 resumeId만 가져옵니다.
  const [resumeData, setResumeData] = useState<ResumeData | null>(null);
  const [feedbackPoints, setFeedbackPoints] = useState<FeedbackPoint[]>([]);
  const [hoveredCommentId, setHoveredCommentId] = useState<number | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [isBookmarked, setIsBookmarked] = useState<boolean>(false); // 북마크 상태
  const [bookmarkId, setBookmarkId] = useState<number | null>(null); // 북마크 ID 저장
  const { resumeId } = useParams();
  const { setResumeUrl } = useResumeStore();

  useEffect(() => {
    console.log("resumeId:", resumeId);
    const fetchData = async () => {
      if (!resumeId) {
        setError("Resume ID is missing.");
        return;
      }

      try {
        setLoading(true);
        setError(null);

        // 이력서와 피드백 데이터를 받아옵니다.
        const data = await getResumeApi(Number(resumeId));

        setResumeData(data);
        setFeedbackPoints(data?.feedbacks || []);

        // 북마크 상태 초기화
        const bookmarkResponse = await postBookmark(Number(resumeId)); // userId 제거, resumeId만 사용
        if (bookmarkResponse) {
          setIsBookmarked(true);
          setBookmarkId(bookmarkResponse);
        }
      } catch (error) {
        console.error("Failed to fetch resume data", error);
        setError("Failed to fetch resume data. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [resumeId]); // resumeId가 변경될 때마다 데이터 로딩

  // 북마크 토글 기능
  const toggleBookmark = async () => {
    if (!resumeId) {
      setError("Resume ID is missing.");
      return;
    }

    try {
      if (isBookmarked) {
        // 북마크 해제
        if (bookmarkId !== null) {
          await deleteBookmarkById(bookmarkId);
        }
        setIsBookmarked(false);
        setBookmarkId(null);
      } else {
        // 북마크 추가
        const response = await postBookmark(Number(resumeId)); // userId 제거, resumeId만 사용
        setIsBookmarked(true);
        setBookmarkId(response); // 서버로부터 받은 북마크 ID 저장
      }
    } catch (error) {
      console.error("Failed to toggle bookmark", error);
      alert("북마크 상태를 변경할 수 없습니다. 다시 시도해주세요.");
    }
  };

  const handleAiFeedback = async () => {
    setLoading(true);
    try {
      const aiFeedback = await postAiFeedback(Number(resumeId));
      setFeedbackPoints((prevPoints) => [
        ...prevPoints,
        ...aiFeedback.feedbacks,
      ]);
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      setError("Failed to retrieve AI feedback.");
    } finally {
      setLoading(false);
    }
  };

  const addFeedbackPoint = async (point: Omit<AddFeedbackPoint, "id">) => {
    console.log("try 직전");
    if (
      !point.content ||
      point.xCoordinate === undefined ||
      point.yCoordinate === undefined
    ) {
      setError("All fields are required to add a feedback point.");
      return;
    }

    try {
      setLoading(true);
      const newPoint: AddFeedbackPoint = {
        xCoordinate: point.xCoordinate,
        yCoordinate: point.yCoordinate,
        content: point.content,
        pageNumber: 1,
      };
      await addFeedbackApi(Number(resumeId), newPoint);
      const updatedData = await getResumeApi(Number(resumeId));
      setFeedbackPoints(updatedData.feedbacks);
      // setResumeUrl(updatedData.file_url);
    } catch (error) {
      console.error("Failed to add feedback point", error);
      setError("Failed to add feedback point. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  // 피드백 점 삭제
  const deleteFeedbackPoint = async (id: number) => {
    try {
      setLoading(true);
      setError(null);

      // 피드백 점 삭제 API 호출
      await deleteFeedbackApi(Number(resumeId), id);

      // 삭제 후 상태 갱신
      setFeedbackPoints(
        (prevComments) => prevComments.filter((item) => item.id !== id) // 상태에서 삭제된 피드백 제거
      );
    } catch (error) {
      console.error("Failed to delete feedback point", error);
      setError("Failed to delete feedback point. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <LoadingSpinner />;
  }

  if (error) {
    return <ErrorMessage message={error} />;
  }

  if (!resumeData) {
    return <div>No resume data available.</div>;
  }
  

  return (
    <div className="flex flex-col flex-grow">
      <Layout
        sidebar={
          <div className="flex flex-col justify-between bg-white p-2 mt-10">
            {/* 북마크 버튼 */}
            <button
              onClick={toggleBookmark}
              className={`flex items-center px-6 py-3 rounded-lg ${
                isBookmarked
                  ? "bg-yellow-100 text-yellow-900"
                  : "text-gray-500 hover:bg-gray-50"
              }`}
            >
              {isBookmarked ? (
                <>
                  <BookmarkMinus className="w-5 h-5 mr-2" />
                  북마크 제거
                </>
              ) : (
                <>
                  <Bookmark className="w-5 h-5 mr-2" />
                  북마크 추가
                </>
              )}
            </button>
            {/* Resume Overview */}
            <ResumeOverview />

            {/* Comment Section */}
            <div className="overflow-y-auto mt-2">
              <CommentSection
                feedbackPoints={feedbackPoints}
                addFeedbackPoint={addFeedbackPoint}
                deleteFeedbackPoint={deleteFeedbackPoint}
                handleAiFeedback={handleAiFeedback}
                hoveredCommentId={hoveredCommentId}
                setHoveredCommentId={setHoveredCommentId}
                editFeedbackPoint={() => {
                  throw new Error("Function not implemented.");
                }}
              />
            </div>
          </div>
        }
      >
        <MainContainer
          feedbackPoints={feedbackPoints}
          addFeedbackPoint={addFeedbackPoint}
          deleteFeedbackPoint={deleteFeedbackPoint}
          hoveredCommentId={hoveredCommentId}
          setHoveredCommentId={setHoveredCommentId}
          editFeedbackPoint={() => {
            throw new Error("Function not implemented.");
          }}
        />
      </Layout>
    </div>
  );
}

export default ResumeFeedbackPage;
