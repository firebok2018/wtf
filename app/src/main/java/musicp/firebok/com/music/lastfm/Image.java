package musicp.firebok.com.music.lastfm;


public class Image extends ImageHolder {

    final static ItemFactory<Image> FACTORY = new ImageFactory();

    private String url;

    private Image() {
    }

    public String getUrl() {
        return url;
    }

    private static class ImageFactory implements ItemFactory<Image> {
        @Override
        public Image createItemFromElement(final DomElement element) {
            final Image i = new Image();
            i.url = element.getChildText("url");
            loadImages(i, element);
            return i;
        }
    }
}
