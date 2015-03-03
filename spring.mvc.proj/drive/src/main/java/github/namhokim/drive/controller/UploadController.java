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
import org.springframework.web.multipart.commons.CommonsMultipartFile;


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
			errorHandler(result);
			return "upload";
		}

		if (uploadItem==null) {
			return "upload";
		}
		
		CommonsMultipartFile mpf = uploadItem.getFileData();
		if (!mpf.isEmpty() && uploadProcedure(mpf)) {
			return "redirect:list";
		} else {
			return "upload";
		}
	    
    }
	
	private void errorHandler(BindingResult result)
	{
		for (ObjectError error : result.getAllErrors()) {
			logger.error("{} - {}", error.getCode(), error.getDefaultMessage());
		}
	}
	
	private boolean uploadProcedure(CommonsMultipartFile multiPartFile) {
		String filename = multiPartFile.getOriginalFilename();

		byte[] bytes = multiPartFile.getBytes();
		try {
			File lOutFile = new File(fsResource.getPath() + filename);
			FileOutputStream lFileOutputStream = new FileOutputStream(
					lOutFile);
			lFileOutputStream.write(bytes);
			lFileOutputStream.close();
			logger.info("/upload/{} - Success", filename);
			return true;
		} catch (IOException ie) {
			logger.error("/upload/{} - Failed, {}", filename, ie.getMessage());
			return false;
		}
	}
	
}
