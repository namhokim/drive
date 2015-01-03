package github.namhokim.drive;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DownloadController {

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download(Locale locale, Model model) {
		return "download";
	}

}
