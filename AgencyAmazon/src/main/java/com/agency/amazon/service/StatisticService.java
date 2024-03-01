package com.agency.amazon.service;

import com.agency.amazon.model.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface StatisticService {
	@Transactional(readOnly = true)
	List<UserDto> getStatisticByDate(Date date);

	@Transactional(readOnly = true)
	List<UserDto> getStatisticBetweenDates(Date fromDate, Date toDate);

	@Transactional(readOnly = true)
	List<UserDto> getStatisticWithTime();

	@Transactional(readOnly = true)
	UserDto getStatisticByAsin(String asin);

	@Transactional(readOnly = true)
	List<UserDto> getStatisticByAsinsList(List<String> asins);

	@Transactional(readOnly = true)
	List<UserDto> getStatisticWithAsins();

	@Transactional(readOnly = true)
	List<UserDto> getStatisticByDateList(List<Date> dates);
}
