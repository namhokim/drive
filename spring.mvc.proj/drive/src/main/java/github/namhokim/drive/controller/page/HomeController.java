package github.namhokim.drive.controller.page;

import java.io.File;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	private FileSystemResource fsResource;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.debug("Welcome home! The client locale is {}.", locale);
		
		createRootDriveForContentPath();
		
		return "home";
	}

	private void createRootDriveForContentPath() {
		File theDir = new File(fsResource.getPath());
		if ( HasDirectory(theDir) == false) {
			boolean succeed = theDir.mkdirs();
			logger.info("Create directory {} was {}", theDir.getAbsolutePath(), succeed);
		}
	}

	private boolean HasDirectory(File theDir) {
		return theDir.exists() && theDir.isDirectory();
	}
	
}
