package musicp.firebok.com.music.lastfm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utilitiy class with methods to calculate an md5 hash and to encode URLs.
 *
 * @author Janni Kovacs
 */
public final class StringUtilities {

    private static MessageDigest mDigest;

    private final static Pattern MD5_PATTERN = Pattern.compile("[a-fA-F0-9]{32}");

    static {
        try {
            mDigest = MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException ignored) {
        }
    }

    /**
     * Returns a 32 chararacter hexadecimal representation of an MD5 hash of the
     * given String.
     *
     * @param s the String to hash
     * @return the md5 hash
     */
    public final static String md5(final String s) {
        try {
            final byte[] mBytes = mDigest.digest(s.getBytes("UTF-8"));
            final StringBuilder mBuilder = new StringBuilder(32);
            for (final byte aByte : mBytes) {
                final String mHex = Integer.toHexString(aByte & 0xFF);
                if (mHex.length() == 1) {
                    mBuilder.append('0');
                }
                mBuilder.append(mHex);
            }
            return mBuilder.toString();
        } catch (final UnsupportedEncodingException ignored) {
        }
        return null;
    }

    /**
     * URL Encodes the given String <code>s</code> using the UTF-8 character
     * encoding.
     *
     * @param s a String
     * @return url encoded string
     */
    public static String encode(final String s) {
        if (s == null) {
            return null;
        }
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (final UnsupportedEncodingException ignored) {
        }
        return null;
    }

    /**
     * Decodes an URL encoded String <code>s</code> using the UTF-8 character
     * encoding.
     *
     * @param s an encoded String
     * @return the decoded String
     */
    public static String decode(final String s) {
        if (s == null) {
            return null;
        }
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (final UnsupportedEncodingException ignored) {
        }
        return null;
    }

    /**
     * Creates a Map out of an array with Strings.
     *
     * @param strings input strings, key-value alternating
     * @return a parameter map
     */
    public static Map<String, String> map(final String... strings) {
        if (strings.length % 2 != 0) {
            throw new IllegalArgumentException("strings.length % 2 != 0");
        }
        final Map<String, String> sMap = new HashMap<String, String>();
        for (int i = 0; i < strings.length; i += 2) {
            sMap.put(strings[i], strings[i + 1]);
        }
        return sMap;
    }

    /**
     * Strips all characters from a String, that might be invalid to be used in
     * file names. By default <tt>: / \ < > | ? " *</tt> are all replaced by
     * <tt>-</tt>. Note that this is no guarantee that the returned name will be
     * definately valid.
     *
     * @param s the String to clean up
     * @return the cleaned up String
     */
    public static String cleanUp(final String s) {
        return s.replaceAll("[*:/\\\\?|<>\"]", "-");
    }

    /**
     * Tests if the given string <i>might</i> already be a 32-char md5 string.
     *
     * @param s String to test
     * @return <code>true</code> if the given String might be a md5 string
     */
    public static boolean isMD5(final String s) {
        return s.length() == 32 && MD5_PATTERN.matcher(s).matches();
    }

    /**
     * Converts a Last.fm boolean result string to a boolean.
     *
     * @param resultString A Last.fm boolean result string.
     * @return <code>true</code> if the given String represents a true,
     *         <code>false</code> otherwise.
     */
    public static boolean convertToBoolean(final String resultString) {
        return "1".equals(resultString);
    }

    /**
     * Converts from a boolean to a Last.fm boolean result string.
     *
     * @param value A boolean value.
     * @return A string representing a Last.fm boolean.
     */
    public static String convertFromBoolean(final boolean value) {
        if (value) {
            return "1";
        } else {
            return "0";
        }
    }

}
