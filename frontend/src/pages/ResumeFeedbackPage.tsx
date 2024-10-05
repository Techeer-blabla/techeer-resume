import { useEffect, useState } from "react";
import { ResumeData } from "../types";
import { getResumeApi } from "../api/getResumeApi.ts";
import ResumeOverviewSection from "../components/resumeoverview/ResumeOverviewSection.tsx";
import CommentSection from "../components/comment/CommentSection.tsx";
import LoadingSpinner from "../components/UI/LoadingSpinner";
import ErrorMessage from "../components/UI/ErrorMessage";

function ResumeFeedbackPage({ resumeId = 1 }: { resumeId: number }) {
  const [resumeData, setResumeData] = useState<ResumeData | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        setError(null);
        const data = await getResumeApi(resumeId);
        setResumeData(data);
      } catch (error) {
        console.error("Failed to fetch resume data", error);
        setError("Failed to fetch resume data. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [resumeId]);

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
    <div>
      <ResumeOverviewSection resumeId={resumeId} />
      <CommentSection
        comments={resumeData.feedbacks}
        addComment={(text) => {
          // Placeholder function for adding a comment
          console.log("Add comment:", text);
        }}
        deleteCommentItem={(id) => {
          // Placeholder function for deleting a comment
          console.log("Delete comment with id:", id);
        }}
        editCommentItem={(item) => {
          // Placeholder function for editing a comment
          console.log("Edit comment:", item);
        }}
        hoveredCommentId={null}
        setHoveredCommentId={() => {
          // Placeholder function for setting hovered comment
        }}
      />
    </div>
  );
}

export default ResumeFeedbackPage;
