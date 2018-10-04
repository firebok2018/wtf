package musicp.firebok.com.music.recycler;

import android.view.View;
import android.widget.AbsListView.RecyclerListener;

import musicp.firebok.com.music.cache.ImageWorker;
import musicp.firebok.com.music.ui.MusicHolder;


/**
 * A @ {@link RecyclerListener} for {@link MusicHolder}'s views.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class RecycleHolder implements RecyclerListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMovedToScrapHeap(final View view) {
        MusicHolder holder = (MusicHolder)view.getTag();
        if (holder == null) {
            holder = new MusicHolder(view);
            view.setTag(holder);
        }

        // Release mImage's reference
        if (holder.mImage.get() != null) {
            ImageWorker.cancelWork(holder.mImage.get());
            holder.mImage.get().setImageDrawable(null);
            holder.mImage.get().setImageBitmap(null);
        }

        // Release mLineOne's reference
        if (holder.mLineOne.get() != null) {
            holder.mLineOne.get().setText(null);
        }

        // Release mLineTwo's reference
        if (holder.mLineTwo.get() != null) {
            holder.mLineTwo.get().setText(null);
        }

        // Stop the play pause button logic
        if (holder.mPlayPauseProgressButton.get() != null) {
            holder.mPlayPauseProgressButton.get().disableAndHide();
        }
    }

}
