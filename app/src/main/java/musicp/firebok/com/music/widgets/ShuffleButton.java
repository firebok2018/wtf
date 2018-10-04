package musicp.firebok.com.music.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;



import musicp.firebok.com.music.MusicPlaybackService;
import musicp.firebok.com.music.R;
import musicp.firebok.com.music.utils.MusicUtils;
import musicp.firebok.com.music.widgets.AudioButton;

/**
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class ShuffleButton extends AudioButton {
    public ShuffleButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(final View v) {
        MusicUtils.cycleShuffle();
        updateShuffleState();
    }

    /** Sets the correct drawable for the shuffle state. */
    public void updateShuffleState() {
        switch (MusicUtils.getShuffleMode()) {
            case MusicPlaybackService.SHUFFLE_NORMAL:
                setContentDescription(getResources().getString(R.string.accessibility_shuffle_all));
                setAlpha(ACTIVE_ALPHA);
                break;
            case MusicPlaybackService.SHUFFLE_AUTO:
                setContentDescription(getResources().getString(R.string.accessibility_shuffle_all));
                setAlpha(ACTIVE_ALPHA);
                break;
            case MusicPlaybackService.SHUFFLE_NONE:
                setContentDescription(getResources().getString(R.string.accessibility_shuffle));
                setAlpha(INACTIVE_ALPHA);
                break;
            default:
                break;
        }
    }
}