package ua.jarvis.core.model.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.jarvis.core.model.User;
import ua.jarvis.core.model.criteria.UserCriteria;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Component
public class SpecificationProvider {

	private static final Map<String, BiConsumer<UserSpecificationBuilder, String>> criteriaMap = new HashMap<>();

	static {
		criteriaMap.put("name", UserSpecificationBuilder::hasName);
		criteriaMap.put("surName", UserSpecificationBuilder::hasSurName);
		criteriaMap.put("middleName", UserSpecificationBuilder::hasMiddleName);
		criteriaMap.put("month", (builder, value) -> {
			if (!"00".equals(value)) builder.hasBirthMonth(value);
		});
		criteriaMap.put("year", (builder, value) -> {
			if (!"0000".equals(value)) builder.hasBirthYear(value);
		});
		criteriaMap.put("day", (builder, value) -> {
			if (!"00".equals(value)) builder.hasBirthDay(value);
		});
		criteriaMap.put("rnokpp", UserSpecificationBuilder::hasRnokpp);
		criteriaMap.put("phoneNumber", UserSpecificationBuilder::hasPhoneNumber);
		criteriaMap.put("autoPlateNumber", UserSpecificationBuilder::hasAutoPlateNumber);
		criteriaMap.put("email", UserSpecificationBuilder::hasEmail);
		criteriaMap.put("driverLicenseNumber", UserSpecificationBuilder::hasDriverLicenseNumber);
		criteriaMap.put("flatNumber", UserSpecificationBuilder::hasFlatNumber);
		criteriaMap.put("homeNumber", UserSpecificationBuilder::hasHomeNumber);
		criteriaMap.put("street", UserSpecificationBuilder::hasStreet);
		criteriaMap.put("city", UserSpecificationBuilder::hasCity);
		criteriaMap.put("region", UserSpecificationBuilder::hasRegion);
		criteriaMap.put("foreignPassport", UserSpecificationBuilder::hasForeignPassport);
		criteriaMap.put("passport", UserSpecificationBuilder::hasPassport);
		criteriaMap.put("district", UserSpecificationBuilder::hasDistrict);
		criteriaMap.put("corpus", UserSpecificationBuilder::hasCorpus);
		criteriaMap.put("other", UserSpecificationBuilder::hasOther);
		criteriaMap.put("otherNum", UserSpecificationBuilder::hasOtherNum);
	}

	public Specification<User> get(final UserCriteria criteria) {
		final UserSpecificationBuilder builder = UserSpecificationBuilder.builder();

		criteria.toMap().forEach((key, value) -> {
			if (value != null) {
				final BiConsumer<UserSpecificationBuilder, String> consumer = criteriaMap.get(key);
				if (consumer != null) {
					consumer.accept(builder, value);
				}
			}
		});

		return builder.build();
	}
}