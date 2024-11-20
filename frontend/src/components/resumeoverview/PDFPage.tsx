import { Worker, Viewer } from "@react-pdf-viewer/core";
import "@react-pdf-viewer/core/lib/styles/index.css";
import { defaultLayoutPlugin } from "@react-pdf-viewer/default-layout";
import "@react-pdf-viewer/default-layout/lib/styles/index.css";
import { pageNavigationPlugin } from "@react-pdf-viewer/page-navigation";

const PDFPage = ({ pdfUrl }: { pdfUrl: string }) => {
  const defaultLayout = defaultLayoutPlugin();
  const workerUrl = `https://unpkg.com/pdfjs-dist@3.4.120/build/pdf.worker.min.js`;
  const pageNavigationPluginInstance = pageNavigationPlugin();
  const { CurrentPageLabel } = pageNavigationPluginInstance;

  console.log("pdf 링크: ", pdfUrl);

  return (
    <div style={{ height: "100vh" }}>
      <Worker workerUrl={workerUrl}>
        <Viewer fileUrl={pdfUrl} plugins={[defaultLayout]} />
      </Worker>
    </div>
  );
};

export default PDFPage;
