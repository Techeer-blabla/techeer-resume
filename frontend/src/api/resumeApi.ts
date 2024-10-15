import { formAxios, jsonAxios } from "./axios.config.ts";

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

//이력서 검색
export const searchResume = async (searchName: string) => {
  try {
    const response = await jsonAxios.get(`search?user_name=${searchName}`);
    console.log("api: ", response);
    return response.data;
  } catch (error) {
    console.log("이력서 검색 오류", error);
    throw error;
  }
};
