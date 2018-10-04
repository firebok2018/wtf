package musicp.firebok.com.music.widgets;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;

import musicp.firebok.com.music.ui.activities.SlidingPanelActivity;


public abstract class AudioActivityButton extends AudioButton {
    protected SlidingPanelActivity mActivity;

    public AudioActivityButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setActivity(FragmentActivity activity) {
        mActivity = (SlidingPanelActivity)activity;
    }
}