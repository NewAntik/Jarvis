package ua.jarvis.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PatternChecker {
	private static final Pattern CAR_PLATE_PATTERN = Pattern.compile("^[А-Яа-я]{2}\\d{4}[А-Яа-я]{2}$");

	public static boolean isCarPlatePattern(final String messageText){
		if (messageText == null || messageText.length() != 8) {
			return false;
		}

		final Matcher matcher = CAR_PLATE_PATTERN.matcher(messageText);
		return matcher.matches();
	}
}
