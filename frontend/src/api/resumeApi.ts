import axios, { AxiosError } from "axios";

const BASE_URL = "http://localhost:8080/api/v1/";

export const postResumeFile = async (resumeId: string, resume: File) => {
  try {
    const formData = new FormData();
    formData.append("resume_file", resume);
    formData.append("createResumeReq ");

    const response = await axios.post(`${BASE_URL}/${resumeId}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data;
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    if (axiosError.response) {
      return axiosError.response.data;
    } else {
      throw new Error("파일업로드 에러");
    }
  }
};
