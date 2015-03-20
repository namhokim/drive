package github.namhokim.drive.controller.page;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("../test-servlet-context.xml")
public class HomeControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	@Inject
	private FileSystemResource fsResource;
	
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public final void testHome() throws Exception {
		this.mockMvc.perform(get("/"))
					.andExpect(status().isOk())
					.andExpect(view().name("home"))
					.andExpect(model().size(0));
		
		// test for execute createRootDriveForContentPath()
		File theDir = new File(fsResource.getPath());
		assertTrue(theDir.exists());
	}

}
