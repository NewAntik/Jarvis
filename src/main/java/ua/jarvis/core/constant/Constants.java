package ua.jarvis.core.constant;

public final class Constants {

	private Constants() {}

	public static final int PHONE_NUMBER_LENGTH = 10;
	public static final String BASE_INFO = "/info";

	public final class UAMessages{
		public static final String BASE_INFO = "Для отримання доступу до цього бота зверніться до @FatherBot.";
		public static final String HAVE_NO_ACCESS = "Ви не маєте доступу для використання бота!";
		public static final String INFO_NOT_PRESENT_MESSAGE = "Дана інформація відсутня!";
		public static final String COMMAND_NOT_FOUND_MESSAGE = "Не правильно введена комманда!" +
			" Введіть /info для отримання інформації про можливості бота або до сервісної підтримки @FatherBot.";

		private UAMessages() {}
	}

	public static final class SpecificationType {
		public static final String NAME = "name";
		public static final String SUR_NAME = "surName";
		public static final String MIDDLE_NAME = "middleName";
		public static final String MONTH = "month";
		public static final String YEAR = "year";
		public static final String DAY = "day";
		public static final String REGION = "region";
		public static final String BIRTH_CERTIFICATE = "birthCertificate";
		public static final String ADRESSES = "addresses";
		public static final String RNOKPP = "rnokpp";
		public static final String PHONES = "phones";
		public static final String PHONE_NUMBER = "number";
		public static final String CARS = "cars";
		public static final String PLATE_NUMBER = "plateNumber";
		public static final String EMAILS = "emails";
		public static final String EMAIL_ADDRESS = "emailAddress";
		public static final String LICENSE_NUMBER = "licenseNumber";
		public static final String DRIVER_LICENSE = "driverLicense";
		public static final String FLAT_NUMBER = "flatNumber";
		public static final String HOME_NUMBER = "homeNumber";
		public static final String STREET = "street";
		public static final String CITY = "city";

		private SpecificationType(){}
	}
}
