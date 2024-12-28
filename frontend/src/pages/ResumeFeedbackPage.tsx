import { useEffect, useState } from "react";
import Layout from "../components/Layout/Layout";
// import MainContainer from "../components/resumeoverview/MainContainer";
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
import useResumeStore from "../store/ResumeStore.ts";
import { useParams } from "react-router-dom";

function ResumeFeedbackPage() {
  const [resumeData, setResumeData] = useState<ResumeData | null>(null); //이력서 데이터를 관리
  const [feedbackPoints, setFeedbackPoints] = useState<FeedbackPoint[]>([]); //피드백 데이터를 관리
  const [hoveredCommentId, setHoveredCommentId] = useState<number | null>(null); //마우스 호버시 코멘트 아이디를 관리
  const [loading, setLoading] = useState<boolean>(true); //로딩 상태를 관리
  const [error, setError] = useState<string | null>(null); //에러 상태를 관리
  const { resumeId, setResumeUrl } = useResumeStore(); //이력서 아이디와 이력서 URL을 관리 -> 이게 왜 필요한지 모르겠음
  const { id } = useParams(); // useParams로 URL의 id 파라미터 가져오기

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
        const data = await getResumeApi(Number(id)); //여기서 레쥬메 아이디로 관리를 해서 데이터를 불러옴
        setResumeData(data);
        console.log(data);
        setResumeUrl(data.fileUrl);
        setFeedbackPoints(data.feedbackResponses || []); // 관련된 설정들
      } catch (error) {
        console.error("Failed to fetch resume data", error);
        setError("Failed to fetch resume data. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

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
            <ResumeOverview
              userName={resumeData.userName}
              position={resumeData.position}
              career={resumeData.career}
              techStackNames={resumeData.techStackNames}
              fileUrl={resumeData.fileUrl}
              isLoading={loading}
            />
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
        children={undefined}
      >
        {/* <MainContainer
          feedbackPoints={feedbackPoints}
          addFeedbackPoint={addFeedbackPoint}
          deleteFeedbackPoint={deleteFeedbackPoint}
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
