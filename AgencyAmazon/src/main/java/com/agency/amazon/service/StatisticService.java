package com.agency.amazon.service;

import com.agency.amazon.model.dto.UserDto;

import java.util.Date;
import java.util.List;

public interface StatisticService {
	List<UserDto> getStatisticByDate(Date date);

	List<UserDto> getStatisticBetweenDates(Date fromDate, Date toDate);

	List<UserDto> getStatisticWithTime();

	UserDto getStatisticByAsin(String asin);

	List<UserDto> getStatisticByAsinsList(List<String> asins);

	List<UserDto> getStatisticWithAsins();

	List<UserDto> getStatisticByDateList(List<Date> dates);
}
