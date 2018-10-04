package musicp.firebok.com.music.utils;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import musicp.firebok.com.music.model.Artist;
import musicp.firebok.com.music.Config;

public abstract class ArtistPopupMenuHelper extends PopupMenuHelper {
    private Artist mArtist;

    public ArtistPopupMenuHelper(Activity activity, FragmentManager fragmentManager) {
        super(activity, fragmentManager);
        mType = PopupMenuType.Artist;
    }

    public abstract Artist getArtist(int position);

    @Override
    public PopupMenuType onPreparePopupMenu(int position) {
        mArtist = getArtist(position);
        return mArtist == null ? null : PopupMenuType.Artist;
    }

    @Override
    protected long getSourceId() {
        return mArtist.mArtistId;
    }

    @Override
    protected Config.IdType getSourceType() {
        return Config.IdType.Artist;
    }

    @Override
    protected long[] getIdList() {
        return MusicUtils.getSongListForArtist(mActivity, mArtist.mArtistId);
    }

    @Override
    protected void onDeleteClicked() {
        final String artist = mArtist.mArtistName;
        DeleteDialog.newInstance(artist, getIdList(), artist)
                .show(mFragmentManager, "DeleteDialog");
    }

    @Override
    protected String getArtistName() {
        return mArtist.mArtistName;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        boolean handled = super.onMenuItemClick(item);
        if (!handled && item.getGroupId() == getGroupId()) {
            switch (item.getItemId()) {
                case FragmentMenuItems.CHANGE_IMAGE:
                    PhotoSelectionDialog.newInstance(getArtistName(),
                            PhotoSelectionDialog.ProfileType.ARTIST, getArtistName())
                            .show(mFragmentManager, "PhotoSelectionDialog");
                    return true;
            }
        }

        return handled;
    }
}
