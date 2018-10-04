package musicp.firebok.com.music.lastfm;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import musicp.firebok.com.music.Config;

/**
 * Wrapper class for Album related API calls and Album Bean.
 *
 * @author Janni Kovacs
 */
public class Album extends MusicEntry {

    protected final static ItemFactory<Album> FACTORY = new AlbumFactory();

    private String artist;

    /**
     * @param name
     * @param url
     * @param artist
     */
    private Album(final String name, final String url, final String artist) {
        super(name, url);
        this.artist = artist;
    }

    /**
     * Get the metadata for an album on Last.fm using the album name or a
     * musicbrainz id. See playlist.fetch on how to get the album playlist.
     *
     * @param artist Artist's name
     * @param albumOrMbid Album name or MBID
     * @return Album metadata
     */
    public final static Album getInfo(final Context context, final String artist,
            final String albumOrMbid) {
        return getInfo(context, artist, albumOrMbid, null, Config.LASTFM_API_KEY);
    }

    /**
     * Get the metadata for an album on Last.fm using the album name or a
     * musicbrainz id. See playlist.fetch on how to get the album playlist.
     *
     * @param artist Artist's name
     * @param albumOrMbid Album name or MBID
     * @param username The username for the context of the request. If supplied,
     *            the user's playcount for this album is included in the
     *            response.
     * @param apiKey The API key
     * @return Album metadata
     */
    public final static Album getInfo(final Context context, final String artist,
            final String albumOrMbid, final String username, final String apiKey) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("artist", artist);
        params.put("album", albumOrMbid);
        MapUtilities.nullSafePut(params, "username", username);
        final Result result = Caller.getInstance(context).call("album.getInfo", apiKey, params);
        return ResponseBuilder.buildItem(result, Album.class);
    }

    private final static class AlbumFactory implements ItemFactory<Album> {

        /**
         * {@inheritDoc}
         */
        @Override
        public Album createItemFromElement(final DomElement element) {
            if (element == null) {
                return null;
            }
            final Album album = new Album(null, null, null);
            MusicEntry.loadStandardInfo(album, element);
            if (element.hasChild("artist")) {
                album.artist = element.getChild("artist").getChildText("name");
                if (album.artist == null) {
                    album.artist = element.getChildText("artist");
                }
            }
            return album;
        }
    }
}
