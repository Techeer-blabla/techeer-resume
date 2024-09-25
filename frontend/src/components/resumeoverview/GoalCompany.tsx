import Tag from "../tag/Tag.tsx";
import ResumeOverviewSection from "./ResumeOverviewSection";

function GoalCompany() {
  return (
    <ResumeOverviewSection title="목표하는 회사는">
      <Tag text="금융권" />
      <Tag text="대기업" />
    </ResumeOverviewSection>
  );
}

export default GoalCompany;
