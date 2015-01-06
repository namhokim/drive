package github.namhokim.drive.controller;

import github.namhokim.drive.domain.UploadItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/upload")
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Inject
	private FileSystemResource fsResource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String upload(Locale locale, Model model) {
		model.addAttribute(new UploadItem());
		return "upload";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String handleFileUpload(UploadItem uploadItem, BindingResult result){
		
		logger.info("upload - POST");

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				logger.error("{0} - {1}", error.getCode(),
						error.getDefaultMessage());
			}
			return "upload";
		}

		if (!uploadItem.getFileData().isEmpty()) {
			String filename = uploadItem.getFileData().getOriginalFilename();

			byte[] bytes = uploadItem.getFileData().getBytes();
			try {
				File lOutFile = new File(fsResource.getPath() + filename);
				FileOutputStream lFileOutputStream = new FileOutputStream(
						lOutFile);
				lFileOutputStream.write(bytes);
				lFileOutputStream.close();
				logger.info("File upload success! - {0}", uploadItem
						.getFileData().getOriginalFilename());
			} catch (IOException ie) {
				logger.error("File writing error! - {0}", ie.getMessage());
			}
		}
	    
	    return "list";
    }

}
