import { useState } from "react";
import Slider from "@mui/material/Slider";
import { postResume } from "../api/resumeApi";

function Upload() {
  const [resume, setResume] = useState<File | null>(null);
  const [career, setCareer] = useState<number>(0);
  const [position, setPosition] = useState<string>("");
  const [applyingCompany, setApplyingCompany] = useState<string[]>([]);
  const [techStack, setTechStack] = useState<string[]>([]);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      setResume(event.target.files[0]);
      console.log("선택된 파일:", event.target.files[0].name); // 파일명 출력
    }
  };

  const handleCancel = () => {
    setResume(null);
  };

  const handleUpload = async () => {
    if (resume) {
      const createResumeReq = {
        username: "testuser",
        position: position,
        career: career,
        applying_company: applyingCompany,
        tech_stack: techStack,
      };

      try {
        const response = await postResume(resume, createResumeReq);
        console.log("업로드성공:", response);
      } catch (error) {
        console.error("업로드 에러:", error);
      }
    } else {
      alert("이력서 파일을 선택하세요");
    }
  };

  const FileUpload = ({ id }: { id: string }) => {
    // id를 props로 받음
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
        <input
          type="file"
          id={id}
          className="hidden"
          onChange={handleFileChange} // 파일 선택 시 이벤트 핸들러 추가
        />
        <p className="text-xs font-medium text-gray-400 mt-2">
          PDF is allowed.
        </p>
      </label>
    );
  };

  // 포지션, 학력, 회사, 스택 데이터
  const positions = [
    "FRONTEND",
    "BACKEND",
    "풀스택",
    "DEVOPS",
    "게임",
    "퍼블리셔",
    "머신러닝/AI",
    "ANDROID",
    "IOS",
    "데이터",
  ];
  const educations = ["전공자", "비전공자"];
  const companies = [
    "IT대기업",
    "스타트업",
    "서비스",
    "SI",
    "금융권",
    "제조업",
    "핀테크",
  ];
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

  // SVG 컴포넌트
  const PositionSVG = ({
    text,
    isSelected,
    onClick,
  }: {
    text: string;
    isSelected: boolean;
    onClick: () => void;
  }) => (
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

  const DirectInputTag = ({
    existingTags,
    onAdd,
  }: {
    existingTags: string[];
    onAdd: (newTag: string) => void;
  }) => {
    const [inputValue, setInputValue] = useState<string>("");

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      setInputValue(e.target.value);
    };

    const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
      if (
        e.key === "Enter" &&
        inputValue.trim() !== "" &&
        !existingTags.includes(inputValue.trim())
      ) {
        onAdd(inputValue.trim());
        setInputValue("");
      }
    };

    return (
      <div className="flex justify-center cursor-pointer">
        <input
          type="text"
          value={inputValue}
          onChange={handleInputChange}
          onKeyPress={handleKeyPress}
          className="w-[121px] h-[33px] bg-[#F3F3F3] flex items-center justify-center rounded-lg text-black text-center outline-none"
          placeholder="직접입력"
        />
      </div>
    );
  };

  const ExperienceSlider = ({
    value,
    onChange,
  }: {
    value: number;
    onChange: (newValue: number) => void;
  }) => {
    const marks = [
      { value: 0, label: "신입" },
      { value: 1, label: "1년" },
      { value: 2, label: "2년" },
      { value: 3, label: "3년" },
      { value: 4, label: "4년" },
      { value: 5, label: "5년" },
      { value: 6, label: "6년" },
      { value: 7, label: "7년" },
      { value: 8, label: "8년" },
      { value: 9, label: "9년" },
      { value: 10, label: "10년" },
    ];

    return (
      <Slider
        value={value}
        onChange={(_event, newValue) => onChange(newValue as number)} // Update with correct type
        aria-labelledby="experience-slider"
        step={1}
        marks={marks}
        min={0}
        max={10}
        valueLabelDisplay="auto"
        sx={{
          "& .MuiSlider-thumb": { color: "#007bff" },
          "& .MuiSlider-track": { color: "#007bff" },
          "& .MuiSlider-rail": { color: "#dddddd" },
        }}
      />
    );
  };

  const [selectedEducation, setSelectedEducation] = useState<string | null>(
    null
  );

  const handlePositionClick = (position: string) => setPosition(position);
  const handleEducationClick = (education: string) =>
    setSelectedEducation(education);
  const handleCompanyClick = (applyingCompany: string) =>
    setApplyingCompany((prevCompanies) => [...prevCompanies, applyingCompany]);

  const [positionTags, setPositionTags] = useState<string[]>(positions);
  const [stackTags, setStackTags] = useState<string[]>(stacks);
  const [educationTags, setEducationTags] = useState<string[]>(educations);
  const [companyTags, setCompanyTags] = useState<string[]>(companies);

  const handleStackClick = (techStack: string) =>
    setTechStack((prevStacks) => [...prevStacks, techStack]);
  const handleAddPosition = (newTag: string) =>
    setPositionTags([...positionTags, newTag]);
  const handleAddStack = (newTag: string) =>
    setStackTags([...stackTags, newTag]);
  const handleAddEducation = (newTag: string) =>
    setEducationTags([...educationTags, newTag]);
  const handleAddCompany = (newTag: string) =>
    setCompanyTags([...companyTags, newTag]);

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
        <div className="w-[40rem] flex items-center mt-[1rem] ml-[5rem]">
          {resume ? (
            <div className="text-gray-600 font-medium overflow-hidden text-ellipsis whitespace-nowrap flex-1">
              선택된 파일: {resume.name}
            </div>
          ) : (
            <div className="flex-1" />
          )}
          <button
            className="w-[7.5rem] h-[2.5rem] flex-shrink-0 text-[#C5C5C5] font-pretendard text-[1rem] font-semibold bg-transparent border border-[#C5C5C5] rounded"
            onClick={handleCancel}
          >
            Cancel
          </button>
        </div>
      </div>

      {/* 오른쪽 섹션 */}
      <div className="flex flex-col justify-start text-black font-pretendard text-[1rem] font-normal">
        <div className="ml-[1rem]"># 포지션</div>
        <div className="grid grid-cols-3 gap-2 mb-4">
          {positionTags.map((positionTag) => (
            <PositionSVG
              key={positionTag}
              text={positionTag}
              isSelected={position == positionTag}
              onClick={() => handlePositionClick(positionTag)}
            />
          ))}
          <DirectInputTag
            existingTags={positionTags}
            onAdd={handleAddPosition}
          />
        </div>

        {/* 스택 */}
        <div className="flex flex-col justify-start text-black font-pretendard text-[1rem] font-normal mt-[1rem]">
          <div className="ml-[1rem]"># 스택</div>
          <div className="grid grid-cols-3 gap-2 mb-4">
            {stackTags.map((stackTag) => (
              <PositionSVG
                key={stackTag}
                text={stackTag}
                isSelected={techStack.includes(stackTag)}
                onClick={() => handleStackClick(stackTag)}
              />
            ))}
            <DirectInputTag existingTags={stackTags} onAdd={handleAddStack} />
          </div>
        </div>

        {/* 경력 슬라이더 */}
        <div className="flex flex-col justify-start text-black font-pretendard text-[1rem] font-normal mt-[1rem]">
          <div className="ml-[1rem]"># 경력</div>
          <div className="ml-[1rem]">
            <ExperienceSlider value={career} onChange={setCareer} />
          </div>
        </div>

        {/* 학력 */}
        <div className="flex flex-col justify-start text-black font-pretendard text-[1rem] font-normal mt-[1rem]">
          <div className="ml-[1rem]"># 학력</div>
          <div className="grid grid-cols-3 gap-2 mb-4">
            {educationTags.map((education) => (
              <PositionSVG
                key={education}
                text={education}
                isSelected={selectedEducation === education}
                onClick={() => handleEducationClick(education)}
              />
            ))}
            <DirectInputTag
              existingTags={educationTags}
              onAdd={handleAddEducation}
            />
          </div>
        </div>

        {/* 회사 */}
        <div className="flex flex-col justify-start text-black font-pretendard text-[1rem] font-normal mt-[1rem]">
          <div className="ml-[1rem]"># 회사</div>
          <div className="grid grid-cols-3 gap-2 mb-4">
            {companyTags.map((company) => (
              <PositionSVG
                key={company}
                text={company}
                isSelected={applyingCompany.includes(company)}
                onClick={() => handleCompanyClick(company)}
              />
            ))}
            <DirectInputTag
              existingTags={companyTags}
              onAdd={handleAddCompany}
            />
          </div>
          {/* 최종 업로드 버튼 */}
          <div className="flex justify-end mt-8">
            <button
              className="w-[10rem] h-[3rem] flex-shrink-0 text-white font-pretendard text-[1rem] font-semibold bg-[#0060FF] rounded"
              onClick={handleUpload} // 업로드 버튼 클릭 시 이벤트 핸들러 추가
            >
              Submit Resume
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
export default Upload;
