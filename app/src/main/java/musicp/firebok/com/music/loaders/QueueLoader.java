package musicp.firebok.com.music.loaders;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import musicp.firebok.com.music.model.Song;
import musicp.firebok.com.music.utils.Lists;

/**
 * Used to return the current playlist or queue.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class QueueLoader extends WrappedAsyncTaskLoader<List<Song>> {

    /**
     * The result
     */
    private final ArrayList<Song> mSongList = Lists.newArrayList();

    /**
     * Constructor of <code>QueueLoader</code>
     *
     * @param context The {@link Context} to use
     */
    public QueueLoader(final Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Song> loadInBackground() {
        // Create the Cursor
        NowPlayingCursor cursor = new NowPlayingCursor(getContext());
        // Gather the data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Copy the song Id
                final long id = cursor.getLong(0);

                // Copy the song name
                final String songName = cursor.getString(1);

                // Copy the artist name
                final String artist = cursor.getString(2);

                // Copy the album id
                final long albumId = cursor.getLong(3);

                // Copy the album name
                final String album = cursor.getString(4);

                // Copy the duration
                final long duration = cursor.getLong(5);

                // Convert the duration into seconds
                final int durationInSecs = (int) duration / 1000;

                // Copy the year
                final int year = cursor.getInt(6);

                // Create a new song
                final Song song = new Song(id, songName, artist, album, albumId, durationInSecs, year);

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
     * Creates the {@link Cursor} used to run the query.
     *
     * @param context The {@link Context} to use.
     * @return The {@link Cursor} used to run the song query.
     */
    public static final Cursor makeQueueCursor(final Context context) {
        return new NowPlayingCursor(context);
    }
}
