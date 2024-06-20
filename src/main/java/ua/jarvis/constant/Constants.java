package ua.jarvis.constant;

public final class Constants {

	private Constants() {}

	public static final int PHONE_NUMBER_LENGTH = 10;
	public static final String DOCUMENT_METHOD_WAS_CALLED_BY = "Received user info document method was called by: {}";
	public static final String BASE_INFO = "/info";

	public final class UAMessages{
		private UAMessages() {}

		public static final String HAVE_NO_ACCESS = "Ви не маєте доступу для використання бота!";
		public static final String INFO_NOT_PRESENT_MESSAGE = "Дана інформація відсутня!";
		public static final String COMMAND_NOT_FOUND_MESSAGE = "Не правильно введена комманда!" +
			" Введіть /info для отримання інформації про можливості бота або зверніться до сервісної підтримки.";

	}

	public final class ExecuterType{
		public static final String NAME_SUR_NAME_MIDL_NAME = "Name SurName MidlName";
		public static final String INFO = "Info";
		public static final String RNOKPP = "Rnokpp";
		public static final String PHONE_NUMBER = "Phone number";
		public static final String PASSPORT = "Passport";
		public static final String FOREIGN_PASSPORT = "Foreign passport";

		private ExecuterType(){}

	}
}
