package musicp.firebok.com.music.menu;

/**
 * Several of the context menu items used in Eleven are reused. This class helps
 * keep things tidy. The integer values of the items are used both as the menu IDs
 * _and_ to determine the sort order of the items.
 */
public interface FragmentMenuItems {
    int PLAY_SELECTION      =  10; // play the selected song, album, etc.
    int PLAY_NEXT           =  20; // queue a track to be played next
    int PLAY_ALBUM          =  25; // play the album that this track belongs to
    //  SHUFFLE             =  30  // defined in res/menu
    int ADD_TO_QUEUE        =  40; // add to end of current queue
    int ADD_TO_PLAYLIST     =  50; // append to a playlist
    int REMOVE_FROM_QUEUE   =  60; // remove track from play queue
    int REMOVE_FROM_PLAYLIST=  70; // remove track from playlist
    int REMOVE_FROM_RECENT  =  80; // remove track from recently played list
    int RENAME_PLAYLIST     =  90; // change name of playlist
    int MORE_BY_ARTIST      = 100; // jump to artist detail page
    int USE_AS_RINGTONE     = 110; // set track as ringtone
    int DELETE              = 120; // delete track from device
    int NEW_PLAYLIST        = 130; // create new playlist - also in res/menu!
    int PLAYLIST_SELECTED   = 140; // this is used for existing playlists
    int CHANGE_IMAGE        = 150; // set new art for artist/album

    // not currently in use
    int FETCH_ARTIST_IMAGE  = 200;
    int FETCH_ALBUM_ART     = 210;
}