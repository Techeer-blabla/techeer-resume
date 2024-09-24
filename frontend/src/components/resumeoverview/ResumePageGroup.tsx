import ResumePage from "./ResumePage";

type ResumePageGroupProps = {
  pages: number; // Number of pages to render
};

function ResumePageGroup({ pages }: ResumePageGroupProps) {
  const pageArray = Array.from({ length: pages }, (_, index) => index + 1);

  return (
    <div className="w-full h-auto mx-auto">
      {pageArray.map((pageNumber) => (
        <ResumePage key={pageNumber} pageNumber={pageNumber} />
      ))}
    </div>
  );
}

export default ResumePageGroup;
