package com.myspring.pro30.news.vo;

import java.sql.Date;

public class NewsVO {
	int newsNo;
	String newTitle;
	String newsContent;
	Date newsWriteDate;
	
	public NewsVO() {
	}

	public int getNewsNo() {
		return newsNo;
	}

	public void setNewsNo(int newsNo) {
		this.newsNo = newsNo;
	}

	public String getNewTitle() {
		return newTitle;
	}

	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
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
	
	
}
