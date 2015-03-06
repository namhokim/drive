package github.namhokim.drive.controller.page;

import java.io.File;
import java.io.FileFilter;

import javax.inject.Inject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/download")
public class DownloadController {

	@Inject
	private FileSystemResource fsResource;
	
	@Inject
	private FileFilter fileFilter;
	
	@RequestMapping(method = RequestMethod.GET)
	public String download(Model model) {
		
		File dir = new File(fsResource.getPath());
		model.addAttribute("lists", dir.listFiles(fileFilter));
		return "download";
	}

}
