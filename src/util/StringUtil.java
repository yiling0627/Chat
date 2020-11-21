package util;

public class StringUtil {
    /*
     * judge if string is null
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) { // trim()
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str) {
        if (str != null && !"".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotMatch(String str1, String str2) {
        if (str1.equals(str2)) {
            return false;
        } else {
            return true;
        }
    }
}
