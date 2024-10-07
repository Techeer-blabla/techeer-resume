import { formAxios } from "./axios.config.ts";

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
    // error handling
    console.error("이력서 업로드 오류:", error);
    throw error;
  }
};

export const getTag = async (
  dto: {
    positions: string[];
    minCareer: number;
    maxCareer: number;
    techStacks: string[];
  },
  pageable: {
    page: number;
    size: number;
    sort: string[];
  }
) => {
  try {
    const response = await formAxios.get("/resumes/search", {
      params: {
        positions: dto.positions,
        minCareer: dto.minCareer,
        maxCareer: dto.maxCareer,
        techStacks: dto.techStacks,
        page: pageable.page,
        size: pageable.size,
        sort: pageable.sort.join(","), // 정렬 기준을 배열로 받을 경우 콤마로 구분하여 전달
      },
    });

    return response.data;
  } catch (error) {
    console.error("태그 검색 오류:", error);
    throw error;
  }
};
