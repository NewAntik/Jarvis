package ua.jarvis.service.utils;

import ua.jarvis.core.constant.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class MessageChecker {

	private static String normalizedText;

	private static String[] date;

	private static String[] address;

	private MessageChecker(){}

	public static String[] getDate(){
		return date;
	}

	public static boolean isUpdateDatabase(final String messageText){
		return messageText.equals("Update Database");
	}

	public static boolean isAddress(final String messageText) {
		final String[] text = messageText.split(" ", -1);
		if(text.length >= 6){
			correctAddress(messageText);
			return true;
		}
		return false;
	}

	public static String[] getCorrectedAddress(){
		return address;
	}

	private static void correctAddress(final String address) {
		final String startMarker = "вул. ";
		final String endMarker = " б.";
		final int startIndex = address.indexOf(startMarker) + startMarker.length();
		final int endIndex = address.indexOf(endMarker);

		final String streetName = address.substring(startIndex, endIndex).trim();
		final String[] streetNameArray = streetName.split(" ", -1);
		if(streetNameArray.length > 1){
			MessageChecker.address = getAddressWithCorrectedStreet(address, streetNameArray, streetName);
		}else{
			MessageChecker.address = address.split(" ", -1);
		}
	}

	private static String[] getAddressWithCorrectedStreet(
		final String address,
		final String[] streetNameArray,
		final String streetName
		){
		final String[] addressArray = address.split(" ", -1);
		List<String> addresList = new LinkedList<>();
		if(addressArray.length - streetNameArray.length > 6){
			addresList.add(addressArray[1]);
			addresList.add(streetName);
			addresList.add(addressArray[4 + streetNameArray.length]);
			addresList.add(addressArray[6 + streetNameArray.length]);

		} else {
			addresList.add(addressArray[1]);
			addresList.add(streetName);
			addresList.add(addressArray[4 + streetNameArray.length]);
		}

		String[] clearAddress  = new String[addresList.size()];
		addresList.forEach(s-> clearAddress[addresList.indexOf(s)] = s);

		return clearAddress;
	}

	public static boolean isSurNameAndNameAndRegion(final String messageText){
		final String[] text = messageText.split(" ", -1);

		return text.length == 4 && Objects.equals(text[2], "_") && isCyrillicStrings(text[0], text[1], text[3]);
	}

	public static boolean isThreeNamesAndRegion(final String messageText){
		final String[] text = messageText.split(" ", -1);

		return text.length == 4 && isCyrillicStrings(text);
	}

	public static boolean isCarPlateNumber(final String messageText) {
		if(messageText.length() != 8 ||
			!isCyrillicStrings(messageText.substring(0,2), messageText.substring(6 ,8))
		){
			return false;
		}

		return messageText.substring(2, 6).chars().allMatch(Character::isDigit);
	}

	public static boolean isSurNameNameDataRegion(final String messageText){
		final String[] text = messageText.split(" ", -1);

		if(text.length != 5 || !text[2].equals("_")){
			return false;
		}

		return isDate(text[3]) && isCyrillicStrings(text[0], text[1], text[4]);
	}

	public static boolean isThreeNamesDateAndRegion(final String messageText) {
		final String[] text = messageText.split(" ", -1);

		if(text.length != 5 || isContainsUnderscore(text[0], text[1] ,text[2])){
			return false;
		}
		return isDate(text[3]) && isCyrillicStrings(text[0], text[1], text[2], text[4]);
	}

	public static boolean isSurNameNameAndDate(final String messageText){
		final String[] text = messageText.split(" ", -1);

		return text.length == 4 && "_".equals(text[2]) && isCyrillicStrings(text[0], text[1]) && isDate(text[3]);
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

		if(text.length != 4 || isContainsUnderscore(text[0], text[1] ,text[2])){
			return false;
		}
		return isDate(text[3]) && isCyrillicStrings(text[0], text[1], text[2]);
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

	public static boolean isPassport(final String messageText) {
		final String[] parts = messageText.split(" ", -1);

		if(messageText.length() == 8 &&
			parts.length == 1 &&
			isFirstTwoCharsCyrillic(messageText) &&
			messageText.substring(2, 8).chars().allMatch(Character::isDigit)
		){
			return true;
		}

		return messageText.length() == 9 && messageText.chars().allMatch(Character::isDigit);
	}

	public static boolean isInfo(final String messageText){
		return messageText.equals(Constants.BASE_INFO);
	}

	public static boolean isForeignPassport(final String messageText) {
		return messageText.length() == 8 && !isFirstTwoCharsCyrillic(messageText);
	}

	public static boolean isPhoneNumber(final String messageText) {
		normalizedText = null;
		normalizePhoneNumber(messageText);

		return normalizedText != null;
	}

	public static String getNormalizedNumber(){
		return normalizedText;
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

	private static boolean isCyrillicLetter(final char ch) {
		// Unicode range for Cyrillic: 0400–04FF, 0500–052F
		return (ch >= '\u0400' && ch <= '\u04FF') || (ch >= '\u0500' && ch <= '\u052F');
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

	private static boolean isFirstTwoCharsCyrillic(final String text) {
		final String twoFirstLetters = text.substring(0, 2);
		final char firstChar = twoFirstLetters.charAt(0);
		final char secondChar = twoFirstLetters.charAt(1);

		return isCyrillicLetter(firstChar) && isCyrillicLetter(secondChar);
	}

	private static boolean isCyrillicString(final String text){
		List<Boolean> answer = new ArrayList<>();
		int length = text.length();
		for(int i = 0; i < length; i++){
			answer.add(isCyrillicLetter(text.charAt(i)));
		}

		return !answer.contains(false);
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

	private static boolean isContainsUnderscore(final String... strings){
		List<Boolean> answer = new ArrayList<>();
		for(String s : strings){
			answer.add(s.equals("_"));
		}

		return !answer.contains(false);
	}
}
