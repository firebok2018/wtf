package musicp.firebok.com.music.lastfm;

import java.util.Map;

/**
 * Utility class to perform various operations on Maps.
 *
 * @author Adrian Woodhead
 */
public final class MapUtilities {

    private MapUtilities() {
    }

    /**
     * Puts the passed key and value into the map only if the value is not null.
     *
     * @param map Map to add key and value to.
     * @param key Map key.
     * @param value Map value, if null will not be added to map.
     */
    public static void nullSafePut(final Map<String, String> map, final String key,
            final String value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    /**
     * Puts the passed key and value into the map only if the value is not null.
     *
     * @param map Map to add key and value to.
     * @param key Map key.
     * @param value Map value, if null will not be added to map.
     */
    public static void nullSafePut(final Map<String, String> map, final String key,
            final Integer value) {
        if (value != null) {
            map.put(key, value.toString());
        }
    }

    /**
     * Puts the passed key and value into the map only if the value is not -1.
     *
     * @param map Map to add key and value to.
     * @param key Map key.
     * @param value Map value, if -1 will not be added to map.
     */
    public static void nullSafePut(final Map<String, String> map, final String key, final int value) {
        if (value != -1) {
            map.put(key, Integer.toString(value));
        }
    }

    /**
     * Puts the passed key and value into the map only if the value is not -1.
     *
     * @param map Map to add key and value to.
     * @param key Map key.
     * @param value Map value, if -1 will not be added to map.
     */
    public static void nullSafePut(final Map<String, String> map, final String key,
            final double value) {
        if (value != -1) {
            map.put(key, Double.toString(value));
        }
    }
}
