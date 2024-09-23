import { useEffect, useRef, useState } from "react";
import noUiSlider from "nouislider";
import "../styles/nouislider.css";

type FileUploadProps = {
  id: string;
};

const FileUpload = ({ id }: FileUploadProps) => {
  return (
    <label
      htmlFor={id}
      className="bg-white text-gray-500 font-semibold text-base rounded w-[40rem] h-[18rem] flex flex-col items-center justify-center cursor-pointer border-2 border-gray-300 border-dashed mx-auto font-sans mt-[2rem]"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        className="w-[7rem] mb-2 fill-gray-500"
        viewBox="0 0 32 32"
      >
        <path
          d="M23.75 11.044a7.99 7.99 0 0 0-15.5-.009A8 8 0 0 0 9 27h3a1 1 0 0 0 0-2H9a6 6 0 0 1-.035-12 1.038 1.038 0 0 0 1.1-.854 5.991 5.991 0 0 1 11.862 0A1.08 1.08 0 0 0 23 13a6 6 0 0 1 0 12h-3a1 1 0 0 0 0 2h3a8 8 0 0 0 .75-15.956z"
          fill="#C5C5C5"
        />
        <path
          d="M20.293 19.707a1 1 0 0 0 1.414-1.414l-5-5a1 1 0 0 0-1.414 0l-5 5a1 1 0 0 0 1.414 1.414L15 16.414V29a1 1 0 0 0 2 0V16.414z"
          fill="#C5C5C5"
        />
      </svg>
      Upload file
      <input type="file" id={id} className="hidden" />
      <p className="text-xs font-medium text-gray-400 mt-2">
        PNG, JPG, SVG, WEBP, and GIF are Allowed.
      </p>
    </label>
  );
};

// 슬라이더 컴포넌트
const Slider = () => {
  const sliderRef = useRef<HTMLDivElement | null>(null);
  const [installCount, setInstallCount] = useState<{
    min: number;
    max: number;
  }>({ min: 0, max: 10 });

  const experienceLabels = [
    "신입",
    "1년",
    "2년",
    "3년",
    "4년",
    "5년",
    "6년",
    "7년",
    "8년",
    "9년",
    "10년",
  ];

  useEffect(() => {
    if (sliderRef.current) {
      const sliderElement = sliderRef.current;

      noUiSlider.create(sliderElement, {
        start: [installCount.min, installCount.max],
        connect: true,
        step: 1,
        range: {
          min: 0,
          max: 10,
        },
      });

      sliderElement.noUiSlider?.on("update", (values: (string | number)[]) => {
        const minValue = Math.round(Number(values[0]));
        const maxValue = Math.round(Number(values[1]));
        setInstallCount({ min: minValue, max: maxValue });
      });

      return () => {
        sliderElement.noUiSlider?.destroy();
      };
    }
  }, []);

  return (
    <div className="w-full p-2 flex flex-col">
      <div className="flex mb-2 text-gray-700 text-lg justify-center">
        <span>{experienceLabels[installCount.min]}</span>
        <span className="mx-2">~</span>
        <span>{experienceLabels[installCount.max]}</span>
      </div>
      <div ref={sliderRef} className="install-slider"></div>
    </div>
  );
};

// 텍스트 배열
const positions = [
  "프론트엔드",
  "백엔드",
  "풀스택",
  "DevOps",
  "게임",
  "퍼블리셔",
  "머신러닝/AI",
  "앱 (iOS, Android)",
  "데이터",
];

const PositionSVG = ({
  text,
  isSelected,
  onClick,
}: {
  text: string;
  isSelected: boolean;
  onClick: () => void;
}) => {
  return (
    <div
      className={`flex justify-center cursor-pointer ${isSelected ? "border-2 border-blue-500 rounded-lg" : ""}`}
      onClick={onClick}
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="121"
        height="33"
        viewBox="0 0 121 33"
        fill="none"
        className="flex-shrink-0"
      >
        <rect width="121" height="32.4206" rx="5" fill="#F3F3F3" />
        <text
          x="50%"
          y="50%"
          textAnchor="middle"
          fill="#040404"
          fontFamily="pretendard"
          fontSize="16"
          fontWeight="400"
          dy=".35em"
        >
          {text}
        </text>
      </svg>
    </div>
  );
};
const educations = ["전공자", "비전공자"];

const EducationSVG = ({
  text,
  isSelected,
  onClick,
}: {
  text: string;
  isSelected: boolean;
  onClick: () => void;
}) => {
  return (
    <div
      className={`flex justify-center cursor-pointer ${isSelected ? "border-2 border-blue-500 rounded-lg" : ""}`}
      onClick={onClick}
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="121"
        height="33"
        viewBox="0 0 121 33"
        fill="none"
        className="flex-shrink-0"
      >
        <rect width="121" height="32.4206" rx="5" fill="#F3F3F3" />
        <text
          x="50%"
          y="50%"
          textAnchor="middle"
          fill="#040404"
          fontFamily="pretendard"
          fontSize="16"
          fontWeight="400"
          dy=".35em"
        >
          {text}
        </text>
      </svg>
    </div>
  );
};
const companies = [
  "IT대기업",
  "스타트업",
  "서비스",
  "SI",
  "금융권",
  "제조업",
  "핀테크",
];

const CompanySVG = ({
  text,
  isSelected,
  onClick,
}: {
  text: string;
  isSelected: boolean;
  onClick: () => void;
}) => {
  return (
    <div
      className={`flex justify-center cursor-pointer ${isSelected ? "border-2 border-blue-500 rounded-lg" : ""}`}
      onClick={onClick}
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="121"
        height="33"
        viewBox="0 0 121 33"
        fill="none"
        className="flex-shrink-0"
      >
        <rect width="121" height="32.4206" rx="5" fill="#F3F3F3" />
        <text
          x="50%"
          y="50%"
          textAnchor="middle"
          fill="#040404"
          fontFamily="pretendard"
          fontSize="16"
          fontWeight="400"
          dy=".35em"
        >
          {text}
        </text>
      </svg>
    </div>
  );
};
const stacks = [
  "Java",
  "C++",
  "Linux",
  "Javascript",
  "Mysql",
  "AWS",
  "React",
  "Python",
];

const StackSVG = ({
  text,
  isSelected,
  onClick,
}: {
  text: string;
  isSelected: boolean;
  onClick: () => void;
}) => {
  return (
    <div
      className={`flex justify-center cursor-pointer ${isSelected ? "border-2 border-blue-500 rounded-lg" : ""}`}
      onClick={onClick}
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="121"
        height="33"
        viewBox="0 0 121 33"
        fill="none"
        className="flex-shrink-0"
      >
        <rect width="121" height="32.4206" rx="5" fill="#F3F3F3" />
        <text
          x="50%"
          y="50%"
          textAnchor="middle"
          fill="#040404"
          fontFamily="pretendard"
          fontSize="16"
          fontWeight="400"
          dy=".35em"
        >
          {text}
        </text>
      </svg>
    </div>
  );
};
export default function Upload() {
  const [selectedPosition, setSelectedPosition] = useState<string | null>(null);

  const handlePositionClick = (position: string) => {
    setSelectedPosition(position);
  };

  const [selectedEducation, setSelectedEducation] = useState<string | null>(
    null
  );

  const handleEducationClick = (education: string) => {
    setSelectedEducation(education);
  };

  const [selectedCompany, setSelectedCompany] = useState<string | null>(null);

  const handleCompanyClick = (company: string) => {
    setSelectedCompany(company);
  };

  const [selectedStack, setSelectedStack] = useState<string | null>(null);

  const handleStackClick = (stack: string) => {
    setSelectedStack(stack);
  };

  return (
    <div className="w-full flex mt-[2rem] ml-[4rem] space-x-4">
      {/* 왼쪽 섹션: 파일 업로드 박스 */}
      <div className="w-[50rem] h-[42rem] flex-shrink-0 rounded-[0.3125rem] border border-[#CEDAF9] bg-[#F8FAFF]">
        <div className="flex-shrink-0 ml-[5rem] mt-[4rem] relative">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="7rem"
            height="7rem"
            viewBox="0 0 114 114"
            className="fill-[#EFEFEF]"
          >
            <circle cx="57" cy="57" r="57" />
            <path
              d="M60.7671 22.5488V40.7682C60.7671 45.3471 56.1425 49.059 50.4378 49.059H11.0969C5.39219 49.059 0.767578 45.3471 0.767578 40.7682V13.1832C0.767578 8.60427 5.39219 4.89233 11.0969 4.89233H24.9332C28.1553 4.89233 30.7674 6.98889 30.7674 9.57513C30.8196 12.1313 33.4164 14.1815 36.6015 14.1812H50.4378C53.1939 14.1811 55.8359 15.0651 57.7758 16.6366C59.7157 18.208 60.7927 20.3366 60.7671 22.5488Z"
              fill="#0060FF"
              transform="translate(25, 28)"
            />
          </svg>
        </div>
        <div className="ml-[5rem] mt-[1rem]">
          <div className="w-[15rem] h-[2rem] flex-shrink-0 text-black font-pretendard text-[1.5rem] font-bold">
            첨부파일 업로드
          </div>
          <div className="text-black font-pretendard text-[1.1rem] font-normal">
            여기에 파일을 끌어다 놓으세요
          </div>
        </div>
        <FileUpload id="uploadFile1" />
        <div className="flex justify-center mt-8 ml-[24.5rem] space-x-2">
          <button className="w-[7.5rem] h-[3rem] flex-shrink-0 text-[#C5C5C5] font-pretendard text-[1.25rem] font-semibold bg-transparent border border-[#C5C5C5] rounded">
            Cancel
          </button>
          <button className="w-[7.5rem] h-[3rem] flex-shrink-0 text-white font-pretendard text-[1.25rem] font-semibold bg-[#0060FF] rounded">
            Upload Files
          </button>
        </div>
      </div>

      {/* 오른쪽 섹션: 텍스트 박스 및 슬라이더 */}
      <div className="flex flex-col justify-start text-black font-pretendard text-[1rem] font-normal">
        <div className="ml-[1rem]"># 포지션</div>
        <div className="grid grid-cols-3 gap-4 mt-[1rem] ml-[1rem]">
          {positions.map((position, index) => (
            <PositionSVG
              key={index}
              text={position}
              isSelected={selectedPosition === position}
              onClick={() => handlePositionClick(position)}
            />
          ))}
        </div>
        <div className="flex flex-col justify-start text-black font-pretendard text-[1rem] font-normal mt-[1rem]">
          <div className="ml-[1rem]"># 경력</div>
        </div>
        {/* 슬라이더 컴포넌트 */}
        <div className="ml-[1rem] mt-[1rem]">
          <Slider />
        </div>
        <div className="flex flex-col justify-start text-black font-pretendard text-[1rem] font-normal mt-[1rem]">
          <div className="ml-[1rem]"># 학력</div>
        </div>
        <div className="grid grid-cols-3 gap-4 mt-[1rem] ml-[1rem]">
          {educations.map((education, index) => (
            <EducationSVG
              key={index}
              text={education}
              isSelected={selectedEducation === education}
              onClick={() => handleEducationClick(education)}
            />
          ))}
        </div>
        <div className="flex flex-col justify-start text-black font-pretendard text-[1rem] font-normal mt-[1rem]">
          <div className="ml-[1rem]"># 지원회사</div>
        </div>
        <div className="grid grid-cols-3 gap-4 mt-[1rem] ml-[1rem]">
          {companies.map((company, index) => (
            <CompanySVG
              key={index}
              text={company}
              isSelected={selectedCompany === company}
              onClick={() => handleCompanyClick(company)}
            />
          ))}
        </div>
        <div className="flex flex-col justify-start text-black font-pretendard text-[1rem] font-normal mt-[1rem]">
          <div className="ml-[1rem]"># 기술스택</div>
        </div>
        <div className="grid grid-cols-3 gap-4 mt-[1rem] ml-[1rem]">
          {stacks.map((stack, index) => (
            <StackSVG
              key={index}
              text={stack}
              isSelected={selectedStack === stack}
              onClick={() => handleStackClick(stack)}
            />
          ))}
        </div>
      </div>
    </div>
  );
}
