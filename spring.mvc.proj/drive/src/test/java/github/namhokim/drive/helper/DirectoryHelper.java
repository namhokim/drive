package github.namhokim.drive.helper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DirectoryHelper {
	
	/**
	 * Create Temporary Directory for Test
	 * @param tempDirName Temporary Directory Name
	 * @return instance of <code>{@link java.io.File}</code>
	 * @throws  IOException  If a file could not be created
	 */
	public static File createTempDirectory(String tempDirName) throws IOException {
		File tmp = File.createTempFile("tmp", "file");
		String tmpDir = tmp.getAbsoluteFile().getParentFile().getAbsolutePath();
		File testTmpDir = new File(tmpDir + File.separator + tempDirName);
		if (testTmpDir.exists() == false) {
			assertThat(testTmpDir.mkdirs(), is(true));
		}
		return testTmpDir;
	}

	/**
	 * Remove all sub-directory
	 * @param dir Target
	 * @return is it was succuss or not
	 */
	public static boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    return dir.delete(); // The directory is empty now and can be deleted.
	}

	/**
	 * Creates an empty file to the specific temporary directory
	 * @param tempDir specific temporary directory
	 * @param prefix prefix of filename
	 * @return instance of <code>{@link java.io.File}</code>
	 * @throws IOException  If a file could not be created
	 */
	public static File createTempFile(File tempDir, String prefix) throws IOException {
		return createTempFile(tempDir, prefix, null);
	}

	/**
	 * Creates a file with binary data to the specific temporary directory
	 * @param tempDir specific temporary directory
	 * @param prefix prefix of filename
	 * @param contentData data for content
	 * @return instance of <code>{@link java.io.File}</code>
	 * @throws IOException If a file could not be created
	 */
	public static File createTempFile(File tempDir, String prefix, byte[] contentData) throws IOException {
		File file = File.createTempFile(prefix, "test");
		if (contentData !=null && contentData.length > 0) {
			OutputStream os = new FileOutputStream(file);
			os.write(contentData);
			os.flush();
			os.close();
		}
		
		File destination = new File(tempDir.getAbsolutePath(), file.getName());
		file.renameTo(destination);
		return destination;
	}
}
