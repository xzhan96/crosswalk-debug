package org.crosswalkproject.xwalkanimation5449;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkView;

public class MainActivity extends XWalkActivity implements View.OnClickListener {
    private XWalkView mXWalkView1, mXWalkView2;
    private Button mRunAnimationButton1, mRunAnimationButton2;
    private ImageButton mRefreshBtn;

    private final static float ANIMATION_FACTOR = 0.6f;

    private void startAnimation( XWalkView xwalkview) {
        AnimatorSet combo = new AnimatorSet();

        float targetAlpha = xwalkview.getAlpha() == 1.f ? ANIMATION_FACTOR : 1.f;
        float targetScaleFactor = xwalkview.getScaleX() == 1.f ? ANIMATION_FACTOR : 1.f;

        ObjectAnimator fade = ObjectAnimator.ofFloat(xwalkview,
                "alpha", xwalkview.getAlpha(), targetAlpha);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(xwalkview,
                "scaleX", xwalkview.getScaleX(), targetScaleFactor);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(xwalkview,
                "scaleY", xwalkview.getScaleY(), targetScaleFactor);

        combo.setDuration(400);
        combo.playTogether(fade, scaleX, scaleY);
        combo.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //XWalkPreferences.setValue(XWalkPreferences.ANIMATABLE_XWALK_VIEW, true);
        setContentView(R.layout.activity_main);

        mXWalkView1 = (XWalkView) findViewById(R.id.xwalkview1);
        mXWalkView2 = (XWalkView) findViewById(R.id.xwalkview2);

        mRefreshBtn = (ImageButton) findViewById(R.id.id_refresh);
        mRefreshBtn.setOnClickListener((View.OnClickListener) this);

        mRunAnimationButton1 = (Button) findViewById(R.id.run_animation1);
        mRunAnimationButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation(mXWalkView1);
            }
        });

        mRunAnimationButton2 = (Button) findViewById(R.id.run_animation2);
        mRunAnimationButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation(mXWalkView2);
            }
        });

    }

    @Override
    protected void onXWalkReady() {
        //mXWalkView.setBackgroundColor(0xFFFF00E0);
        mXWalkView1.load("http://www.baidu.com", null);
        mXWalkView2.load("http://www.sogou.com", null);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_refresh:
                mXWalkView1.reload(0);
                break;
        }
    }
}
