import { Worker } from "@react-pdf-viewer/core";
import { Viewer } from "@react-pdf-viewer/core";
import "@react-pdf-viewer/core/lib/styles/index.css";

{
  /* <Viewer fileUrl="/path/to/document.pdf" />; */
}

function PDFTest() {
  return (
    <div>
      <h2>PDF Viewer</h2>

      <Worker workerUrl="https://unpkg.com/pdfjs-dist@3.4.120/build/pdf.worker.min.js">
        {/* 뷰어 컴포넌트를 여기에 배치 */}
        <div
          style={{ border: "1px solid rgba(0, 0, 0, 0.3)", height: "750px" }}
        >
          <Viewer fileUrl="https://rexume.s3.ap-northeast-2.amazonaws.com/resume/a10d20c9-3_%E1%84%8C%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%B3%E1%86%BC%E1%84%86%E1%85%A7%E1%86%BC%E1%84%89%E1%85%A5%20%E1%84%8E%E1%85%AC%E1%84%89%E1%85%B5%E1%86%AB.pdf" />
        </div>
      </Worker>
    </div>
  );
}

export default PDFTest;
