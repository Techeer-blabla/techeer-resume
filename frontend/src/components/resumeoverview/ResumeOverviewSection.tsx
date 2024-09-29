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
    <section className="bg-white rounded px-4 my-2">
      <h2 className="text-md font-semibold text-gray-900 mb-3 text-left font-Pretendard">
        {title}
      </h2>
      <div className="flex flex-wrap justify-start space-y-3 sm:space-y-0 sm:space-x-3">
        {children}
      </div>
    </section>
  );
}

export default ResumeOverviewSection;
