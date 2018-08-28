package edm.music.t3v.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Vuong_IT on 19/11/2017.
 */

public class MusicViewPager extends ViewPager {
    private boolean enableSwipe;

    public MusicViewPager(Context context) {
        super(context);
    }
                    
    public MusicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!enableSwipe)
            return false;

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!enableSwipe)
            return false;

        return super.onInterceptTouchEvent(ev);
    }

    public boolean isEnableSwipe() {
        return enableSwipe;
    }

    public void setEnableSwipe(boolean enableSwipe) {
        this.enableSwipe = enableSwipe;
    }
}
