package musicp.firebok.com.music.model;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import java.util.Comparator;

import musicp.firebok.com.music.Config;

public class SearchResult {
    private static final String TAG = SearchResult.class.getSimpleName();

    public static final Comparator COMPARATOR = new Comparator<SearchResult>() {
        @Override
        public int compare(final SearchResult lhs, final SearchResult rhs) {
            return lhs.mType.ordinal() - rhs.mType.ordinal();
        }
    };

    public static enum ResultType {
        Song,
        Artist,
        Album,
        Playlist,
        Unknown;

        public static int getNumTypes() {
            // # of items minus the unknown
            return ResultType.values().length - 1;
        }

        public static ResultType getResultType(final String mimetype) {
            if (mimetype != null) {
                if (mimetype.equals("artist")) {
                    return Artist;
                } else if (mimetype.equals("album")) {
                    return Album;
                } else if (mimetype.startsWith("audio/") || mimetype.equals("application/ogg")
                        || mimetype.equals("application/x-ogg")) {
                    return Song;
                }
            }

            return Unknown;
        }

        public static ResultType getResultType(final Cursor cursor, int index) {
            return getResultType(cursor.getString(index));
        }

        public static ResultType getResultType(final Cursor cursor) {
            try {
                return getResultType(cursor,
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
            } catch(IllegalArgumentException ex) {
                return Unknown;
            }
        }

        public Config.IdType getSourceType() {
            switch (this) {
                case Artist:
                    return Config.IdType.Artist;
                case Album:
                    return Config.IdType.Album;
                case Playlist:
                    return Config.IdType.Playlist;
                case Song:
                default:
                    return Config.IdType.NA;
            }
        }
    };

    public ResultType mType;
    public String mArtist;
    public String mAlbum;
    public String mTitle;
    public long mId;
    public long mAlbumId;
    public int mAlbumCount;
    public int mSongCount;

    public static SearchResult createSearchResult(final Cursor cursor) {
        SearchResult result = new SearchResult();

        result.mType = ResultType.getResultType(cursor);

        // not a valid mime type - quitting
        if (result.mType == ResultType.Unknown) {
            Log.e(TAG, "No valid mime type found!");
            return null;
        }

        // Get the Id of the content
        result.mId = cursor.getLong(cursor
                .getColumnIndexOrThrow(android.provider.BaseColumns._ID));

        // title
        result.mTitle = cursor.getString(cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

        // Get the artist name
        result.mArtist = cursor.getString(cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));

        // Get the album name
        result.mAlbum = cursor.getString(cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM));

        // album count
        result.mAlbumCount = cursor.getInt(cursor.getColumnIndexOrThrow("data1"));

        // song count
        result.mSongCount = cursor.getInt(cursor.getColumnIndexOrThrow("data2"));

        return result;
    }

    public static SearchResult createPlaylistResult(final Cursor cursor) {
        SearchResult result = new SearchResult();

        result.mType = ResultType.Playlist;

        // Get the Id of the content
        result.mId = cursor.getLong(cursor
                .getColumnIndexOrThrow(android.provider.BaseColumns._ID));

        // title
        result.mTitle = cursor.getString(cursor
                .getColumnIndexOrThrow(MediaStore.Audio.PlaylistsColumns.NAME));

        return result;
    }
}
