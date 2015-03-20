package github.namhokim.drive.controller.file;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("../test-servlet-context.xml")
public class FileUploadControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	@Inject
	private FileSystemResource fsResource;
	
	private MockMvc mockMvc;
	
	private File testFile;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@After
	public void TearDown() throws Exception {
		if (testFile !=null) testFile.delete();
	}
	
	@Test
	public final void testUpload() throws Exception {
		byte[] contentData = new byte[] { 0x25, 0x26, 0x27 };

		MockMultipartFile uploadFile = new MockMultipartFile("fileData", "testUpload.file", "text/plain", contentData);
		this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file").file(uploadFile))
					.andExpect(status().is3xxRedirection());
		
		File createdFile = new File(fsResource.getPath() + File.separator + "testUpload.file");
		assertThat(createdFile.exists(), is(true));
		createdFile.delete();
	}

}
