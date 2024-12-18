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
// import { useParams } from "react-router-dom";
import useResumeStore from "../store/ResumeStore.ts";

function ResumeFeedbackPage() {
  const [resumeData, setResumeData] = useState<ResumeData | null>(null);
  const [feedbackPoints, setFeedbackPoints] = useState<FeedbackPoint[]>([]);
  const [hoveredCommentId, setHoveredCommentId] = useState<number | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const { resumeId, setResumeUrl } = useResumeStore();

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        setError(null);
        const data = await getResumeApi(Number(resumeId));
        setResumeData(data);

        // 데이터에서 북마크 상태와 ID 설정
        if (data.bookmarks && data.bookmarks.length > 0) {
          setIsBookmarked(true);
          setBookmarkId(data.bookmarks[0].bookmarkId); // 첫 번째 북마크의 ID 설정
        } else {
          setIsBookmarked(false);
          setBookmarkId(null);
        }

        setResumeUrl(data.fileUrl);
        setFeedbackPoints(data.feedbackResponses || []);
      } catch (error) {
        console.error("Failed to fetch resume data", error);
        setError("Failed to fetch resume data. Please try again later.");
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [resumeId, setResumeUrl]);

  const handleAiFeedback = async () => {
    setLoading(true);
    try {
      const aiFeedback = await postAiFeedback(resumeId);
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
      console.log("업데이트 데이터: ", updatedData);
      setFeedbackPoints(updatedData.feedbackResponses);
    } catch (error) {
      console.error("Failed to add feedback point", error);
      setError("Failed to add feedback point. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  const deleteFeedbackPoint = async (id: number) => {
    try {
      setLoading(true);
      setError(null);

      // Call the API to delete the feedback point
      await deleteFeedbackApi(Number(resumeId), id);

      // After successful deletion, update the local state to remove the feedback
      setFeedbackPoints((prevComments) =>
        (prevComments || []).filter((item) => item.id !== id)
      );
      console.log("Deleted feedback point: ", id);
    } catch (error) {
      console.error("Failed to delete feedback point", error);
      setError("Failed to delete feedback point. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  const editFeedbackPoint = (updatedItem: AddFeedbackPoint) => {
    console.log("Edit feedback point: ", updatedItem);
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
    <div className="flex flex-col flex-grow ">
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
                editFeedbackPoint={editFeedbackPoint}
                hoveredCommentId={hoveredCommentId}
                handleAiFeedback={handleAiFeedback}
                setHoveredCommentId={setHoveredCommentId}
              />
            </div>
          </div>
        }
      >
        <MainContainer
          feedbackPoints={feedbackPoints}
          addFeedbackPoint={addFeedbackPoint}
          deleteFeedbackPoint={deleteFeedbackPoint}
          editFeedbackPoint={editFeedbackPoint}
          hoveredCommentId={hoveredCommentId}
          setHoveredCommentId={setHoveredCommentId}
        />
      </Layout>
    </div>
  );
}

export default ResumeFeedbackPage;
