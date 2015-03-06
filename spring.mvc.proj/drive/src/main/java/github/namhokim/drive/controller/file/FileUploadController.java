package github.namhokim.drive.controller.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import github.namhokim.drive.domain.UploadItem;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value = "/file")
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Inject
	private FileSystemResource fsResource;

	@RequestMapping(method = RequestMethod.POST)
	public String upload(HttpServletRequest request, UploadItem uploadItem, BindingResult result){
		if (result.hasErrors()) {
			errorHandler(result);
			return "upload";
		}

		if (uploadItem==null) {
			logger.error("handleFileUpload error, UploadItem was null");
			return "upload";
		}
		
		CommonsMultipartFile mpf = uploadItem.getFileData();
		if (!mpf.isEmpty() && uploadProcedure(mpf, request.getRemoteAddr())) {
			return "redirect:download";
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
	
	private boolean uploadProcedure(CommonsMultipartFile multiPartFile, String remoteAddr) {
		String filename = multiPartFile.getOriginalFilename();

		byte[] bytes = multiPartFile.getBytes();
		try {
			File lOutFile = new File(fsResource.getPath() + filename);
			FileOutputStream lFileOutputStream = new FileOutputStream(
					lOutFile);
			lFileOutputStream.write(bytes);
			lFileOutputStream.close();
			logger.info("{} uploaded by {}", filename, remoteAddr);
			return true;
		} catch (IOException ie) {
			logger.error("{} uploadf ailed, {} by {}", filename, ie.getMessage(), remoteAddr);
			return false;
		}
	}

}
