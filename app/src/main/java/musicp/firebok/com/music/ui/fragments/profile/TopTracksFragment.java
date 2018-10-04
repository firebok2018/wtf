package musicp.firebok.com.music.ui.fragments.profile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/**
 * This class is used to display all of the songs the user put on their device
 * within the last four weeks.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class TopTracksFragment extends SmartPlaylistFragment
implements ISetupActionBar {

    @Override
    protected SmartPlaylistType getSmartPlaylistType() {
        return Config.SmartPlaylistType.TopTracks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Loader<SectionListContainer<Song>> onCreateLoader(final int id, final Bundle args) {
        // show the loading progress bar
        mLoadingEmptyContainer.showLoading();

        TopTracksLoader loader = new TopTracksLoader(getActivity(),
                TopTracksLoader.QueryType.TopTracks);
        return new SectionCreator<Song>(getActivity(), loader, null);
    }

    @Override
    protected SongAdapter createAdapter() {
        return new TopTracksAdapter(
            getActivity(),
            R.layout.list_item_top_tracks
        );
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        setupActionBar();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setupActionBar() {
        ((BaseActivity)getActivity()).setupActionBar(R.string.playlist_top_tracks);
        ((BaseActivity)getActivity()).setActionBarElevation(true);
    }

    public class TopTracksAdapter extends SongAdapter {
        public TopTracksAdapter (final Activity context, final int layoutId) {
            super(context, layoutId, getFragmentSourceId(), getFragmentSourceType());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView positionText = (TextView) view.findViewById(R.id.position_number);
            positionText.setText(String.valueOf(position + 1));
            return view;
        }
    }

    @Override
    public void setupNoResultsContainer(NoResultsContainer empty) {
        super.setupNoResultsContainer(empty);

        empty.setMainText(R.string.empty_top_tracks_main);
        empty.setSecondaryText(R.string.empty_top_tracks_secondary);
    }

    @Override
    protected long getFragmentSourceId() {
        return Config.SmartPlaylistType.TopTracks.mId;
    }

    protected int getShuffleTitleId() { return R.string.menu_shuffle_top_tracks; }

    @Override
    protected int getClearTitleId() { return R.string.clear_top_tracks_title; }

    @Override
    protected void clearList() { MusicUtils.clearTopTracks(getActivity()); }
}