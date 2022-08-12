package com.myspring.pro30.news.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;

public class NewsVO {
	int newsNo;
	String newsTitle;
	String newsContent;
	Date newsWriteDate;
	String imageFileName;
	String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NewsVO() {
	}

	public int getNewsNo() {
		return newsNo;
	}

	public void setNewsNo(int newsNo) {
		this.newsNo = newsNo;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public Date getNewsWriteDate() {
		return newsWriteDate;
	}

	public void setNewsWriteDate(Date newsWriteDate) {
		this.newsWriteDate = newsWriteDate;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		try {
			if(imageFileName!= null && imageFileName.length()!=0) {
				this.imageFileName = URLEncoder.encode(imageFileName,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	

	
	
	
}
