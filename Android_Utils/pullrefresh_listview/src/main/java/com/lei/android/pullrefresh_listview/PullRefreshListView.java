package com.lei.android.pullrefresh_listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * 下拉刷新自定义控件ListView
 */
public abstract class PullRefreshListView extends ListView {
    private OnRefreshingListener listener;

    protected static final int STATE_PULL_REFRESH = 0;//下拉刷新
    protected static final int STATE_RELEASE_REFRESH = 1;//松开刷新
    protected static final int STATE_REFRESHING = 2;//正在刷新

    protected View mHeaderView;//头布局
    private int mHeaderViewHeight;//头布局高度
    private int startY;//滑动起点的Y坐标

    private int mCurrentState = STATE_PULL_REFRESH;//当前状态

    public PullRefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public PullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public PullRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    /**
     * 初始化头布局
     */
    private void initHeaderView() {
        mHeaderView = getHeaderView();//获取头布局View
        this.addHeaderView(mHeaderView);//加入头布局
        mHeaderView.setEnabled(false);
        mHeaderView.measure(0, 0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();//获取头布局高度
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);//隐藏头布局
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {//确保startY有效
                    startY = (int) ev.getRawY();
                }
                if (mCurrentState == STATE_REFRESHING) {//正在刷新时跳出Move操作
                    break;
                }
                if (onTouchMove(ev)) return true;
                break;

            case MotionEvent.ACTION_UP:
                startY = -1;//重置
                if (mCurrentState == STATE_RELEASE_REFRESH) {
                    mCurrentState = STATE_REFRESHING;
                    mHeaderView.setPadding(0, 0, 0, 0);//显示头布局
                    setRefreshingState();
                    if (null!=listener) listener.onRefresh();
                } else if (mCurrentState == STATE_PULL_REFRESH) {
                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);//隐藏头布局
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * ListView上下滑动时动态设置头布局padding，动态改变头布局下拉刷新与松开刷新状态
     *
     * @param ev
     * @return
     */
    private boolean onTouchMove(MotionEvent ev) {
        int endY = (int) ev.getRawY();
        int dy = endY - startY;//移动偏移量
        if (dy > 0 && getFirstVisiblePosition() == 0) {//只有下拉并且当前是第一个item，才允许下拉
            int padding = dy - mHeaderViewHeight;//计算padding
            mHeaderView.setPadding(0, padding, 0, 0);//设置当前padding

            if (padding > 0 && mCurrentState != STATE_RELEASE_REFRESH) {//状态改为松开刷新
                mCurrentState = STATE_RELEASE_REFRESH;
                setReleaseRefreshState();
            } else if (padding < 0 && mCurrentState != STATE_PULL_REFRESH) {//改为下拉刷新
                mCurrentState = STATE_PULL_REFRESH;
                setPullRefreshState();
            }
            return true;
        }
        return false;
    }

    /**
     * 刷新下拉控件的布局
     */
    private void refreshState() {
        switch (mCurrentState) {
            case STATE_PULL_REFRESH:
                setPullRefreshState();
                break;
            case STATE_RELEASE_REFRESH:
                setReleaseRefreshState();
                break;
            case STATE_REFRESHING:
                setRefreshingState();
                break;
        }
    }

    /**
     * 设置刷新成功与否监听
     *
     * @param listener
     */
    public void setPullRefreshListener(OnRefreshingListener listener) {
        this.listener = listener;
    }

    /**
     * 由实现类设置头布局内容
     *
     * @return
     */
    public abstract View getHeaderView();

    /**
     * 下拉刷新，由实现类描述具体进入下拉刷新状态后所要做的事情
     */
    public abstract void setPullRefreshState();

    /**
     * 松开刷新，由实现类描述具体进入松开刷新状态后所要做的事情
     */
    public abstract void setReleaseRefreshState();

    /**
     * 正在刷新,由实现类描述具体进入刷新状态后所要做的事情
     */
    public abstract void setRefreshingState();

    /**
     * 刷新结束后重置为下拉刷新状态
     */
    public void onRefreshComplete() {
        if (mCurrentState == STATE_REFRESHING) {
            mCurrentState = STATE_PULL_REFRESH;
            setPullRefreshState();
        }
    }

    public void setCurrentState(int mCurrentState) {
        this.mCurrentState = mCurrentState;
    }

    /**
     * 开始刷新具体处理业务监听接口
     */
    public interface OnRefreshingListener {
        void onRefresh();
    }
}
