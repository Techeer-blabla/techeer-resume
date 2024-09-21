import { ReactNode } from "react";

type ResumeOverviewSectionProps = {
  title: string;
  children: ReactNode;
};

function ResumeOverviewSection({
  title,
  children,
}: ResumeOverviewSectionProps) {
  return (
    <section className="bg-white rounded-lg px-6 my-4">
      {" "}
      {/* Removed shadow and updated padding */}
      <h2 className="text-lg font-semibold text-gray-900 mb-4 text-left font-pretendard">
        {/* Reduced font size and applied Pretendard font */}
        {title}
      </h2>
      <div className="flex flex-wrap justify-start space-y-4 sm:space-y-0 sm:space-x-4">
        {/* Updated from justify-around to justify-start */}
        {children}
      </div>
    </section>
  );
}

export default ResumeOverviewSection;
