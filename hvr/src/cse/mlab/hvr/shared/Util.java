package cse.mlab.hvr.shared;

public class Util {
	public static boolean isEmailFOrmatValid(Object value) {
		if (value == null)
			return true;

		String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";

		boolean valid = false;

		if (value.getClass().toString().equals(String.class.toString())) {
			valid = ((String) value).matches(emailPattern);
		} else {
			valid = ((Object) value).toString().matches(emailPattern);
		}

		return valid;
	}

}
