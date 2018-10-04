package musicp.firebok.com.music.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;



import java.util.List;

/**
 * This class is used to display all of the playlists on a user's device.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class PlaylistFragment extends MusicBrowserFragment implements
        LoaderCallbacks<List<Playlist>>,
        OnItemClickListener, MusicStateListener {

    /**
     * The adapter for the list
     */
    private PlaylistAdapter mAdapter;

    /**
     * The list view
     */
    private ListView mListView;

    /**
     * Pop up menu helper
     */
    private PopupMenuHelper mPopupMenuHelper;

    /**
     * This holds the loading progress bar as well as the no results message
     */
    private LoadingEmptyContainer mLoadingEmptyContainer;

    /**
     * Empty constructor as per the {@link Fragment} documentation
     */
    public PlaylistFragment() {
    }

    @Override
    public int getLoaderId() {
        return PagerAdapter.MusicFragments.PLAYLIST.ordinal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPopupMenuHelper = new PlaylistPopupMenuHelper(getActivity(), getFragmentManager(), null) {
            @Override
            public Playlist getPlaylist(int position) {
                return mAdapter.getItem(position);
            }
        };

        // Create the adapter
        mAdapter = new PlaylistAdapter(getActivity());
        mAdapter.setPopupMenuClickedListener(new IPopupMenuCallback.IListener() {
            @Override
            public void onPopupMenuClicked(View v, int position) {
                mPopupMenuHelper.showPopupMenu(v, position);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        // The View for the fragment's UI
        final ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.list_base, null);
        // Initialize the list
        mListView = (ListView)rootView.findViewById(R.id.list_base);
        // Set the data behind the grid
        mListView.setAdapter(mAdapter);
        // Release any references to the recycled Views
        mListView.setRecyclerListener(new RecycleHolder());
        // Play the selected song
        mListView.setOnItemClickListener(this);
        // Setup the loading and empty state
        mLoadingEmptyContainer =
                (LoadingEmptyContainer)rootView.findViewById(R.id.loading_empty_container);
        mListView.setEmptyView(mLoadingEmptyContainer);

        // Register the music status listener
        ((BaseActivity)getActivity()).setMusicStateListenerListener(this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ((BaseActivity)getActivity()).removeMusicStateListenerListener(this);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Enable the options menu
        setHasOptionsMenu(true);
        // Start the loader
        initLoader(null, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position,
            final long id) {
        Playlist playlist = mAdapter.getItem(position);

        SmartPlaylistType playlistType = SmartPlaylistType.getTypeById(playlist.mPlaylistId);
        if (playlistType != null) {
            NavUtils.openSmartPlaylist(getActivity(), playlistType);
        } else {
            NavUtils.openPlaylist(getActivity(), playlist.mPlaylistId, playlist.mPlaylistName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Loader<List<Playlist>> onCreateLoader(final int id, final Bundle args) {
        // show the loading progress bar
        mLoadingEmptyContainer.showLoading();
        return new PlaylistLoader(getActivity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLoadFinished(final Loader<List<Playlist>> loader, final List<Playlist> data) {
        // Check for any errors
        if (data.isEmpty()) {
            mLoadingEmptyContainer.showNoResults();
            return;
        }

        // Start fresh
        mAdapter.unload();
        // Add the data to the adpater
        for (final Playlist playlist : data) {
            mAdapter.add(playlist);
        }
        // Build the cache
        mAdapter.buildCache();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLoaderReset(final Loader<List<Playlist>> loader) {
        // Clear the data in the adapter
        mAdapter.unload();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restartLoader() {
        restartLoader(null, this);
    }

    @Override
    public void onPlaylistChanged() {
        restartLoader();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMetaChanged() {
        // Nothing to do
    }
}
