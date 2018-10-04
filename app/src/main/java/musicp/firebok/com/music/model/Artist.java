package musicp.firebok.com.music.model;


import android.text.TextUtils;

public class Artist {

    public  long mArtistId;
    public  String mArtistName;
    public  int mAlbumNumber;
    public  int mSongNumber;
    public  String mBucketLabel;

    public Artist(final  long artistId, final String artistName, final int albumNumber,
                  final int songNumber){
        super();
        mArtistId=artistId;
        mArtistName=artistName;
        mSongNumber=songNumber;
        mAlbumNumber=albumNumber;
    }

    @Override
    public int hashCode() {
        //return super.hashCode();
        final int prime = 31;
        int result =1;
        result = prime*result +mAlbumNumber;
        result = prime*result +(int)mArtistId;
        result = prime*result +(mArtistName == null ? 0 : mArtistName.hashCode());
        result = prime*result +mSongNumber;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
       // return super.equals(obj);
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Artist other = (Artist)obj;
        if (mAlbumNumber != other.mAlbumNumber) {
            return false;
        }
        if (mArtistId != other.mArtistId) {
            return false;
        }
        if (!TextUtils.equals(mArtistName, other.mArtistName)) {
            return false;
        }
        if (mSongNumber != other.mSongNumber) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return super.toString();
        return mArtistName;
    }
}
