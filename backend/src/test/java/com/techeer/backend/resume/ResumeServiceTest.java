package com.techeer.backend.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import com.techeer.backend.api.resume.controller.ResumeController;
import com.techeer.backend.api.resume.service.ResumeService;

@WebMvcTest(ResumeController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ResumeServiceTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ResumeService resumeService;

	// @Autowired
	// private Gson gson;

	// @Test
	// @DisplayName("POST 이력서 등록 컨트롤러 로직 확인")
	// public void postResume() throws Exception {
	// 	CreateResumeRequest request = new CreateResumeRequest();
	// 	String requestJson = gson.toJson(request);
	//
	// 	ResumeResponse resumeResponse =
	// 		new ResumeResponse();
	//
	// }

}
