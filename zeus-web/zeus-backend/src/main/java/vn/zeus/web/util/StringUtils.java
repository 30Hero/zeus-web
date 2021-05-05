package vn.zeus.web.util;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static String concat(String... values) {
		StringBuilder builder = new StringBuilder();
		for (String value : values) {
			builder.append(value);
		}
		return builder.toString();
	}

	public static String join(List<String> target, char separator) {
		StringBuilder builder = new StringBuilder();
		for (String str : target) {
			builder.append(str).append(separator);
		}
		return builder.deleteCharAt(builder.length() - 1).toString();
	}

	public static String replaceNamePlaceholders(String value, Map<String, Object> params, String prefix,
			String safix) {
		String result = value;
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			result = result.replace(prefix + entry.getKey() + safix, entry.getValue().toString());
		}
		return result;
	}

	/**
	 * Remove all space character in the string
	 * 
	 * @param str String
	 * @return phone number
	 */
	public static String replaceAllSpace(String str) {
		if (Objects.isNull(str)) {
			return str;
		}
		return str.trim().replaceAll("\\s+", EMPTY);
	}

	/**
	 * Convert the object to string.
	 * 
	 * @param obj Object
	 * @return if the argument is {@code null}, return empty string; otherwise, the
	 *         value of {@code obj.toString()} is returned.
	 */
	public static String covertToString(Object obj) {
		if (Objects.isNull(obj)) {
			return EMPTY;
		}
		return String.valueOf(obj).trim();
	}

	/**
	 * Un-quotes a quoted string <br>
	 * Ex: "\'reilly?" -> "'reilly?"
	 * 
	 * @param str
	 * @return
	 */
	public static String stripslashes(String str) {
		if (Objects.isNull(str)) {
			return str;
		}
		return str.replace("\\", "");
	}

	/**
	 * Format currency Viet Nam
	 * 
	 * @param number
	 * @return
	 */
	public static String formatNumberVN(Double number) {
		if (Objects.isNull(number)) {
			return null;
		}
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		return formatter.format(number).replace(",", ".");
	}

	public static String substring(final String str, int start) {
		if (str == null) {
			return EMPTY;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return EMPTY;
		}

		return str.substring(start);
	}

	public static String getUrlImageByPath(String path) {
		String[] temp = path.split("/");
		return temp[temp.length - 1];
	}

	public static boolean isEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}
}
