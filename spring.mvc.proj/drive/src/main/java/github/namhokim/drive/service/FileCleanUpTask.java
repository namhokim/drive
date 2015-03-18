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

	private long criticalTime = 10000;
	
	@Inject
	private FileSystemResource fsResource;
	
	private long currentTime;
	
	public FileCleanUpTask(long criticalMillis) {
		this.criticalTime = criticalMillis;
	}
	
    public void removeExpiredFiles() {
    	
    	currentTime = System.currentTimeMillis();

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

}
