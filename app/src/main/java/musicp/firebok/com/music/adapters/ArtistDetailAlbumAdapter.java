package musicp.firebok.com.music.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import musicp.firebok.com.music.Config;
import musicp.firebok.com.music.R;
import musicp.firebok.com.music.cache.ImageFetcher;
import musicp.firebok.com.music.loaders.AlbumLoader;
import musicp.firebok.com.music.model.Album;
import musicp.firebok.com.music.utils.ElevenUtils;
import musicp.firebok.com.music.utils.NavUtils;
import musicp.firebok.com.music.widgets.IPopupMenuCallback;
import musicp.firebok.com.music.widgets.PopupMenuButton;
import musicp.firebok.com.music.sectionAdapter.SectionAdapter;

public class ArtistDetailAlbumAdapter
        extends RecyclerView.Adapter<ArtistDetailAlbumAdapter.ViewHolder>
        implements LoaderCallbacks<List<Album>>, IPopupMenuCallback {
    private static final int TYPE_FIRST = 1;
    private static final int TYPE_MIDDLE = 2;
    private static final int TYPE_LAST = 3;

    private final Activity mActivity;
    private final ImageFetcher mImageFetcher;
    private final LayoutInflater mInflater;
    private List<Album> mAlbums = Collections.emptyList();
    private IListener mListener;
    private int mListMargin;

    public ArtistDetailAlbumAdapter(final Activity activity) {
        mActivity = activity;
        mImageFetcher = ElevenUtils.getImageFetcher(activity);
        mInflater = LayoutInflater.from(activity);
        mListMargin = activity.getResources().
                getDimensionPixelSize(R.dimen.list_item_general_margin);
    }

    @Override
    public int getItemViewType(int position) {
        // use view types to distinguish first and last elements
        // so they can be given special treatment for layout
        if(position == 0) { return TYPE_FIRST; }
        else if(position == getItemCount()-1) { return TYPE_LAST; }
        else return TYPE_MIDDLE;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.artist_detail_album, parent, false);
        // add extra margin to the first and last elements
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)v.getLayoutParams();
        if     (viewType == TYPE_FIRST) { params.leftMargin = mListMargin; }
        else if(viewType == TYPE_LAST)  { params.rightMargin = mListMargin; }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album a = mAlbums.get(position);
        holder.title.setText(a.mAlbumName);
        holder.year.setText(a.mYear);
        mImageFetcher.loadAlbumImage(
                a.mArtistName, a.mAlbumName, a.mAlbumId, holder.art);
        holder.popupbutton.setPopupMenuClickedListener(mListener);
        holder.popupbutton.setPosition(position);
        addAction(holder.itemView, a);
    }

    private void addAction(View view, final Album album) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.openAlbumProfile(
                        mActivity, album.mAlbumName, album.mArtistName, album.mAlbumId);
            }
        });
    }

    @Override
    public int getItemCount() { return mAlbums.size(); }

    public Album getItem(int position) {
        return mAlbums.get(position);
    }

    @Override
    public void setPopupMenuClickedListener(IListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView art;
        public TextView title;
        public TextView year;
        public PopupMenuButton popupbutton;
        public ViewHolder(View root) {
            super(root);
            art = (ImageView)root.findViewById(R.id.album_art);
            title = (TextView)root.findViewById(R.id.title);
            year = (TextView)root.findViewById(R.id.year);
            popupbutton = (PopupMenuButton)root.findViewById(R.id.overflow);
        }
    }

    @Override // LoaderCallbacks
    public Loader<List<Album>> onCreateLoader(int id, Bundle args) {
        return new AlbumLoader(mActivity, args.getLong(Config.ID));
    }

    @Override // LoaderCallbacks
    public void onLoadFinished(Loader<List<Album>> loader, List<Album> albums) {
        if (albums.isEmpty()) { return; }
        mAlbums = albums;
        notifyDataSetChanged();
    }

    @Override // LoaderCallbacks
    public void onLoaderReset(Loader<List<Album>> loader) {
        mAlbums = Collections.emptyList();
        notifyDataSetChanged();
        mImageFetcher.flush();
    }
}