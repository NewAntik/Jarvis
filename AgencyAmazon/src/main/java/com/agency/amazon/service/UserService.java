package com.agency.amazon.service;

import com.agency.amazon.controller.request.LoginRequest;
import com.agency.amazon.controller.request.RegistrationRequest;
import com.agency.amazon.model.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface UserService {

	ResponseEntity<?> registration(RegistrationRequest request);

	ResponseEntity<?> authorization(LoginRequest request);

	List<UserDto> getStatisticByDate(Date date);

	List<UserDto> getStatisticBetweenDates(Date fromDate, Date toDate);

	List<UserDto> getStatisticWithTime();

	UserDto getStatisticByAsin(String asin);

	List<UserDto> getStatisticByAsinsList(List<String> asins);

	List<UserDto> getStatisticWithAsins();
}
