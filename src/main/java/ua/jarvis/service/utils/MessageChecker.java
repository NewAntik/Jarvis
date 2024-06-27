package ua.jarvis.service.utils;

import ua.jarvis.constant.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MessageChecker {

	private static String normalizedText;

	private static String[] date;

	private MessageChecker(){}

	public static String[] getDate(){
		return date;
	}

	private static boolean isContainsUnderscore(final String... strings){
		List<Boolean> answer = new ArrayList<>();
		for(String s : strings){
			answer.add(s.equals("_"));
		}

		return !answer.contains(false);
	}

	public static boolean isThreeNamesDateAndRegion(final String messageText) {
		final String[] text = messageText.split(" ", -1);

		if(isContainsUnderscore(text[0], text[1] ,text[2])){
			return false;
		}
		return text.length == 5 && isDate(text[3]) && isCyrillicStrings(text[0], text[1], text[2], text[4]);
	}

	public static boolean isSurNameNameAndDate(final String messageText){
		final String[] text = messageText.split(" ", -1);

		if (text.length == 4 && "_".equals(text[2]) && isCyrillicStrings(text[0], text[1])) {
			isDate(text[3]);
			return true;
		}

		return false;
	}


	public static boolean isSurNameMidlNameAndDate(final String messageText) {
		final String[] text = messageText.split(" ", -1);

		if (text.length == 4 && "_".equals(text[1]) && isCyrillicStrings(text[0], text[2])) {
			isDate(text[3]);
			return true;
		}
		return false;
	}

	public static boolean isUnderscoreNameMidlNameAndDate(final String messageText) {
		final String[] text = messageText.split(" ", -1);

		if (text.length == 4 && "_".equals(text[0]) && isCyrillicStrings(text[1], text[2])) {
			isDate(text[3]);
			return true;
		}
		return false;
	}

	public static boolean isNameSurNameMidlNameDate(final String messageText){
		final String[] text = messageText.split(" ", -1);
		if(isContainsUnderscore(text[0], text[1] ,text[2])){
			return false;
		}
		return text.length == 4 && isDate(text[3]) && isCyrillicStrings(text[0], text[1], text[2]);
	}

	private static boolean isDate(final String text) {
		date = null;
		final String[] parts = text.split("\\.");
		if (parts.length != 3) {
			return false;
		}
		for (String part : parts) {
			if (!part.chars().allMatch(Character::isDigit)) {
				return false;
			}
		}

		date = new String[3];
		date[0] = parts[0];
		date[1] = parts[1];
		date[2] = parts[2];

		return true;
	}

	public static boolean isSurNameAndMidlName(final String messageText) {
		final String[] surNameAndMidlName = messageText.split(" ", -1);

		return surNameAndMidlName.length == 3 &&
			"_".equals(surNameAndMidlName[1]) &&
			isCyrillicStrings(surNameAndMidlName[0], surNameAndMidlName[2]);
	}

	public static boolean isNameAndSurName(final String messageText){
		final String[] names = messageText.split(" ", -1);

		return names.length == 2 && isCyrillicStrings(names);
	}

	public static boolean isNameSurNameMidlName(final String messageText){
		final String[] names = messageText.split(" ", -1);

		return names.length == 3 && isCyrillicStrings(names);
	}

	private static boolean isCyrillicStrings(final String... strings) {
		List<Boolean> answer = new ArrayList<>();
		for(String s : strings){
			answer.add(isCyrillicString(s));
		}

		return !answer.contains(false);
	}

	private static boolean isCyrillicString(final String text){
		List<Boolean> answer = new ArrayList<>();
		int length = text.length();
		for(int i = 0; i < length; i++){
			answer.add(isCyrillicLetter(text.charAt(i)));
		}

		return !answer.contains(false);
	}

	private static boolean isCyrillicLetter(final char ch) {
		// Unicode range for Cyrillic: 0400–04FF, 0500–052F
		return (ch >= '\u0400' && ch <= '\u04FF') || (ch >= '\u0500' && ch <= '\u052F');
	}

	private static boolean containsCyrillicLetters(final String text) {
		final String twoFirstLetters = text.substring(0, 2);
		final char firstChar = twoFirstLetters.charAt(0);
		final char secondChar = twoFirstLetters.charAt(1);

		return isCyrillicLetter(firstChar) && isCyrillicLetter(secondChar);
	}

	public static boolean isInfo(final String messageText){
		return messageText.equals(Constants.BASE_INFO);
	}

	public static boolean isForeignPassport(final String messageText) {
		return messageText.length() == 8 && containsEnglishLetters(messageText);
	}

	private static boolean containsEnglishLetters(final String text) {
		final String twoFirstLetters = text.substring(0, 2);

		final char firstChar = twoFirstLetters.charAt(0);
		final char secondChar = twoFirstLetters.charAt(1);

		return Character.isLetter(firstChar) && Character.isLetter(secondChar) &&
			Character.isUpperCase(firstChar) && Character.isUpperCase(secondChar);
	}

	public static boolean isPassport(final String messageText) {
		if(messageText.length() == 8 && containsCyrillicLetters(messageText)){
			return true;
		}
		if(messageText.length() == 9 && messageText.chars().allMatch(Character::isDigit)){
			return true;
		}

		return false;
	}

	public static boolean isPhoneNumber(final String messageText) {
		normalizedText = null;
		normalizePhoneNumber(messageText);

		return normalizedText != null;
	}

	public static String getNormalizedNumber(){
		return normalizedText;
	}

	private static void normalizePhoneNumber(String number) {
		number = number.replaceAll("[^\\d]", "");

		if (number.startsWith("38")) {
			number = number.substring(2);
		} else if (number.startsWith("8")) {
			number = number.substring(1);
		}

		if (number.length() == Constants.PHONE_NUMBER_LENGTH) {
			normalizedText = number;
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
