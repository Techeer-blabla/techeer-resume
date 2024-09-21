type ResumePageProps = {
  pageNumber: number;
};

function ResumePage({ pageNumber }: ResumePageProps) {
  return (
    <div className="w-full h-[903px] mx-auto mt-4 bg-[#bbb8b8] flex justify-center items-center">
      {/* Placeholder for PDF Image */}
      <p className="text-white">Resume PDF Page {pageNumber}</p>
    </div>
  );
}

export default ResumePage;
