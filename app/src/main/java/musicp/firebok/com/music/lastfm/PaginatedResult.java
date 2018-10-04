package musicp.firebok.com.music.lastfm;

import java.util.Collection;
import java.util.Iterator;

/**
 * A <code>PaginatedResult</code> is returned by methods which result set might
 * be so large that it needs to be paginated. Each <code>PaginatedResult</code>
 * contains the total number of result pages, the current page and a
 * <code>Collection</code> of entries for the current page.
 *
 * @author Janni Kovacs
 */
public class PaginatedResult<T> implements Iterable<T> {

    private final int page;

    private final int totalPages;

    public final Collection<T> pageResults;

    /**
     * @param page
     * @param totalPages
     * @param pageResults
     */
    PaginatedResult(final int page, final int totalPages, final Collection<T> pageResults) {
        this.page = page;
        this.totalPages = totalPages;
        this.pageResults = pageResults;
    }

    /**
     * Returns the page number of this result.
     *
     * @return page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Returns the total number of pages available.
     *
     * @return total pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Returns <code>true</code> if this Result contains no elements, which is
     * the case for service calls that would have returned a
     * <code>PaginatedResult</code> but fail.
     *
     * @return <code>true</code> if this result contains no elements
     */
    public boolean isEmpty() {
        return pageResults == null || pageResults.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return pageResults.iterator();
    }
}
