package github.namhokim.drive.controller.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.ByteStreams;

@Controller
@RequestMapping(value = "/file")
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Inject
	private FileSystemResource fsResource;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public String upload(HttpServletRequest request, @RequestParam(value = "fileData", required = false) List<MultipartFile> files){

		if (files==null) {
			logger.error("handleFileUpload error, UploadItem was null");
			return "upload";
		}
		
		try {
			for (MultipartFile file : files) {
				uploadProcedure(file, request.getRemoteAddr());
			}
			return "redirect:download";
		} catch (IOException e) {
			logger.error("upload exception: {}", e.getMessage());
			return "upload";
		}
	}
	
	private boolean uploadProcedure(MultipartFile multiPartFile, String remoteAddr) throws IOException {
		String filename = multiPartFile.getOriginalFilename();

		try {
			File lOutFile = new File(fsResource.getPath() + filename);
			multiPartFile.transferTo(lOutFile);
			logger.info("{} uploaded by {}", filename, remoteAddr);
			return true;
		} catch (IOException ie) {
			logger.error("{} uploadf ailed, {} by {}", filename, ie.getMessage(), remoteAddr);
			return false;
		}
	}

}
