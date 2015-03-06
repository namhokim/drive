package github.namhokim.drive.controller.file;

import github.namhokim.drive.domain.Result;

import java.io.File;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/file")
public class FileRemoveController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileRemoveController.class);
	
	@Inject
	private FileSystemResource fsResource;
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> remove(HttpServletRequest request, @RequestParam("filename") String filename) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		logger.info("{} removed by {}", filename, request.getRemoteAddr());
		
		File target = new File(fsResource.getPath() + filename);
		String message = "";
		boolean removeResult = false;
		if (target.exists()) {
			removeResult = target.delete();
			if (removeResult) {
				message = getResult(true, "Success");
			} else {
				message = getResult(false, "Failure");
			}
		} else {
			message = getResult(false, "File not found");
		}
		
		return new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
	}

	private String getResult(boolean isSucceed, String message) {
		Gson gson = new Gson();
		Result result = new Result();
		result.setSuccess(isSucceed);
		result.setReason(message);
		return gson.toJson(result);
	}

}
