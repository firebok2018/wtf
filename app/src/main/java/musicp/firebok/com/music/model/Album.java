package musicp.firebok.com.music.model;

import android.text.TextUtils;

public class Album {
    public long mAlbumId;
    public String mAlbumName;
    public String mArtistName;
    public int mSongNumber;
    public String mYear;
    public String mBucketLabel;

    public Album(final long albumId, final String albumName, final String artistName,
                 final int songNumber,final  String albumyear){
        super();
        mAlbumId = albumId;
        mAlbumName = albumName;
        mArtistName = artistName;
        mSongNumber = songNumber;
        mYear=albumyear;
    }

    @Override
    public int hashCode() {
        //return super.hashCode();
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) mAlbumId;
        result = prime * result + (mAlbumName == null ? 0 : mAlbumName.hashCode());
        result = prime * result + (mArtistName == null ? 0 : mArtistName.hashCode());
        result = prime * result + mSongNumber;
        result = prime * result + (mYear == null ? 0 : mYear.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Album other = (Album)obj;
        if (mAlbumId != other.mAlbumId) {
            return false;
        }
        if (!TextUtils.equals(mAlbumName, other.mAlbumName)) {
            return false;
        }
        if (!TextUtils.equals(mArtistName, other.mArtistName)) {
            return false;
        }
        if (mSongNumber != other.mSongNumber) {
            return false;
        }
        if (!TextUtils.equals(mYear, other.mYear)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return super.toString();
        return mAlbumName;
    }
}
