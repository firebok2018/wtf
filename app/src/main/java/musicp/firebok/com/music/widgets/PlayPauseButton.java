package musicp.firebok.com.music.widgets;

import android.animation.Animator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import musicp.firebok.com.music.R;
import musicp.firebok.com.music.utils.ElevenUtils;
import musicp.firebok.com.music.utils.MusicUtils;


/**
 * A custom {@link ImageButton} that represents the "play and pause" button.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class PlayPauseButton extends ImageButton implements OnClickListener, OnLongClickListener {

    /**
     * Play button theme resource
     */
    private static final String PLAY = "btn_playback_play";

    /**
     * Pause button theme resource
     */
    private static final String PAUSE = "btn_playback_pause";

    /**
     * @param context The {@link Context} to use
     * @param attrs The attributes of the XML tag that is inflating the view.
     */
    @SuppressWarnings("deprecation")
    public PlayPauseButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setBackground(getResources().getDrawable(R.drawable.selectable_background));
        // Control playback (play/pause)
        setOnClickListener(this);
        // Show the cheat sheet
        setOnLongClickListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(final View v) {
        MusicUtils.playOrPause();
        int centerX = (v.getLeft() + v.getRight())  / 2;
        int centerY = (v.getTop()  + v.getBottom()) / 2;
        int startRadius = 0;
        int endRadius = (int) Math.hypot(v.getWidth(), v.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, startRadius, endRadius);

        anim.setDuration(800);
        anim.start();

        updateState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onLongClick(final View view) {
        if (TextUtils.isEmpty(view.getContentDescription())) {
            return false;
        } else {
            ElevenUtils.showCheatSheet(view);
            return true;
        }
    }

    /**
     * Sets the correct drawable for playback.
     */
    public void updateState() {
        if (MusicUtils.isPlaying()) {
            setContentDescription(getResources().getString(R.string.accessibility_pause));
            setImageDrawable(getResources().getDrawable(R.drawable.btn_playback_pause));
        } else {
            setContentDescription(getResources().getString(R.string.accessibility_play));
            setImageDrawable(getResources().getDrawable(R.drawable.btn_playback_play));
        }
    }

}
