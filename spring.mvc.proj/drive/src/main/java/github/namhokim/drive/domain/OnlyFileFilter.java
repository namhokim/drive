package github.namhokim.drive.domain;

import java.io.File;
import java.io.FileFilter;

public class OnlyFileFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		return pathname.isFile();
	}

}
