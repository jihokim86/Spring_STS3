package com.myspring.pro30.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FileDownloadController {
	private static final String ARTICLE_IMAGE_REPO = "C:\\article\\news_image";
	@RequestMapping("/download.do")
	protected void download(@RequestParam("imageFileName") String imageFileName,
							@RequestParam("newsNo") int newsNo,
			                 HttpServletResponse response)throws Exception {
		
		System.out.println("download_newsNo="+newsNo);
		System.out.println("download_imageFileName="+imageFileName);
		
		OutputStream out = response.getOutputStream();
		String downFile = ARTICLE_IMAGE_REPO + "\\"+newsNo+"\\"+imageFileName;
		System.out.println(downFile);
		File file = new File(downFile);

		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + imageFileName);
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024 * 8];
		while (true) {
			int count = in.read(buffer); 
			if (count == -1) 
				break;
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
	}

}
