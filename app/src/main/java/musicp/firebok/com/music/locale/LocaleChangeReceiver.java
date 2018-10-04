package musicp.firebok.com.music.locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Locale change intent receiver that invokes {@link LocalizedStore} to update
 * the database for the new locale.
 */
public class LocaleChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LocalizedStore.getInstance(context).onLocaleChanged();
    }
}
