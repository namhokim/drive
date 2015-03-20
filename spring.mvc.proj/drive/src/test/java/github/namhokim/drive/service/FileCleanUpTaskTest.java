package github.namhokim.drive.service;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import github.namhokim.drive.helper.DirectoryHelper;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.util.ReflectionTestUtils;

public class FileCleanUpTaskTest {
	
	File tempDir;
	FileCleanUpTask task;	// test target

	@Before
	public void setUp() throws Exception {
		 task = new FileCleanUpTask();
		 tempDir = DirectoryHelper.createTempDirectory("test");
	}
	
	@After
	public void tearDown() throws Exception {
		DirectoryHelper.deleteDir(tempDir);
	}
	

	@Test
	public final void testRemoveExpiredFiles() throws IOException {

		File file = DirectoryHelper.createTempFile(tempDir, "willBeRemove");
		ReflectionTestUtils.setField(task, "fsResource", new FileSystemResource(tempDir.getAbsolutePath()));
		ReflectionTestUtils.invokeMethod(task, "setCurrentTimeNow");
		ReflectionTestUtils.setField(task, "criticalTime", -1000);	// should be future (1 second before)
		
		assertThat(file.exists(), is(true));
		task.removeExpiredFiles();				// action
		assertThat(file.exists(), is(false));
	}
	
	@Test
	public final void testNotRemoveExpiredFiles() throws IOException {

		File file = DirectoryHelper.createTempFile(tempDir, "willBeRemain");
		ReflectionTestUtils.setField(task, "fsResource", new FileSystemResource(tempDir.getAbsolutePath()));
		ReflectionTestUtils.invokeMethod(task, "setCurrentTimeNow");
		ReflectionTestUtils.setField(task, "criticalTime", 1000);	// should be future (1 second after)
		
		assertThat(file.exists(), is(true));
		task.removeExpiredFiles();				// action
		assertThat(file.exists(), is(true));
	}

	@Test
	public final void testAccept_timeOverFileReturnTrue() throws IOException {

		File file = DirectoryHelper.createTempFile(tempDir, "timeOver");
		ReflectionTestUtils.invokeMethod(task, "setCurrentTimeNow");
		ReflectionTestUtils.setField(task, "criticalTime", -1000);	// should be future (1 second before)
		assertThat(task.accept(file), is(true));
	}
	
	@Test
	public final void testAccept_remainTimeFileReturnFalse() throws IOException {

		File file = DirectoryHelper.createTempFile(tempDir, "reaminTime");
		ReflectionTestUtils.invokeMethod(task, "setCurrentTimeNow");
		ReflectionTestUtils.setField(task, "criticalTime", 1000);	// should be future (1 second after)
		assertThat(task.accept(file), is(false));
	}

}
