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
} from "../api/feedbackApi.ts";
import { AddFeedbackPoint, FeedbackPoint, ResumeData } from "../types.ts";
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

  // 피드백 점 추가
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

  // 댓글 및 피드백 점 삭제 (로그만 출력)
  // 피드백 점 삭제
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

  // 댓글 및 피드백 점 수정 (로그만 출력)
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
                setHoveredCommentId={setHoveredCommentId}
              />
            </div>
          </div>
        }
      >
        {/* Main Content */}
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
