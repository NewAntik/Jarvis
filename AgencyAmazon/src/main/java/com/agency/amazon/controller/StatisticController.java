package com.agency.amazon.controller;

import com.agency.amazon.model.dto.AsinDto;
import com.agency.amazon.model.dto.DateDto;
import com.agency.amazon.model.dto.UserDto;
import com.agency.amazon.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.agency.amazon.controller.UserController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class StatisticController {
	private static final Logger LOG = LoggerFactory.getLogger(StatisticController.class);

	private final UserService userService;

	public StatisticController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping ("/statistics/dates")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> date(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
		LOG.debug("Date method was called in StatisticController.");

		return userService.getStatisticByDate(date);
	}

	@PostMapping("/statistics/between/dates")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> betweenDates(@RequestBody @Valid DateDto date){
		LOG.debug("BetweenDates method was called in StatisticController.");

		return userService.getStatisticBetweenDates(date.getFromDate(), date.getToDate());
	}

	@PostMapping("/statistics/all/dates")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> allDates(){
		LOG.debug("AllDates method was called in StatisticController.");

		return userService.getStatisticWithTime();
	}

	@PostMapping ("/statistics/asins")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public UserDto asin(@RequestBody String asin){
		LOG.debug("Asins method was called in StatisticController.");

		return userService.getStatisticByAsin(asin);
	}

	@PostMapping ("/statistics/multiply/asins")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> listAsins(@RequestBody @Valid AsinDto asinDto){
		LOG.debug("ListAsins method was called in StatisticController.");

		return userService.getStatisticByAsinsList(asinDto.getAsins());
	}

	@PostMapping ("/statistics/all/asins")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> allAsins(){
		LOG.debug("AllAsins method was called in StatisticController.");

		return userService.getStatisticWithAsins();
	}
}
