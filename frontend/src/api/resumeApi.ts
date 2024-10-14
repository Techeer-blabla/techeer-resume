import { formAxios, jsonAxios } from "./axios.config.ts";

// 이력서 업로드 API
export const postResume = async (
  resume: File,
  createResumeReq: {
    username: string;
    position: string;
    career: number;
    applying_company: string[];
    tech_stack: string[];
  }
) => {
  try {
    const formData = new FormData();
    formData.append("resume_file", resume);
    formData.append("createResumeReq", JSON.stringify(createResumeReq));

    // API 호출
    const response = await formAxios.post(`/resumes`, formData, {});

    return response.data;
  } catch (error) {
    console.error("이력서 업로드 오류:", error);
    throw error;
  }
};

export const postFilter = async (filterData: {
  dto: {
    positions: string[];
    minCareer: number;
    maxCareer: number;
    techStacks: string[];
  };
  pageable: {
    page: number;
    size: number;
    sort: string[];
  };
}) => {
  try {
    const response = await jsonAxios.post(`/resumes/search`, filterData);

    return response.data;
  } catch (error) {
    console.error("포지션/경력 필터링 오류:", error);
    throw error;
  }
};
