package ua.jarvis.constant;

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

	public static final class ExecuterType{
		public static final String NAME_SUR_NAME_MIDL_NAME = "Name, sur name, midl name";
		public static final String INFO = "Info";
		public static final String RNOKPP = "Rnokpp";
		public static final String PHONE_NUMBER = "Phone number";
		public static final String PASSPORT = "Passport";
		public static final String FOREIGN_PASSPORT = "Foreign passport";
		public static final String SUR_NAME_NAME = "Sur name, name";
		public static final String SUR_NAME_MIDL_NAME = "Sur name, midl name";
		public static final String NAME_SUR_NAME_MIDL_NAME_DATE = "Name, sur name, midl name, date";
		public static final String UNDERSCORE_NAME_MIDL_NAME_DATE = "_, name, midl name, date";
		public static final String SUR_NAME_UNDERSCORE_MIDL_NAME_DATE = "Sur name, _, midl name, date";
		public static final String NAME_SUR_NAME_UNDERSCORE_DATE = "name, sur name, _, date" ;
		public static final String THREE_NAMES_DATE_REGION = "Three names, date, region.";

		private ExecuterType(){}

	}
}
