package com.agency.amazon.controller;

import com.agency.amazon.model.dto.AsinDto;
import com.agency.amazon.model.dto.DateDto;
import com.agency.amazon.model.dto.UserDto;
import com.agency.amazon.service.StatisticService;
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

	private final StatisticService statisticService;

	public StatisticController(final StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	@PostMapping ("/statistics/dates")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> date(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
		LOG.debug("Date method was called in StatisticController.");

		return statisticService.getStatisticByDate(date);
	}

	@PostMapping ("/statistics/multiply/dates")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> datesList(@RequestBody @Valid DateDto date){
		LOG.debug("datesList method was called in StatisticController.");

		return statisticService.getStatisticByDateList(date.getDates());
	}

	@PostMapping("/statistics/between/dates")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> betweenDates(@RequestBody @Valid DateDto date){
		LOG.debug("BetweenDates method was called in StatisticController.");

		return statisticService.getStatisticBetweenDates(date.getDates().get(0), date.getDates().get(1));
	}

	@PostMapping("/statistics/all/dates")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> allDates(){
		LOG.debug("AllDates method was called in StatisticController.");

		return statisticService.getStatisticWithTime();
	}

	@PostMapping ("/statistics/asins")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public UserDto asin(@RequestBody @Valid AsinDto asinDto){
		LOG.debug("Asins method was called in StatisticController.");

		return statisticService.getStatisticByAsin(asinDto.getAsins().get(0));
	}

	@PostMapping ("/statistics/multiply/asins")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> asinsList(@RequestBody @Valid AsinDto asinDto){
		LOG.debug("ListAsins method was called in StatisticController.");

		return statisticService.getStatisticByAsinsList(asinDto.getAsins());
	}

	@PostMapping ("/statistics/all/asins")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<UserDto> allAsins(){
		LOG.debug("AllAsins method was called in StatisticController.");

		return statisticService.getStatisticWithAsins();
	}
}
