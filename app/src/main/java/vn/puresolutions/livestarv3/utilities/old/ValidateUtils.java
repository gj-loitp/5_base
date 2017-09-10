package vn.puresolutions.livestarv3.utilities.old;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String passwordPattern = "((?=.*\\d)(?=.*[A-z]).{6,})";
    public static final String urlPattern = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isURL(String url) {
        if (url == null) {
            return false;
        }
        return url.matches(urlPattern);
    }

    public static boolean validateEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validateFileSize(String path, float maxSize) {
        File file = new File(path);
        if (file != null && file.length() > 0) {
            float fileSize = (float)file.length() / (1024 * 1024);
            if (fileSize <= maxSize) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean validateDate(String strDate, String strFormat) {
        Date date = DateUtils.stringToDate(strDate, strFormat);
        if (date != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\d{10,12}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static <V> boolean isEmpty(Collection<V> c) {
        return (c == null || c.size() == 0);
    }
}
