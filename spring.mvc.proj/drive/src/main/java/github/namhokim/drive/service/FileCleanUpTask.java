package github.namhokim.drive.service;

import java.io.File;
import java.io.FileFilter;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class FileCleanUpTask implements FileFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(FileCleanUpTask.class);
	
	/**
	 * 10 seconds
	 */
	public static final long DEFAULT_CRITICAL_TIME = 10000;

	private long criticalTime;
	
	@Inject
	private FileSystemResource fsResource;
	
	private long currentTime;
	
	public FileCleanUpTask() {
		this.criticalTime = DEFAULT_CRITICAL_TIME;
	}
	
	public FileCleanUpTask(long criticalMillis) {
		this.criticalTime = criticalMillis;
	}
	
	/**
	 * Task for remove of expired files 
	 */
    public void removeExpiredFiles() {
    	
    	setCurrentTimeNow();

        File dir = new File(fsResource.getPath());
        File[] list = dir.listFiles(this);
        for (File file : list) {
        	logger.info("Auto expire file removed: {}", file.getName());
        	file.delete();
        }
    }

	@Override
	public boolean accept(File file) {
		if (!file.isFile()) return false;
		
		long modifiedTime = file.lastModified();
		long diffMilliseconds = currentTime - modifiedTime;
		
		return (diffMilliseconds > criticalTime);
	}

	private void setCurrentTimeNow() {
		this.currentTime = System.currentTimeMillis();
	}

}
