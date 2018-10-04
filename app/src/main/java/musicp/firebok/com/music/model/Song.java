package musicp.firebok.com.music.model;

import android.text.TextUtils;

public class Song {
    public long mSongId; //Unique ID Song
    public String mSongName;//the song name
    public String mArtistName;//the song artist
    public String mAlbumName;//the song album
    public long mAlbumId;//the album id
    public int mDuration; //the song duration in seconds
    public int mYear; //the year song was recorded
    /**
     * Bucket label for the name - may not necessarily be the name - for example songs sorted by
     * artists would be the artist bucket label and not the song name bucket label
     */
    public String mBucketLabel;

    public Song(final long songId, final String songName,final String artistName,
                final String albumName, final long albumId, final int duration, final int year){
        mSongId=songId;
        mSongName=songName;
        mArtistName=artistName;
        mAlbumName=albumName;
        mAlbumId=albumId;
        mDuration=duration;
        mYear=year;
    }

    @Override
    public int hashCode() {
        //return super.hashCode();
        final int prime =31;
        int result=1;
        result = prime * result +(mAlbumName == null?0:mAlbumName.hashCode());
        result = prime * result +(int)mAlbumId;
        result = prime * result +(mArtistName == null?0:mArtistName.hashCode());
        result = prime * result +mDuration;
        result = prime * result +(int)mSongId;
        result = prime * result +(mSongName == null?0:mSongName.hashCode());
        result = prime * result +mYear;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
       // return super.equals(obj);
        if (this==obj){return true;}
        if (obj == null){return false;}
        if (getClass() != obj.getClass()){return false;}
        final Song other = (Song)obj;
        if (mSongId != other.mSongId){return false;}
        if (!TextUtils.equals(mAlbumName,other.mAlbumName)){return false;}
        if (mAlbumId != other.mAlbumId){return false;}
        if (!TextUtils.equals(mArtistName, other.mArtistName)){return false;}
        if (mDuration != other.mDuration){return false;}
        if (!TextUtils.equals(mSongName, other.mSongName)){return false;}
        if (mYear != other.mYear){return false;}
        return true;
    }

    @Override
    public String toString() {
        //return super.toString();
        return mSongName;
    }
}
