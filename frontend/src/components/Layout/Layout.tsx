import React from "react";
import Footer from "../resumeoverview/Footer.tsx";

interface LayoutProps {
  children: React.ReactNode;
  sidebar: React.ReactNode;
}

function Layout({ children, sidebar }: LayoutProps): React.ReactElement {
  return (
    <div>
      <div className="flex flex-row w-full h-screen">
        {/* Left Column: Main Content */}
        <div className="w-full md:w-2/3 h-full overflow-y-auto">
          {children}
          <Footer />
        </div>

        {/* Right Column: Sidebar */}
        <div className="w-1/3 h-full flex flex-col p-4 overflow-hidden">
          {sidebar}
        </div>
      </div>
    </div>
  );
}

export default Layout;
