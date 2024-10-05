import { useEffect, useState } from "react";
import { ResumeData } from "../../types.ts";
import { getResumeApi } from "../../api/getResumeApi.ts";

function ResumeOverviewSection({ resumeId = 1 }: { resumeId?: number }) {
  const [resumeData, setResumeData] = useState<ResumeData | null>(null);

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
    <div>
      <h1>{resumeData.user_name}'s Resume Overview</h1>
      <p>Position: {resumeData.position}</p>
      <p>Career: {resumeData.career} years</p>
      <p>Tech Stack: {resumeData.tech_stack.join(", ")}</p>
      <a href={resumeData.file_url} target="_blank" rel="noopener noreferrer">
        Download Resume
      </a>
    </div>
  );
}

export default ResumeOverviewSection;
