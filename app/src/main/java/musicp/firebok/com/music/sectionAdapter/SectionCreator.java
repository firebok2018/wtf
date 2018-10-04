package musicp.firebok.com.music.sectionAdapter;

import android.content.Context;



import java.util.List;
import java.util.TreeMap;

import musicp.firebok.com.music.utils.SectionCreatorUtils;

/**
 * This class wraps a SimpleListLoader and creates header sections for the sections
 * @param <T> The type of item that is loaded
 */
public class SectionCreator<T> extends WrappedAsyncTaskLoader<SectionListContainer<T>> {
    /**
     * Simple list loader class that exposes a load method
     * @param <T> type of item to load
     */
    public static abstract class SimpleListLoader<T> extends WrappedAsyncTaskLoader<List<T>> {
        protected Context mContext;

        public SimpleListLoader(Context context) {
            super(context);
            mContext = context;
        }

        public Context getContext() {
            return mContext;
        }
    }

    private SimpleListLoader<T> mLoader;
    private SectionCreatorUtils.IItemCompare<T> mComparator;

    /**
     * Creates a SectionCreator object which loads @loader
     * @param context The {@link Context} to use.
     * @param loader loader to wrap
     * @param comparator the comparison object to run to create the sections
     */
    public SectionCreator(Context context, SimpleListLoader<T> loader,
                          SectionCreatorUtils.IItemCompare<T> comparator) {
        super(context);
        mLoader = loader;
        mComparator = comparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SectionListContainer<T> loadInBackground() {
        List<T> results = mLoader.loadInBackground();
        TreeMap<Integer, SectionCreatorUtils.Section> sections = null;

        if (mComparator != null) {
            sections = SectionCreatorUtils.createSections(results, mComparator);
        }

        return new SectionListContainer<T>(sections, results);
    }
}
