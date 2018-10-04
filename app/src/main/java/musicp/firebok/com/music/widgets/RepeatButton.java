package musicp.firebok.com.music.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;



/**
 * A custom {@link ImageButton} that represents the "repeat" button.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class RepeatButton extends AudioButton {
    public RepeatButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(final View v) {
        MusicUtils.cycleRepeat();
        updateRepeatState();
    }

    /** Sets the correct drawable for the repeat state. */
    public void updateRepeatState() {
        switch (MusicUtils.getRepeatMode()) {
            case MusicPlaybackService.REPEAT_ALL:
                setContentDescription(getResources().getString(R.string.accessibility_repeat_all));
                setImageDrawable(getResources().getDrawable(R.drawable.btn_playback_repeat_all));
                setAlpha(ACTIVE_ALPHA);
                break;
            case MusicPlaybackService.REPEAT_CURRENT:
                setContentDescription(getResources().getString(R.string.accessibility_repeat_one));
                setImageDrawable(getResources().getDrawable(R.drawable.btn_playback_repeat_one));
                setAlpha(ACTIVE_ALPHA);
                break;
            case MusicPlaybackService.REPEAT_NONE:
                setContentDescription(getResources().getString(R.string.accessibility_repeat));
                setImageDrawable(getResources().getDrawable(R.drawable.btn_playback_repeat_all));
                setAlpha(INACTIVE_ALPHA);
                break;
            default:
                break;
        }
    }
}