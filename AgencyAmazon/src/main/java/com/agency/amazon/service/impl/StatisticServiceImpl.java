package com.agency.amazon.service.impl;

import com.agency.amazon.model.User;
import com.agency.amazon.model.dto.UserDto;
import com.agency.amazon.repository.UserRepository;
import com.agency.amazon.service.StatisticService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class StatisticServiceImpl implements StatisticService {

	private final UserRepository userRepository;

	public StatisticServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Cacheable("UserDto")
	public List<UserDto> getStatisticByDate(final Date date) {
		return mapToUserDto(userRepository.findByCreatedDate(date)
			.orElseThrow(() -> new NoSuchElementException("No info with the date:" + date.toString())));
	}

	@Override
	@Cacheable("UserDto")
	public List<UserDto> getStatisticBetweenDates(final Date fromDate, final Date toDate) {
		return mapToUserDto(userRepository.findBetweenDates(fromDate, toDate)
			.orElseThrow(() -> new NoSuchElementException(format("Cannot find mfa for user with id: %s%s", fromDate, toDate))));
	}

	@Override
	@Cacheable("UserDto")
	public List<UserDto> getStatisticWithTime() {
		return mapToUserDto(userRepository.findAllByCreatedDateIsNotNull()
			.orElseThrow(() -> new NoSuchElementException("There is no users with the Date.")));
	}

	@Override
	@Cacheable("UserDto")
	public UserDto getStatisticByAsin(final String asin) {
		final User user = userRepository.findByAsin(asin)
			.orElseThrow(() -> new NoSuchElementException("No info with the asin: " + asin));

		return new UserDto
			(
				user.getFirstName(),
				user.getLastName(),
				user.getRole(),
				user.getCreatedDate(),
				user.getAsin()
			);
	}

	@Override
	@Cacheable("UserDto")
	public List<UserDto> getStatisticByAsinsList(final List<String> asins) {
		return mapToUserDto(userRepository.findAllByAsinIn(asins)
			.orElseThrow(() -> new NoSuchElementException("No info with the asins: " + asins.toString())));
	}

	@Override
	@Cacheable("UserDto")
	public List<UserDto> getStatisticWithAsins() {
		return mapToUserDto(userRepository.findAllByAsinIsNotNull()
			.orElseThrow(() -> new NoSuchElementException("There is no users with Asins.")));
	}

	@Override
	@Cacheable("UserDto")
	public List<UserDto> getStatisticByDateList(final List<Date> dates) {
		return mapToUserDto(userRepository.findAllByCreatedDateIn(dates)
			.orElseThrow(() -> new NoSuchElementException("No info with the asins: " + dates.toString())));
	}

	private List<UserDto> mapToUserDto(final List<User> users) {
		return users.stream()
			.map(user -> new UserDto
				(
					user.getFirstName(),
					user.getLastName(),
					user.getRole(),
					user.getCreatedDate(),
					user.getAsin())
			)
			.toList();
	}
}
