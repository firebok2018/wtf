package musicp.firebok.com.music.lastfm;


public abstract class MusicEntry extends ImageHolder {

    protected String name;

    protected String url;

    private String wikiSummary;

    protected MusicEntry(final String name, final String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getWikiSummary() {
        return wikiSummary;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + "name='" + name + '\'' + ", url='" + url
                + '\'' + ']';
    }

    /**
     * Loads all generic information from an XML <code>DomElement</code> into
     * the given <code>MusicEntry</code> instance, i.e. the following tags:<br/>
     * <ul>
     * <li>playcount/plays</li>
     * <li>listeners</li>
     * <li>streamable</li>
     * <li>name</li>
     * <li>url</li>
     * <li>mbid</li>
     * <li>image</li>
     * <li>tags</li>
     * </ul>
     *
     * @param entry An entry
     * @param element XML source element
     */
    protected static void loadStandardInfo(final MusicEntry entry, final DomElement element) {
        // copy
        entry.name = element.getChildText("name");
        entry.url = element.getChildText("url");
        // wiki
        DomElement wiki = element.getChild("bio");
        if (wiki == null) {
            wiki = element.getChild("wiki");
        }
        if (wiki != null) {
            entry.wikiSummary = wiki.getChildText("summary");
        }
        // images
        ImageHolder.loadImages(entry, element);
    }
}
