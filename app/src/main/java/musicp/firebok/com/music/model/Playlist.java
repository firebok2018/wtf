package musicp.firebok.com.music.model;

import android.text.TextUtils;

public class Playlist {

    /**
     * The unique Id of the playlist
     */
    public long mPlaylistId;

    /**
     * The playlist name
     */
    public String mPlaylistName;

    /**
     * The number of songs in this playlist
     */
    public int mSongCount;

    /**
     * Constructor of <code>Genre</code>
     *
     * @param playlistId The Id of the playlist
     * @param playlistName The playlist name
     */
    public Playlist(final long playlistId, final String playlistName, final int songCount) {
        super();
        mPlaylistId = playlistId;
        mPlaylistName = playlistName;
        mSongCount = songCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) mPlaylistId;
        result = prime * result + (mPlaylistName == null ? 0 : mPlaylistName.hashCode());
        result = prime * result + mSongCount;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Playlist other = (Playlist)obj;
        if (mPlaylistId != other.mPlaylistId) {
            return false;
        }

        if (mSongCount != other.mSongCount) {
            return false;
        }

        return TextUtils.equals(mPlaylistName, other.mPlaylistName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return mPlaylistName;
    }

    /**
     * @return true if this is a smart playlist
     */
    public boolean isSmartPlaylist() {
        return mPlaylistId < 0;
    }
}
