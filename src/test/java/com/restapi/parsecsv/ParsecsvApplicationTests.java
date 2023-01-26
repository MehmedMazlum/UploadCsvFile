package com.restapi.parsecsv;

import com.restapi.parsecsv.controller.UploadController;
import com.restapi.parsecsv.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static java.nio.file.Paths.get;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebServiceServerTest
class ParsecsvApplicationTests {

	@Mock
	RecordRepository repository;

	@Autowired
	private MockMvc mockMvc;


	@Mock
	private UploadController uploadController;

/*	@Autowired
	private MockWebServiceClient client;

	@Test
	void returnsListOfBooks() {

		final var request = "<js:getBooksRequest"+
				"   xmlns:js=\"http://jschmitz.dev/spring-boot-webserviceservertest/webservice/model\""+
				"/>";

		this.client
				.sendRequest(withPayload(new StringSource(request)))
				.andExpect(noFault());
	}*/

/*	@Test
	public void contextLoads() throws Exception {
		assertThat(uploadController).isNotNull();
	}*/

/*	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.uploadController.uploadCSVFile(get("/"))..andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, World")));
	}*/

}
