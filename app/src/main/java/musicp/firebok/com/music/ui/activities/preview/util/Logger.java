package musicp.firebok.com.music.ui.activities.preview.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * <pre>
 *     Debug logging
 * </pre>
 */
public class Logger {

    private static final String TAG = "AudioPreview";

    private static boolean isDebugging() {
        return Log.isLoggable(TAG, Log.DEBUG);
    }

    /**
     * Log a debug message
     *
     * @param tag {@link String}
     * @param msg {@link String }
     *
     * @throws IllegalArgumentException {@link IllegalArgumentException}
     */
    public static void logd(String tag, String msg) throws IllegalArgumentException {
        if (TextUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("'tag' cannot be empty!");
        }
        if (TextUtils.isEmpty(msg)) {
            throw new IllegalArgumentException("'msg' cannot be empty!");
        }
        if (isDebugging()) {
            Log.d(TAG, tag + " [ " + msg + " ]");
        }
    }

    /**
     * Log a debug message
     *
     * @param tag {@link String}
     * @param msg {@link String }
     *
     * @throws IllegalArgumentException {@link IllegalArgumentException}
     */
    public static void loge(String tag, String msg) throws IllegalArgumentException {
        if (TextUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("'tag' cannot be empty!");
        }
        if (TextUtils.isEmpty(msg)) {
            throw new IllegalArgumentException("'msg' cannot be empty!");
        }
        Log.e(TAG, tag + " [ " + msg + " ]");
    }

}

