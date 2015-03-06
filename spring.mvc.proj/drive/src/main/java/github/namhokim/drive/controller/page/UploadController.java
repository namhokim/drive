package github.namhokim.drive.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import github.namhokim.drive.domain.UploadItem;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String upload(Model model) {
		model.addAttribute(new UploadItem());
		return "upload";
	}
	
}
