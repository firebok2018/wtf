package musicp.firebok.com.music.lastfm;

import android.content.Context;

import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

import musicp.firebok.com.music.Config;

/**
 * Bean that contains artist information.<br/>
 * This class contains static methods that executes API methods relating to
 * artists.<br/>
 * Method names are equivalent to the last.fm API method names.
 *
 * @author Janni Kovacs
 */
public class Artist extends MusicEntry {

    protected final static ItemFactory<Artist> FACTORY = new ArtistFactory();

    protected Artist(final String name, final String url) {
        super(name, url);
    }

    /**
     * Retrieves detailed artist info for the given artist or mbid entry.
     *
     * @param artistOrMbid Name of the artist or an mbid
     * @return detailed artist info
     */
    public final static Artist getInfo(final Context context, final String artistOrMbid) {
        return getInfo(context, artistOrMbid, Locale.getDefault(), Config.LASTFM_API_KEY);
    }

    /**
     * Retrieves detailed artist info for the given artist or mbid entry.
     *
     * @param artistOrMbid Name of the artist or an mbid
     * @param locale The language to fetch info in, or <code>null</code>
     * @param apiKey The API key
     * @return detailed artist info
     */
    public final static Artist getInfo(final Context context, final String artistOrMbid,
            final Locale locale, final String apiKey) {
        final Map<String, String> mParams = new WeakHashMap<String, String>();
        mParams.put("artist", artistOrMbid);
        if (locale != null && locale.getLanguage().length() != 0) {
            mParams.put("lang", locale.getLanguage());
        }
        final Result mResult = Caller.getInstance(context).call("artist.getInfo", apiKey, mParams);
        return ResponseBuilder.buildItem(mResult, Artist.class);
    }

    /**
     * Use the last.fm corrections data to check whether the supplied artist has
     * a correction to a canonical artist. This method returns a new
     * {@link Artist} object containing the corrected data, or <code>null</code>
     * if the supplied Artist was not found.
     *
     * @param artist The artist name to correct
     * @return a new {@link Artist}, or <code>null</code>
     */
    public final static Artist getCorrection(final Context context, final String artist) {
        Result result = null;
        try {
            result = Caller.getInstance(context).call("artist.getCorrection",
                    Config.LASTFM_API_KEY, "artist", artist);
            if (!result.isSuccessful()) {
                return null;
            }
            final DomElement correctionElement = result.getContentElement().getChild("correction");
            if (correctionElement == null) {
                return new Artist(artist, null);
            }
            final DomElement artistElem = correctionElement.getChild("artist");
            return FACTORY.createItemFromElement(artistElem);
        } catch (final Exception ignored) {
            return null;
        }
    }

    private final static class ArtistFactory implements ItemFactory<Artist> {

        /**
         * {@inheritDoc}
         */
        @Override
        public Artist createItemFromElement(final DomElement element) {
            if (element == null) {
                return null;
            }
            final Artist artist = new Artist(null, null);
            MusicEntry.loadStandardInfo(artist, element);
            return artist;
        }
    }
}
