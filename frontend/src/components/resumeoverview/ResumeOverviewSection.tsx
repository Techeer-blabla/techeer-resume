import { useEffect, useState } from "react";
import { ResumeData } from "../../types.ts";
import { getResumeApi } from "../../api/feedbackApi.ts";
import useResumeStore from "../../store/ResumeStore.ts";

function ResumeOverviewSection() {
  const [resumeData, setResumeData] = useState<ResumeData | null>(null);
  const { resumeId } = useResumeStore();

  useEffect(() => {
    if (resumeId === undefined) {
      console.error("Invalid resumeId: undefined");
      return;
    }

    const fetchResumeData = async () => {
      try {
        const data = await getResumeApi(resumeId);
        setResumeData(data);
      } catch (error) {
        console.error("Failed to load resume data", error);
      }
    };

    fetchResumeData();
  }, [resumeId]);

  if (!resumeData) {
    return <div>Loading...</div>;
  }

  return (
    <section className="bg-white rounded-lg px-6 my-4">
      <h2 className="text-lg font-semibold text-gray-900 mb-4 text-left font-pretendard">
        {resumeData.userName}'s Resume Overview
      </h2>
      <div className="flex flex-wrap justify-start space-y-4 sm:space-y-0 sm:space-x-4">
        <div>
          <p className="font-medium">Position:</p>
          <p>{resumeData.position}</p>
        </div>
        <div>
          <p className="font-medium">Career:</p>
          <p>{resumeData.career} years</p>
        </div>
        <div>
          <p className="font-medium">Tech Stack:</p>
          <p>{resumeData.techStack}</p>
        </div>
        <div>
          <a
            href={resumeData.fileUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-500 underline"
          >
            Download Resume
          </a>
        </div>
      </div>
    </section>
  );
}

export default ResumeOverviewSection;
