import { useState } from "react";
import { Bookmark, FileText } from "lucide-react";
import Navbar from "../components/common/Navbar.tsx";
import BookmarkTap from "../components/MyInfoPage/BookmarkTap.tsx";
import ResumeTap from "../components/MyInfoPage/ResumeTap.tsx";
import UserInfo from "../components/MyInfoPage/UserInfo.tsx";

function MyInfoPage() {
  const [activeTab, setActiveTab] = useState<"resume" | "bookmark">("resume");

  const renderContent = () => {
    switch (activeTab) {
      case "resume":
        return <ResumeTap />;
      case "bookmark":
        return <BookmarkTap />;
      default:
        return null;
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="pt-5 pb-2 bg-white">
        <Navbar />
      </div>

      <div className="p-5 mt-5">
        <div className="p-5 mt-5 max-w-6xl mx-auto px-4 py-8 mt-10">
          {/* 유저 정보 */}
          <div className="flex gap-8">
            <UserInfo />

            <div className="flex-1">
              <div className="flex space-x-1 -mb-1">
                <button
                  onClick={() => setActiveTab("resume")}
                  className={`flex items-center px-6 py-4 rounded-t-lg ${
                    activeTab === "resume"
                      ? "bg-white text-gray-900"
                      : "text-gray-500 hover:bg-gray-100"
                  }`}
                >
                  <FileText className="w-5 h-5 mr-2" />
                  나의 이력서
                </button>

                <button
                  onClick={() => setActiveTab("bookmark")}
                  className={`flex items-center px-6 py-4 rounded-t-lg ${
                    activeTab === "bookmark"
                      ? "bg-white text-gray-900"
                      : "text-gray-500 hover:bg-gray-100"
                  }`}
                >
                  <Bookmark className="w-5 h-5 mr-2" />
                  북마크
                </button>
              </div>
              {renderContent()}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MyInfoPage;
