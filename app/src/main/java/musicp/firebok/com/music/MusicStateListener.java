package musicp.firebok.com.music;

public interface MusicStateListener {

    /**
     * Called when {@link MusicPlaybackService#REFRESH} is invoked
     */
    public void restartLoader();

    /**
     * Called when {@link MusicPlaybackService#PLAYLIST_CHANGED} is invoked
     */
    public void onPlaylistChanged();

    /**
     * Called when {@link MusicPlaybackService#META_CHANGED} is invoked
     */
    public void onMetaChanged();

}
