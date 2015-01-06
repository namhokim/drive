package github.namhokim.drive.controller;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FileListController {
	
	@Inject
	private FileSystemResource fsResource;
	
	@Inject
	private FileFilter fileFilter;

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download(Locale locale, Model model) {
		
		File dir = new File(fsResource.getPath());
		model.addAttribute("lists", dir.listFiles(fileFilter));
		return "filelist";
	}

}
