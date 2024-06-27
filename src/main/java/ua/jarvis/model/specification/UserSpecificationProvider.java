package ua.jarvis.model.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import ua.jarvis.model.Address;
import ua.jarvis.model.BirthCertificate;
import ua.jarvis.model.User;

public class UserSpecificationProvider {
	public static Specification<User> hasSurName(String surName) {
		return (root, query, builder) -> builder.equal(root.get("surName"), surName);
	}

	public static Specification<User> hasName(String name) {
		return (root, query, builder) -> builder.equal(root.get("name"), name);
	}

	public static Specification<User> hasMidlName(String midlName) {
		return (root, query, builder) -> builder.equal(root.get("midlName"), midlName);
	}

	public static Specification<User> hasBirthMonth(String month) {
		return (root, query, builder) -> {
			Join<User, BirthCertificate> birthCertificateJoin = root.join("birthCertificate", JoinType.INNER);
			return builder.equal(birthCertificateJoin.get("month"), month);
		};
	}

	public static Specification<User> hasBirthYear(String year) {
		return (root, query, builder) -> {
			Join<User, BirthCertificate> birthCertificateJoin = root.join("birthCertificate", JoinType.INNER);
			return builder.equal(birthCertificateJoin.get("year"), year);
		};
	}

	public static Specification<User> hasBirthDay(String day) {
		return (root, query, builder) -> {
			Join<User, BirthCertificate> birthCertificateJoin = root.join("birthCertificate", JoinType.INNER);
			return builder.equal(birthCertificateJoin.get("day"), day);
		};
	}

	public static Specification<User> hasRegion(final String region) {
		return (root, query, builder) -> {
			Join<User, Address> addressJoin = root.joinSet("addresses", JoinType.INNER);
			return builder.equal(addressJoin.get("region"), region);
		};
	}
}
