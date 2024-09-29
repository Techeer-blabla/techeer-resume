import React from "react";
import Layout from "../components/Layout/Layout";
import MainContainer from "../components/resumeoverview/MainContainer";
import ResumeOverview from "../components/resumeoverview/ResumeOverview";
import CommentSection from "../components/comment/CommentSection";

function ResumeFeedbackPage(): React.ReactElement {
  return (
    <Layout
      sidebar={
        <div className="flex flex-col h-full bg-white p-2">
          {/* Resume Overview: 크기 축소 및 테두리 제거 */}
          <ResumeOverview />

          {/* Comment Section: 스크롤 가능, 마진 조정 */}
          <div className="flex-grow overflow-y-auto mt-2">
            <CommentSection />
          </div>
        </div>
      }
    >
      <MainContainer />
    </Layout>
  );
}

export default ResumeFeedbackPage;
