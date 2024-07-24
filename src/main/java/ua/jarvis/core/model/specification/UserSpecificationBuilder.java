package ua.jarvis.core.model.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.jarvis.core.constant.Constants;
import ua.jarvis.core.model.Car;
import ua.jarvis.core.model.User;
import ua.jarvis.core.model.Address;
import ua.jarvis.core.model.BirthCertificate;
import ua.jarvis.core.model.DriverLicense;
import ua.jarvis.core.model.Email;
import ua.jarvis.core.model.Phone;

@Component
public class UserSpecificationBuilder {

	private Specification<User> specification;

	private UserSpecificationBuilder() {
		this.specification = Specification.where(null);
	}

	public static UserSpecificationBuilder builder() {
		return new UserSpecificationBuilder();
	}

	public UserSpecificationBuilder hasCity(final String city) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, Address> addressJoin = root.joinSet(Constants.SpecificationType.ADRESSES, JoinType.INNER);
			return builder.equal(addressJoin.get(Constants.SpecificationType.CITY), city);
		});
		return this;
	}

	public UserSpecificationBuilder hasHomeNum(final String homeNum) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, Address> addressJoin = root.joinSet(Constants.SpecificationType.ADRESSES, JoinType.INNER);
			return builder.equal(addressJoin.get(Constants.SpecificationType.HOME_NUMBER), homeNum);
		});
		return this;
	}

	public UserSpecificationBuilder hasStreet(final String street) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, Address> addressJoin = root.joinSet(Constants.SpecificationType.ADRESSES, JoinType.INNER);
			return builder.equal(addressJoin.get(Constants.SpecificationType.STREET), street);
		});
		return this;
	}

	public UserSpecificationBuilder hasHomeNumber(final String homeNumber) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, Address> addressJoin = root.joinSet(Constants.SpecificationType.ADRESSES, JoinType.INNER);
			return builder.equal(addressJoin.get(Constants.SpecificationType.HOME_NUMBER), homeNumber);
		});
		return this;
	}

	public UserSpecificationBuilder hasFlatNumber(final String flatNum) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, Address> addressJoin = root.joinSet(Constants.SpecificationType.ADRESSES, JoinType.INNER);
			return builder.equal(addressJoin.get(Constants.SpecificationType.FLAT_NUMBER), flatNum);
		});
		return this;
	}

	public UserSpecificationBuilder hasDriverLicenseNumber(final String number) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, DriverLicense> driverLicense = root.joinSet(Constants.SpecificationType.DRIVER_LICENSE, JoinType.INNER);
			return builder.equal(driverLicense.get(Constants.SpecificationType.LICENSE_NUMBER), number);
		});
		return this;
	}

	public UserSpecificationBuilder hasEmail(final String email) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, Email> emailJoin = root.joinSet(Constants.SpecificationType.EMAILS, JoinType.INNER);
			return builder.equal(emailJoin.get(Constants.SpecificationType.EMAIL_ADDRESS), email);
		});
		return this;
	}

	public UserSpecificationBuilder hasAutoPlateNumber(final String plateNumber) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, Car> carJoin = root.joinSet(Constants.SpecificationType.CARS, JoinType.INNER);
			return builder.equal(carJoin.get(Constants.SpecificationType.PLATE_NUMBER), plateNumber);
		});
		return this;
	}

	public UserSpecificationBuilder hasPhoneNumber(final String phoneNumber) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, Phone> phoneJoin = root.joinSet(Constants.SpecificationType.PHONES, JoinType.INNER);
			return builder.equal(phoneJoin.get(Constants.SpecificationType.PHONE_NUMBER), phoneNumber);
		});
		return this;
	}

	public UserSpecificationBuilder hasRnokpp(final String rnokpp) {
		this.specification = specification.and((root, query, builder) ->
			builder.equal(root.get(Constants.SpecificationType.RNOKPP), rnokpp)
		);
		return this;
	}

	public UserSpecificationBuilder hasSurName(final String surName) {
		this.specification = specification.and((root, query, builder) ->
			builder.equal(root.get(Constants.SpecificationType.SUR_NAME), surName)
		);
		return this;
	}

	public UserSpecificationBuilder hasName(final String name) {
		this.specification = specification.and((root, query, builder) ->
			builder.equal(root.get(Constants.SpecificationType.NAME), name)
		);
		return this;
	}

	public UserSpecificationBuilder hasMidlName(final String midlName) {
		this.specification = specification.and((root, query, builder) ->
			builder.equal(root.get(Constants.SpecificationType.MIDDLE_NAME), midlName)
		);
		return this;
	}

	public UserSpecificationBuilder hasBirthMonth(final String month) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, BirthCertificate> birthCertificateJoin =
				root.join(Constants.SpecificationType.BIRTH_CERTIFICATE, JoinType.INNER);
			return builder.equal(birthCertificateJoin.get(Constants.SpecificationType.MONTH), month);
		});
		return this;
	}

	public UserSpecificationBuilder hasBirthYear(final String year) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, BirthCertificate> birthCertificateJoin =
				root.join(Constants.SpecificationType.BIRTH_CERTIFICATE, JoinType.INNER
				);
			return builder.equal(birthCertificateJoin.get(Constants.SpecificationType.YEAR), year);
		});
		return this;
	}

	public UserSpecificationBuilder hasBirthDay(final String day) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, BirthCertificate> birthCertificateJoin =
				root.join(Constants.SpecificationType.BIRTH_CERTIFICATE, JoinType.INNER);
			return builder.equal(birthCertificateJoin.get(Constants.SpecificationType.DAY), day);
		});
		return this;
	}

	public UserSpecificationBuilder hasRegion(final String region) {
		this.specification = specification.and((root, query, builder) -> {
			Join<User, Address> addressJoin = root.joinSet(Constants.SpecificationType.ADRESSES, JoinType.INNER);
			return builder.equal(addressJoin.get(Constants.SpecificationType.REGION), region);
		});
		return this;
	}

	public Specification<User> build() {
		return this.specification;
	}
}
