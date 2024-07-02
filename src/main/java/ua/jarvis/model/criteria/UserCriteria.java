package ua.jarvis.model.criteria;

import java.util.HashMap;
import java.util.Map;

public class UserCriteria {

	private String name;
	private String surName;
	private String middleName;
	private String month;
	private String year;
	private String day;
	private String rnokpp;
	private String phoneNumber;
	private String autoPlateNumber;
	private String email;
	private String driverLicenseNumber;
	private String flatNumber;
	private String homeNumber;
	private String street;
	private String city;
	private String region;

	public UserCriteria() {}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(final String surName) {
		this.surName = surName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(final String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(final String year) {
		this.year = year;
	}

	public String getDay() {
		return day;
	}

	public void setDay(final String day) {
		this.day = day;
	}

	public String getRnokpp() {
		return rnokpp;
	}

	public void setRnokpp(final String rnokpp) {
		this.rnokpp = rnokpp;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAutoPlateNumber() {
		return autoPlateNumber;
	}

	public void setAutoPlateNumber(final String autoPlateNumber) {
		this.autoPlateNumber = autoPlateNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getDriverLicenseNumber() {
		return driverLicenseNumber;
	}

	public void setDriverLicenseNumber(final String driverLicenseNumber) {
		this.driverLicenseNumber = driverLicenseNumber;
	}

	public String getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(final String flatNumber) {
		this.flatNumber = flatNumber;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(final String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(final String region) {
		this.region = region;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("surName", surName);
		map.put("middleName", middleName);
		map.put("month", month);
		map.put("year", year);
		map.put("day", day);
		map.put("rnokpp", rnokpp);
		map.put("phoneNumber", phoneNumber);
		map.put("autoPlateNumber", autoPlateNumber);
		map.put("email", email);
		map.put("driverLicenseNumber", driverLicenseNumber);
		map.put("flatNumber", flatNumber);
		map.put("homeNumber", homeNumber);
		map.put("street", street);
		map.put("city", city);
		map.put("region", region);

		return map;
	}

	public static class UserCriteriaBuilder {
		private String name;
		private String surName;
		private String middleName;
		private String month;
		private String year;
		private String day;
		private String rnokpp;
		private String phoneNumber;
		private String autoPlateNumber;
		private String email;
		private String driverLicenseNumber;
		private String flatNumber;
		private String homeNumber;
		private String street;
		private String city;
		private String region;

		public UserCriteriaBuilder() {}

		public UserCriteriaBuilder name(String name) {
			this.name = name;
			return this;
		}

		public UserCriteriaBuilder surName(String surName) {
			this.surName = surName;
			return this;
		}

		public UserCriteriaBuilder middleName(String middleName) {
			this.middleName = middleName;
			return this;
		}

		public UserCriteriaBuilder month(String month) {
			this.month = month;
			return this;
		}

		public UserCriteriaBuilder year(String year) {
			this.year = year;
			return this;
		}

		public UserCriteriaBuilder day(String day) {
			this.day = day;
			return this;
		}

		public UserCriteriaBuilder rnokpp(String rnokpp) {
			this.rnokpp = rnokpp;
			return this;
		}

		public UserCriteriaBuilder phoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public UserCriteriaBuilder autoPlateNumber(String autoPlateNumber) {
			this.autoPlateNumber = autoPlateNumber;
			return this;
		}

		public UserCriteriaBuilder email(String email) {
			this.email = email;
			return this;
		}

		public UserCriteriaBuilder driverLicenseNumber(String driverLicenseNumber) {
			this.driverLicenseNumber = driverLicenseNumber;
			return this;
		}

		public UserCriteriaBuilder flatNumber(String flatNumber) {
			this.flatNumber = flatNumber;
			return this;
		}

		public UserCriteriaBuilder homeNumber(String homeNumber) {
			this.homeNumber = homeNumber;
			return this;
		}

		public UserCriteriaBuilder street(String street) {
			this.street = street;
			return this;
		}

		public UserCriteriaBuilder city(String city) {
			this.city = city;
			return this;
		}

		public UserCriteriaBuilder region(String region) {
			this.region = region;
			return this;
		}

		public UserCriteria build() {
			UserCriteria userCriteria = new UserCriteria();
			userCriteria.setName(this.name);
			userCriteria.setSurName(this.surName);
			userCriteria.setMiddleName(this.middleName);
			userCriteria.setMonth(this.month);
			userCriteria.setYear(this.year);
			userCriteria.setDay(this.day);
			userCriteria.setRnokpp(this.rnokpp);
			userCriteria.setPhoneNumber(this.phoneNumber);
			userCriteria.setAutoPlateNumber(this.autoPlateNumber);
			userCriteria.setEmail(this.email);
			userCriteria.setDriverLicenseNumber(this.driverLicenseNumber);
			userCriteria.setFlatNumber(this.flatNumber);
			userCriteria.setStreet(this.street);
			userCriteria.setHomeNumber(this.homeNumber);
			userCriteria.setCity(this.city);
			userCriteria.setRegion(this.region);
			return userCriteria;
		}
	}
}
