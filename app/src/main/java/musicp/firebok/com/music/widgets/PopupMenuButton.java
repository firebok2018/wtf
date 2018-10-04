package musicp.firebok.com.music.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import musicp.firebok.com.music.R;


public class PopupMenuButton extends ImageView implements IPopupMenuCallback,
        View.OnClickListener {
    protected int mPosition = -1;
    protected IListener mClickListener = null;

    public PopupMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        setScaleType(ScaleType.CENTER_INSIDE);
        setBackground(getResources().getDrawable(R.drawable.selectable_background_light));
        setOnClickListener(this);
    }

    public void setPosition(final int position) {
        mPosition = position;
    }

    @Override
    public void setPopupMenuClickedListener(final IListener listener) {
        mClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            mClickListener.onPopupMenuClicked(v, mPosition);
        }
    }
}
