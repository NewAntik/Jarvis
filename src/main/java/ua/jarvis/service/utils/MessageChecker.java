package ua.jarvis.service.utils;

import ua.jarvis.constant.Constants;

public final class MessageChecker {

	private static String phoneNumber;

	private MessageChecker(){}

	public static boolean isInfo(final String messageText){
		return messageText.equals(Constants.BASE_INFO);
	}

	public static boolean isForeignPassport(final String messageText) {
		if (messageText.length() != 8) {
			return false;
		} else {
			final String twoFirstLetters = messageText.substring(0, 2);
			return isEnglishLetters(twoFirstLetters);
		}
	}

	private static boolean isEnglishLetters(final String text) {
		if (text.length() != 2) {
			return false;
		}
		final char firstChar = text.charAt(0);
		final char secondChar = text.charAt(1);
		return Character.isLetter(firstChar) && Character.isLetter(secondChar) &&
			Character.isUpperCase(firstChar) && Character.isUpperCase(secondChar);
	}

	public static boolean isPassport(final String messageText) {
		if (messageText.length() != 8) {
			return false;
		} else {
			final String twoFirstLetters = messageText.substring(0, 2);
			return isCyrillicLetters(twoFirstLetters);
		}
	}

	private static boolean isCyrillicLetters(final String text) {
		if (text.length() != 2) {
			return false;
		}
		final char firstChar = text.charAt(0);
		final char secondChar = text.charAt(1);

		return isCyrillicLetter(firstChar) && isCyrillicLetter(secondChar);
	}

	private static boolean isCyrillicLetter(final char ch) {
		// Unicode range for Cyrillic: 0400–04FF, 0500–052F
		return (ch >= '\u0400' && ch <= '\u04FF') || (ch >= '\u0500' && ch <= '\u052F');
	}

	public static boolean isPhoneNumber(final String messageText) {
		phoneNumber = null;
		normalizePhoneNumber(messageText);

		return phoneNumber != null;
	}

	public static String getNormalizedNumber(){
		return phoneNumber;
	}

	private static void normalizePhoneNumber(String number) {
		number = number.replaceAll("[^\\d]", "");

		if (number.startsWith("38")) {
			number = number.substring(2);
		} else if (number.startsWith("8")) {
			number = number.substring(1);
		}

		if (number.length() == Constants.PHONE_NUMBER_LENGTH) {
			phoneNumber = number;
		}
	}

	public static boolean isRnokpp(final String messageText){
		if (messageText == null) {
			return false;
		}
		if(messageText.length() != 10){
			return false;
		}
		if(messageText.startsWith("0")){
			return false;
		}

		return messageText.chars().allMatch(Character::isDigit);
	}
}
