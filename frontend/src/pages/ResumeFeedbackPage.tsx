import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
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
import {
  postBookmark,
  deleteBookmarkById,
  getBookmarkById,
} from "../api/bookMarkApi.ts";
import { AddFeedbackPoint, FeedbackPoint, ResumeData } from "../types.ts";
import useResumeStore from "../store/ResumeStore.ts";
import { useBookmarkStore } from "../store/BookmarkStore.ts";
import { Bookmark, BookmarkMinus } from "lucide-react";
import Swal from "sweetalert2";

function ResumeFeedbackPage() {
  const [resumeData, setResumeData] = useState<ResumeData | null>(null);
  const [feedbackPoints, setFeedbackPoints] = useState<FeedbackPoint[]>([]);
  const [hoveredCommentId, setHoveredCommentId] = useState<number | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const { id } = useParams();
  const resumeId = Number(id);
  const { setResumeUrl } = useResumeStore();
  const { bookmarks, setBookmarks, isBookmarked } = useBookmarkStore();

  useEffect(() => {
    const fetchData = async () => {
      if (!resumeId) {
        setError("Resume ID is missing.");
        return;
      }
      try {
        setLoading(true);
        setError(null);

        const data = await getResumeApi(resumeId);
        setResumeData(data);
        setFeedbackPoints(data.feedbackResponses || []);
        setResumeUrl(data.fileUrl);

        const userId = 1;
        const bookmarksData = await getBookmarkById(userId);
        setBookmarks(bookmarksData.result || []);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [resumeId, setResumeUrl, setBookmarks]);

  const toggleBookmark = async () => {
    if (!resumeId) {
      setError("Resume ID is missing.");
      return;
    }

    try {
      const existingBookmark = bookmarks.find(
        (bk) => bk.resume_id === resumeId
      );

      if (existingBookmark) {
        await deleteBookmarkById(existingBookmark.bookmark_id);
        setBookmarks(
          bookmarks.filter(
            (bk) => bk.bookmark_id !== existingBookmark.bookmark_id
          )
        );
        Swal.fire({
          icon: "success",
          title: "북마크가 해제되었습니다.",
          confirmButtonText: "확인",
        });
      } else {
        const newBookmarkId = await postBookmark(resumeId);
        setBookmarks([
          ...bookmarks,
          {
            bookmark_id: newBookmarkId,
            resume_id: resumeId,
            //임시
          },
        ]);
        Swal.fire({
          icon: "success",
          title: "북마크가 추가되었습니다.",
          confirmButtonText: "확인",
        });
      }
    } catch {
      Swal.fire({
        icon: "error",
        title: "북마크 상태를 변경할 수 없습니다. 다시 시도해주세요.",
        confirmButtonText: "확인",
      });
    }
  };

  const handleAiFeedback = async () => {
    setLoading(true);
    try {
      const aiFeedback = await postAiFeedback(resumeId);
      setFeedbackPoints((prev) => [...prev, ...aiFeedback.feedbacks]);
    } catch {
      setError("Failed to retrieve AI feedback.");
    } finally {
      setLoading(false);
    }
  };

  const addFeedbackPoint = async (point: Omit<AddFeedbackPoint, "id">) => {
    try {
      if (
        !point.content ||
        point.xCoordinate === undefined ||
        point.yCoordinate === undefined
      ) {
        setError("All fields are required to add a feedback point.");
        return;
      }
      setLoading(true);
      const newPoint: AddFeedbackPoint = { ...point, pageNumber: 1 };
      await addFeedbackApi(resumeId, newPoint);
      const updatedData = await getResumeApi(resumeId);
      setFeedbackPoints(updatedData.feedbackResponses);
    } catch {
      setError("Failed to add feedback point. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  const deleteFeedbackPoint = async (feedbackId: number) => {
    try {
      setLoading(true);
      await deleteFeedbackApi(resumeId, feedbackId);
      setFeedbackPoints((prev) =>
        prev.filter((item) => item.id !== feedbackId)
      );
    } catch {
      setError("Failed to delete feedback point. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  const editFeedbackPoint = (updatedItem: AddFeedbackPoint) => {
    console.log("Edit feedback point: ", updatedItem);
  };

  if (loading) return <LoadingSpinner />;
  if (error) return <ErrorMessage message={error} />;
  if (!resumeData) return <div>No resume data available.</div>;

  const bookmarked = isBookmarked(resumeId);

  return (
    <div className="flex flex-col flex-grow ">
      <Layout
        sidebar={
          <div className="flex flex-col justify-between bg-white p-2 mt-10">
            <button
              onClick={toggleBookmark}
              className={`flex items-center px-6 py-3 rounded-lg ${
                bookmarked
                  ? "bg-yellow-100 text-yellow-900"
                  : "text-gray-500 hover:bg-gray-50"
              }`}
            >
              {bookmarked ? (
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
            <ResumeOverview
              userName={resumeData.userName}
              position={resumeData.position}
              career={resumeData.career}
              techStackNames={resumeData.techStackNames}
              fileUrl={resumeData.fileUrl}
              isLoading={loading}
            />
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
          laterResumeId={resumeData.laterResumeId}
          previousResumeId={resumeData.previousResumeId}
        />
      </Layout>
    </div>
  );
}

export default ResumeFeedbackPage;
