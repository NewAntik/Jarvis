package ua.jarvis.model.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.jarvis.model.User;
import ua.jarvis.model.criteria.UserCriteria;

@Component
public class SpecificationProvider {

	public SpecificationProvider(){}
	public Specification<User> get(final UserCriteria criteria) {
		UserSpecificationBuilder builder = UserSpecificationBuilder.builder();

		criteria.toMap().forEach((key, value) -> {
			if (value != null) {
				if ("name".equals(key)) {
					builder.hasName(value);
				} else if ("surName".equals(key)) {
					builder.hasSurName(value);
				} else if ("middleName".equals(key)) {
					builder.hasMidlName(value);
				} else if ("month".equals(key) && !value.equals("00")) {
					builder.hasBirthMonth(value);
				} else if ("year".equals(key) && !value.equals("0000")) {
					builder.hasBirthYear(value);
				} else if ("day".equals(key) && !value.equals("00")) {
					builder.hasBirthDay(value);
				} else if ("rnokpp".equals(key)) {
					builder.hasRnokpp(value);
				} else if ("phoneNumber".equals(key)) {
					builder.hasPhoneNumber(value);
				} else if ("autoPlateNumber".equals(key)) {
					builder.hasAutoPlateNumber(value);
				} else if ("email".equals(key)) {
					builder.hasEmail(value);
				} else if ("driverLicenseNumber".equals(key)) {
					builder.hasDriverLicenseNumber(value);
				} else if ("flatNumber".equals(key)) {
					builder.hasFlatNumber(value);
				} else if ("homeNumber".equals(key)) {
					builder.hasHomeNumber(value);
				} else if ("street".equals(key)) {
					builder.hasStreet(value);
				} else if ("homeNum".equals(key)) {
					builder.hasHomeNum(value);
				} else if ("city".equals(key)) {
					builder.hasCity(value);
				} else if ("region".equals(key)) {
					builder.hasRegion(value);
				}
			}
		});

		return builder.build();
	}
}
