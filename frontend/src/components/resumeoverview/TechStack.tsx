import Tag from "../tag/Tag.tsx";
import ResumeOverviewSection from "./ResumeOverviewSection";

function TechStackSection() {
  return (
    <ResumeOverviewSection title="기술 스택">
      <Tag text="React" />
      <Tag text="Tailwind CSS" />
      <Tag text="JavaScript" />
    </ResumeOverviewSection>
  );
}

export default TechStackSection;
