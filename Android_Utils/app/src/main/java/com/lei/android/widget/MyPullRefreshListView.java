package com.lei.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lei.android.android_utils.R;
import com.lei.android.pullrefresh_listview.PullRefreshListView;

public class MyPullRefreshListView extends PullRefreshListView {
    private View mHeaderView;
    private TextView tv_refresh_state, tv_last_refresh_time;
    private ImageView iv_arrow;
    private ProgressBar pBar_refreshing;

    private RotateAnimation animUp,animDown;

    public MyPullRefreshListView(Context context) {
        super(context);
    }

    public MyPullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPullRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化头布局
     */
    private void init() {
        mHeaderView = View.inflate(getContext(), R.layout.refresh_header_view, null);
        tv_refresh_state = (TextView) mHeaderView.findViewById(R.id.tv_refresh_state);
        tv_last_refresh_time = (TextView) mHeaderView.findViewById(R.id.tv_last_refresh_time);
        iv_arrow = (ImageView) mHeaderView.findViewById(R.id.iv_refresh_arrow);
        pBar_refreshing = (ProgressBar) mHeaderView.findViewById(R.id.pBar_refreshing);
    }

    @Override
    public View getHeaderView() {
        init();
        initArrowAnim();
        return mHeaderView;
    }

    @Override
    public void setPullRefreshState() {
        tv_refresh_state.setText("下拉刷新");
        iv_arrow.setVisibility(VISIBLE);
        pBar_refreshing.setVisibility(INVISIBLE);
        iv_arrow.startAnimation(animDown);
    }

    @Override
    public void setReleaseRefreshState() {
        tv_refresh_state.setText("松开刷新");
        iv_arrow.setVisibility(VISIBLE);
        pBar_refreshing.setVisibility(INVISIBLE);
        iv_arrow.startAnimation(animUp);
    }

    @Override
    public void setRefreshingState() {
        tv_refresh_state.setText("正在刷新...");
        tv_last_refresh_time.setVisibility(GONE);
        iv_arrow.clearAnimation();
        iv_arrow.setVisibility(INVISIBLE);
        pBar_refreshing.setVisibility(VISIBLE);
    }

    private void initArrowAnim() {
        /* 箭头向上动画 */
        animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(200);
        animUp.setFillAfter(true);
        /* 箭头向下动画 */
        animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(200);
        animDown.setFillAfter(true);
    }
}
