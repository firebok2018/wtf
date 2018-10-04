package musicp.firebok.com.music.lastfm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public abstract class ImageHolder {

    protected Map<ImageSize, String> imageUrls = new HashMap<ImageSize, String>();

    /**
     * Returns a Set of all {@link ImageSize}s available.
     *
     * @return all sizes
     */
    public Set<ImageSize> availableSizes() {
        return imageUrls.keySet();
    }

    /**
     * Returns the URL of the image in the specified size, or <code>null</code>
     * if not available.
     *
     * @param size The preferred size
     * @return an image URL
     */
    public String getImageURL(final ImageSize size) {
        return imageUrls.get(size);
    }

    /**
     * @param holder
     * @param element
     */
    protected static void loadImages(final ImageHolder holder, final DomElement element) {
        final Collection<DomElement> images = element.getChildren("image");
        for (final DomElement image : images) {
            final String attribute = image.getAttribute("size");
            ImageSize size = null;
            if (attribute == null) {
                size = ImageSize.UNKNOWN;
            } else {
                try {
                    size = ImageSize.valueOf(attribute.toUpperCase(Locale.ENGLISH));
                } catch (final IllegalArgumentException e) {
                    // if they suddenly again introduce a new image size
                }
            }
            if (size != null) {
                holder.imageUrls.put(size, image.getText());
            }
        }
    }
}
