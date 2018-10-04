package musicp.firebok.com.music.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import musicp.firebok.com.music.R;


/**
 * This class is the default empty state view for most listviews/fragments
 * It allows the ability to set a main text, a main highlight text and a secondary text
 * By default this container has some strings loaded, but other classes can call the apis to change
 * the text
 */
public class NoResultsContainer extends LinearLayout {
    public NoResultsContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * This changes the Main text (top-most text) of the empty container
     * @param resId String resource id
     */
    public void setMainText(final int resId) {
        ((TextView)findViewById(R.id.no_results_main_text)).setText(resId);
    }

    public void setMainHighlightText(final String text) {
        final TextView hightlightText = (TextView)findViewById(R.id.no_results_main_highlight_text);

        if (text == null || text.isEmpty()) {
            hightlightText.setVisibility(View.GONE);
        } else {
            hightlightText.setText(text);
            hightlightText.setVisibility(View.VISIBLE);
        }
    }

    public void setSecondaryText(final int resId) {
        ((TextView)findViewById(R.id.no_results_secondary_text)).setText(resId);
    }

    public void setTextColor(int color) {
        ((TextView)findViewById(R.id.no_results_main_text)).setTextColor(color);
        ((TextView)findViewById(R.id.no_results_main_highlight_text)).setTextColor(color);
        ((TextView)findViewById(R.id.no_results_secondary_text)).setTextColor(color);
    }
}