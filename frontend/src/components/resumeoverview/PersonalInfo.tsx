import Tag from "../tag/Tag.tsx";
import ResumeOverviewSection from "./ResumeOverviewSection";

function PersonalInfo() {
  return (
    <ResumeOverviewSection title="나는 이런 사람이에요!">
      <Tag text="프론트엔드" />
      <Tag text="신입" />
      <Tag text="전공자" />
    </ResumeOverviewSection>
  );
}

export default PersonalInfo;
