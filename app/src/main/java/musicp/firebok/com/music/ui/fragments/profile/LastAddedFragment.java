package musicp.firebok.com.music.ui.fragments.profile;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * This class is used to display all of the songs the user put on their device
 * within the last four weeks.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class LastAddedFragment extends SmartPlaylistFragment implements ISetupActionBar {

    /**
     * {@inheritDoc}
     */
    @Override
    public Loader<SectionListContainer<Song>> onCreateLoader(final int id, final Bundle args) {
        // show the loading progress bar
        mLoadingEmptyContainer.showLoading();

        LastAddedLoader loader = new LastAddedLoader(getActivity());
        return new SectionCreator<Song>(getActivity(), loader, null);
    }

    @Override
    public void setupNoResultsContainer(NoResultsContainer empty) {
        super.setupNoResultsContainer(empty);

        empty.setMainText(R.string.empty_last_added_main);
        empty.setSecondaryText(R.string.empty_last_added);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        setupActionBar();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setupActionBar() {
        ((BaseActivity)getActivity()).setupActionBar(R.string.playlist_last_added);
        ((BaseActivity)getActivity()).setActionBarElevation(true);
    }

    @Override
    protected long getFragmentSourceId() {
        return Config.SmartPlaylistType.LastAdded.mId;
    }

    protected SmartPlaylistType getSmartPlaylistType() {
        return Config.SmartPlaylistType.LastAdded;
    }

    @Override
    protected int getShuffleTitleId() { return R.string.menu_shuffle_last_added; }

    @Override
    protected int getClearTitleId() { return R.string.clear_last_added; }

    @Override
    protected void clearList() { MusicUtils.clearLastAdded(getActivity()); }
}