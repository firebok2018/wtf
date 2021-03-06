package musicp.firebok.com.music.loaders;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;

import java.util.ArrayList;
import java.util.List;

import musicp.firebok.com.music.model.Song;
import musicp.firebok.com.music.utils.Lists;
import musicp.firebok.com.music.utils.PreferenceUtils;

/**
 * Used to query {@link MediaStore.Audio.Media.EXTERNAL_CONTENT_URI} and return
 * the Song for a particular album.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
@SuppressWarnings("JavadocReference")
public class AlbumSongLoader extends WrappedAsyncTaskLoader<List<Song>> {

    /**
     * The result
     */
    private final ArrayList<Song> mSongList = Lists.newArrayList();

    /**
     * The Id of the album the songs belong to.
     */
    private final Long mAlbumID;

    /**
     * Constructor of <code>AlbumSongHandler</code>
     *
     * @param context The {@link Context} to use.
     * @param albumId The Id of the album the songs belong to.
     */
    public AlbumSongLoader(final Context context, final Long albumId) {
        super(context);
        mAlbumID = albumId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Song> loadInBackground() {
        // Create the Cursor
        Cursor cursor = makeAlbumSongCursor(getContext(), mAlbumID);
        // Gather the data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Copy the song Id
                final long id = cursor.getLong(0);

                // Copy the song name
                final String songName = cursor.getString(1);

                // Copy the artist name
                final String artist = cursor.getString(2);

                // Copy the album name
                final String album = cursor.getString(3);

                // Copy the duration
                final long duration = cursor.getLong(4);

                // Make the duration label
                final int seconds = (int) (duration / 1000);

                // Grab the Song Year
                final int year = cursor.getInt(5);

                // Create a new song
                final Song song = new Song(id, songName, artist, album, mAlbumID, seconds, year);

                // Add everything up
                mSongList.add(song);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return mSongList;
    }

    /**
     * @param context The {@link Context} to use.
     * @param albumId The Id of the album the songs belong to.
     * @return The {@link Cursor} used to run the query.
     */
    public static final Cursor makeAlbumSongCursor(final Context context, final Long albumId) {
        // Match the songs up with the artist
        String selection = (AudioColumns.IS_MUSIC + "=1") +
                " AND " + AudioColumns.TITLE + " != ''" +
                " AND " + AudioColumns.ALBUM_ID + "=" + albumId;
        return context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] {
                        /* 0 */
                        BaseColumns._ID,
                        /* 1 */
                        AudioColumns.TITLE,
                        /* 2 */
                        AudioColumns.ARTIST,
                        /* 3 */
                        AudioColumns.ALBUM,
                        /* 4 */
                        AudioColumns.DURATION,
                        /* 5 */
                        AudioColumns.YEAR,
                }, selection, null,
                PreferenceUtils.getInstance(context).getAlbumSongSortOrder());
    }

}
