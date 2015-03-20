package github.namhokim.drive.controller.file;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;

import github.namhokim.drive.helper.DirectoryHelper;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("../test-servlet-context.xml")
public class FileDownloadControllerTest {

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
	public final void testDownload_FileNotExist_404() throws Exception {
		this.mockMvc.perform(get("/file").param("filename", "notExist.file"))
					.andExpect(status().isNotFound());
	}
	
	@Test
	public final void testDownload_FileExistEmptyFile_200() throws Exception {
		byte[] emptyData = new byte[] {};
		testFile = DirectoryHelper.createTempFile(fsResource.getFile(), "emptyTestFile");

		this.mockMvc.perform(get("/file").param("filename", testFile.getName()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
					.andExpect(content().bytes(emptyData));
	}
	
	@Test
	public final void testDownload_FileExist_200() throws Exception {
		byte[] contentData = new byte[] { 0x20, 0x21, 0x23 };
		testFile = DirectoryHelper.createTempFile(fsResource.getFile(), "contentTestFile", contentData);

		this.mockMvc.perform(get("/file").param("filename", testFile.getName()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
					.andExpect(content().bytes(contentData));
	}

}
