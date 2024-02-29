package com.agency.amazon.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DateDto {
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fromDate;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date toDate;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
