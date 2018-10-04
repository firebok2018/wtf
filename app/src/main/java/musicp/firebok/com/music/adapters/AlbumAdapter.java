

package musicp.firebok.com.music.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

import musicp.firebok.com.music.R;
import musicp.firebok.com.music.cache.ImageFetcher;
import musicp.firebok.com.music.model.Album;
import musicp.firebok.com.music.ui.MusicHolder;
import musicp.firebok.com.music.utils.ElevenUtils;
import musicp.firebok.com.music.widgets.IPopupMenuCallback;
import musicp.firebok.com.music.widgets.IPopupMenuCallback.IListener;


public class AlbumAdapter extends BaseAdapter implements IPopupMenuCallback {
    /**
     * The resource Id of the layout to inflate
     */
    private final int mLayoutId;

    /**
     * Image cache and image fetcher
     */
    private final ImageFetcher mImageFetcher;

    /**
     * Used to cache the album info
     */
    private MusicHolder.DataHolder[] mData = new MusicHolder.DataHolder[0];
    private List<Album> mAlbums = Collections.emptyList();

    /**
     * Used to listen to the pop up menu callbacks
     */
    private IPopupMenuCallback.IListener mListener;

    /** number of columns of containing grid view,
     *  used to determine which views to pad */
    private int mColumns;
    private int mPadding;

    private Context mContext;


    public AlbumAdapter(final Activity context, final int layoutId) {
        mContext = context;
        // Get the layout Id
        mLayoutId = layoutId;
        // Initialize the cache & image fetcher
        mImageFetcher = ElevenUtils.getImageFetcher(context);
        mPadding = context.getResources().getDimensionPixelSize(R.dimen.list_item_general_margin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // Recycle ViewHolder's items
        MusicHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
            holder = new MusicHolder(convertView);
            convertView.setTag(holder);
            // set the pop up menu listener
            holder.mPopupMenuButton.get().setPopupMenuClickedListener(mListener);
        } else {
            holder = (MusicHolder)convertView.getTag();
        }

        adjustPadding(position, convertView);

        // Retrieve the data holder
        final MusicHolder.DataHolder dataHolder = mData[position];

        // Sets the position each time because of recycling
        holder.mPopupMenuButton.get().setPosition(position);
        // Set each album name (line one)
        holder.mLineOne.get().setText(dataHolder.mLineOne);
        // Set the artist name (line two)
        holder.mLineTwo.get().setText(dataHolder.mLineTwo);
        // Asynchronously load the album images into the adapter
        mImageFetcher.loadAlbumImage(
                dataHolder.mLineTwo, dataHolder.mLineOne,
                dataHolder.mItemId, holder.mImage.get());

        return convertView;
    }

    private void adjustPadding(final int position, View convertView) {
        if (position < mColumns) {
            // first row
            convertView.setPadding(0, mPadding, 0, 0);
            return;
        }
        int count = getCount();
        int footers = count % mColumns;
        if (footers == 0) { footers = mColumns; }
        if (position >= (count-footers)) {
            // last row
            convertView.setPadding(0, 0, 0, mPadding);
        } else {
            // middle rows
            convertView.setPadding(0, 0 ,0, 0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return mAlbums.size();
    }

    @Override
    public Album getItem(int pos) {
        return mAlbums.get(pos);
    }

    @Override
    public long getItemId(int pos) { return pos; }

    /**
     * Method used to cache the data used to populate the list or grid. The idea
     * is to cache everything before {@code #getView(int, View, ViewGroup)} is
     * called.
     */
    public void buildCache() {
        mData = new MusicHolder.DataHolder[mAlbums.size()];
        int i = 0;
        for (Album album : mAlbums) {
            mData[i] = new MusicHolder.DataHolder();
            mData[i].mItemId = album.mAlbumId;
            mData[i].mLineOne = album.mAlbumName;
            mData[i].mLineTwo = album.mArtistName;
            i++;
        }
    }

    public void setData(List<Album> albums) {
        mAlbums = albums;
        buildCache();
        notifyDataSetChanged();
    }

    public void setNumColumns(int columns) {
        mColumns = columns;
    }

    public void unload() {
        setData(Collections.<Album>emptyList());
    }

    /**
     * @param pause True to temporarily pause the disk cache, false otherwise.
     */
    public void setPauseDiskCache(final boolean pause) {
        if (mImageFetcher != null) {
            mImageFetcher.setPauseDiskCache(pause);
        }
    }

    /**
     * @param album The key used to find the cached album to remove
     */
    public void removeFromCache(final Album album) {
        if (mImageFetcher != null) {
            mImageFetcher.removeFromCache(
                    ImageFetcher.generateAlbumCacheKey(album.mAlbumName, album.mArtistName));
        }
    }

    /**
     * Flushes the disk cache.
     */
    public void flush() {
        mImageFetcher.flush();
    }

    /**
     * Gets the item position for a given id
     * @param id identifies the object
     * @return the position if found, -1 otherwise
     */
    public int getItemPosition(long id) {
        int i = 0;
        for (Album album : mAlbums) {
            if (album.mAlbumId == id) {
                return i;
            }
            i++;
        }

        return -1;
    }

    @Override
    public void setPopupMenuClickedListener(IPopupMenuCallback.IListener listener) {
        mListener = listener;
    }
}