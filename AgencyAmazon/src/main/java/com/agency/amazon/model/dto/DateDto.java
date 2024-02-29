package com.agency.amazon.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class DateDto {
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private List<Date> dates;

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}
}
