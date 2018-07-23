package com.kpmg.cdd.controller;

import com.kpmg.cdd.entity.Document;
import com.kpmg.cdd.repository.DocumentMapper;
import com.kpmg.cdd.util.UuidUtils;
import org.apache.commons.io.FileUtils;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class DocumentController {

	private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

	@Value("${file.upload-path}")
	private String uploadPath;

	@Autowired
	private DocumentMapper documentMapper;

	@RequestMapping(value = "/files", method = RequestMethod.POST)
	private String uploadFiles(@RequestParam String id,@RequestParam String caseId, HttpServletRequest request) throws IOException, XMLStreamException {
		if (!(request instanceof MultipartHttpServletRequest)) {
			throw new FlowableIllegalArgumentException("Multipart request is required");
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		if (multipartRequest.getFileMap().size() == 0) {
			throw new FlowableIllegalArgumentException("Multipart request with file content is required");
		}

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		if(files.size()!=1){
			throw new FlowableIllegalArgumentException("Just can upload one file");
		}

		for (MultipartFile file : files) {
			log.debug("========================================");
			if (file.isEmpty()) {
				return "no file uploaded";
			} else {
				String originalFilename=file.getOriginalFilename();
				String extName="";
				if(originalFilename.indexOf(".")>0){
					 extName=originalFilename.substring(originalFilename.lastIndexOf("."),originalFilename.length());
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
				String date = df.format(new Date());// new
				String fileName=date+"/" + UuidUtils.getSimpleId();
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(uploadPath+fileName));

				Document document = new Document();
				document.setId(id);
				document.setStatus("uploaded");
				document.setUploadUrl(fileName);
				document.setType(extName);
				documentMapper.updateUploadUrl(document);

			}
			log.debug("========================================");
		}



		return caseId;
	}

	@RequestMapping(value = "/files/{caseId}", method = RequestMethod.GET)
	public List<Document> fetchDocumentList(@PathVariable String caseId) throws UnsupportedEncodingException {
		return documentMapper.selectByCaseId(caseId);
	}

	@RequestMapping(value = "/files/{id}", method = RequestMethod.DELETE)
	public int removeDocument(@PathVariable String id) throws UnsupportedEncodingException {
		Document document = new Document();
		document.setId(id);
		document.setStatus("need");
		document.setUploadUrl("");
		document.setType("");
		return documentMapper.updateUploadUrl(document);
	}

	@RequestMapping(value = "/file/{id}", method = RequestMethod.GET)
	public void downloadSummaryExcelDetail(@PathVariable String id,
										   HttpServletRequest request,
										   HttpServletResponse response) throws UnsupportedEncodingException {
		Document document=documentMapper.selectById(id);


		String userAgent = request.getHeader("User-Agent");

		String fileNameDecode = URLDecoder.decode(document.getName().replaceAll(" ","-")+document.getType(), "UTF-8");
		File file = new File(uploadPath+document.getUploadUrl());

		if (file.exists()) {
			// 设置强制下载不打开
			response.setContentType("application/force-download");
			// 针对IE或者以IE为内核的浏览器：
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				try {
					response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"",
							new String(fileNameDecode.getBytes("gbk"), "iso-8859-1")));
				} catch (UnsupportedEncodingException e) {
					log.error(e.getMessage(), e);
					response.setHeader("Content-disposition",
							String.format("attachment; filename=\"%s\"", fileNameDecode));
				}
			} else {
				// 非IE浏览器的处理：
				fileNameDecode = new String(fileNameDecode.getBytes("UTF-8"), "iso-8859-1");
				response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileNameDecode));
				response.setContentType("application/octet-stream;charset=UTF-8");
			}

			output(response, file);
		}
	}

	private void output(HttpServletResponse response, File file) {
		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			OutputStream os = response.getOutputStream();
			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (null != bis) {
				try {
					bis.close();
				} catch (Exception e2) {
					log.error(e2.getMessage(), e2);
				}
			}
			if (null != fis) {
				try {
					fis.close();
				} catch (Exception e2) {
					log.error(e2.getMessage(), e2);
				}
			}
		}
	}

}
