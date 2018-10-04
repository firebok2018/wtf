package musicp.firebok.com.music.utils;

import android.app.Activity;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;


import java.util.TreeSet;

import musicp.firebok.com.music.model.Song;

public abstract class SongPopupMenuHelper extends PopupMenuHelper {
    protected Song mSong;

    public SongPopupMenuHelper(Activity activity, FragmentManager fragmentManager) {
        super(activity, fragmentManager);
    }

    public abstract Song getSong(int position);

    @Override
    public PopupMenuHelper.PopupMenuType onPreparePopupMenu(int position) {
        mSong = getSong(position);

        if (mSong == null) {
            return null;
        }

        return PopupMenuType.Song;
    }

    @Override
    protected void playAlbum() {
        MusicUtils.playAlbum(mActivity, mSong.mAlbumId, 0, false);
    }

    @Override
    protected long[] getIdList() {
        return new long[] { mSong.mSongId };
    }

    @Override
    protected String getArtistName() {
        return mSong.mArtistName;
    }

    @Override
    protected void onDeleteClicked() {
        DeleteDialog.newInstance(mSong.mSongName, getIdList(), null)
                .show(mFragmentManager, "DeleteDialog");
    }

    @Override
    protected void updateMenuIds(PopupMenuType type, TreeSet<Integer> set) {
        super.updateMenuIds(type, set);

        // Don't show more by artist if it is an unknown artist
        if (MediaStore.UNKNOWN_STRING.equals(mSong.mArtistName)) {
            set.remove(FragmentMenuItems.MORE_BY_ARTIST);
        }
    }
}

