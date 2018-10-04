package musicp.firebok.com.music.ui.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import musicp.firebok.com.music.utils.MusicUtils;
import musicp.firebok.com.music.utils.PreferenceUtils;
/**
 * Settings.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // UP
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Add the preferences
        addPreferencesFromResource(R.xml.settings);

        // Removes the cache entries
        deleteCache();

        PreferenceUtils.getInstance(this).setOnSharedPreferenceChangeListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Removes all of the cache entries.
     */
    private void deleteCache() {
        final Preference deleteCache = findPreference("delete_cache");
        deleteCache.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                new AlertDialog.Builder(SettingsActivity.this).setMessage(R.string.delete_warning)
                        .setPositiveButton(android.R.string.ok, new OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                ImageFetcher.getInstance(SettingsActivity.this).clearCaches();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
                return true;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals(PreferenceUtils.SHOW_VISUALIZER) &&
                sharedPreferences.getBoolean(key, false) && !PreferenceUtils.canRecordAudio(this)) {
            PreferenceUtils.requestRecordAudio(this);
        } if (key.equals(PreferenceUtils.SHAKE_TO_PLAY)) {
            MusicUtils.setShakeToPlayEnabled(sharedPreferences.getBoolean(key, false));
        } else if (key.equals(PreferenceUtils.SHOW_ALBUM_ART_ON_LOCKSCREEN)) {
            MusicUtils.setShowAlbumArtOnLockscreen(sharedPreferences.getBoolean(key, true));
        }
    }
}

