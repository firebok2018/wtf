package musicp.firebok.com.music.appwidgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import musicp.firebok.com.music.MusicPlaybackService;


public abstract class AppWidgetBase extends AppWidgetProvider {

    protected PendingIntent buildPendingIntent(Context context, final String action,
                                               final ComponentName serviceName) {
        Intent intent = new Intent(action);
        intent.setComponent(serviceName);
        return PendingIntent.getService(context, 0, intent, 0);
    }

}
