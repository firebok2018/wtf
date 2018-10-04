package musicp.firebok.com.music.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import musicp.firebok.com.music.Config;
import musicp.firebok.com.music.R;
import musicp.firebok.com.music.cache.ImageFetcher;
import musicp.firebok.com.music.loaders.AlbumSongLoader;
import musicp.firebok.com.music.model.Song;
import musicp.firebok.com.music.ui.fragments.AlbumDetailFragment;
import musicp.firebok.com.music.utils.MusicUtils;

public abstract class AlbumDetailSongAdapter extends DetailSongAdapter {
    private AlbumDetailFragment mFragment;

    public AlbumDetailSongAdapter(Activity activity, AlbumDetailFragment fragment) {
        super(activity);
        mFragment = fragment;
    }

    protected int rowLayoutId() { return R.layout.album_detail_song; }

    protected Config.IdType getSourceType() {
        return Config.IdType.Album;
    }

    @Override // LoaderCallbacks
    public Loader<List<Song>> onCreateLoader(int id, Bundle args) {
        onLoading();
        setSourceId(args.getLong(Config.ID));
        return new AlbumSongLoader(mActivity, getSourceId());
    }

    @Override // LoaderCallbacks
    public void onLoadFinished(Loader<List<Song>> loader, List<Song> songs) {
        super.onLoadFinished(loader, songs);
        mFragment.update(songs);
    }

    protected Holder newHolder(View root, ImageFetcher fetcher) {
        return new AlbumHolder(root, fetcher, mActivity);
    }

    private static class AlbumHolder extends Holder {
        TextView duration;
        Context context;

        protected AlbumHolder(View root, ImageFetcher fetcher, Context context) {
            super(root, fetcher);
            this.context = context;
            duration = (TextView)root.findViewById(R.id.duration);
        }

        protected void update(Song song) {
            title.setText(song.mSongName);
            duration.setText(MusicUtils.makeShortTimeString(context, song.mDuration));
        }
    }
}