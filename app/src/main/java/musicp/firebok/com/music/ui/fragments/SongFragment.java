package musicp.firebok.com.music.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import musicp.firebok.com.music.Config;
import musicp.firebok.com.music.adapters.PagerAdapter;
import musicp.firebok.com.music.loaders.SongLoader;
import musicp.firebok.com.music.model.Song;
import musicp.firebok.com.music.sectionAdapter.SectionCreator;
import musicp.firebok.com.music.sectionAdapter.SectionListContainer;
import musicp.firebok.com.music.ui.fragments.profile.BasicSongFragment;
import musicp.firebok.com.music.utils.MusicUtils;
import musicp.firebok.com.music.utils.SectionCreatorUtils;


/**
 * This class is used to display all of the songs on a user's device.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class SongFragment extends BasicSongFragment {

    /**
     * {@inheritDoc}
     */
    public void playAll(int position) {
        int internalPosition = mAdapter.getInternalPosition(position);
        final long[] list = mAdapter.getUnderlyingAdapter().getSongIds();
        if (list != null) {
            MusicUtils.playAll(getActivity(), list, internalPosition, -1, Config.IdType.NA, false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Loader<SectionListContainer<Song>> onCreateLoader(final int id, final Bundle args) {
        // show the loading progress bar
        mLoadingEmptyContainer.showLoading();

        // get the context
        Context context = getActivity();

        // create the underlying song loader
        SongLoader songLoader = new SongLoader(context);

        // get the song comparison method to create the headers with
        SectionCreatorUtils.IItemCompare<Song> songComparison = SectionCreatorUtils.createSongComparison(context);

        // return the wrapped section creator
        return new SectionCreator<Song>(context, songLoader, songComparison);
    }


    @Override
    public int getLoaderId() {
        return PagerAdapter.MusicFragments.SONG.ordinal();
    }

    /**
     * Scrolls the list to the currently playing song when the user touches the
     * header in the {@link TitlePageIndicator}.
     */
    public void scrollToCurrentSong() {
        final int currentSongPosition = getItemPositionBySong();

        if (currentSongPosition != 0) {
            mListView.setSelection(currentSongPosition);
        }
    }

    /**
     * @return The position of an item in the list based on the name of the
     * currently playing song.
     */
    private int getItemPositionBySong() {
        final long trackId = MusicUtils.getCurrentAudioId();
        if (mAdapter == null) {
            return 0;
        }

        int position = mAdapter.getItemPosition(trackId);

        // if for some reason we don't find the item, just jump to the top
        if (position < 0) {
            return 0;
        }

        return position;
    }

    @Override
    public LoaderManager getFragmentLoaderManager() {
        return getParentFragment().getLoaderManager();
    }
}
