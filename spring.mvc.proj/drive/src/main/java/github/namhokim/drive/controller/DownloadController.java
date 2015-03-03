package github.namhokim.drive.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DownloadController {
	
	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);
	
	@Inject
	private FileSystemResource fsResource;

	@RequestMapping(method = RequestMethod.GET, value = "/download")
	@ResponseBody
	public ResponseEntity<InputStreamResource> download(HttpServletRequest request, @RequestParam("filename") String filename) throws IOException {
		
		logger.info("{} download by {}", filename, request.getRemoteAddr());
		logger.info("user agent: {}", request.getHeader ("user-agent"));

		// Java의 java.net.URLEncoder는 공백문자를 %20가 아닌 +로 인코딩한다.
		// 만약 +로 Content-Disposition를 반환하면 공백이 +로 바뀌어 버린다. 
		String encordedFilename = URLEncoder.encode(filename,"UTF-8").replace("+", "%20");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		responseHeaders.set("Content-Disposition", "attachment;filename=" + encordedFilename + ";");
		responseHeaders.set("Content-Transfer-Encoding", "binary");
		return new ResponseEntity<InputStreamResource>(getFileContent(filename), responseHeaders, HttpStatus.OK);
	}
		
	private InputStreamResource getFileContent(String filename) throws IOException {
		String path = String.format("%s%s", fsResource.getPath(), filename);
		FileSystemResource resource = new FileSystemResource(path);
		return new InputStreamResource(resource.getInputStream());
	}

}
