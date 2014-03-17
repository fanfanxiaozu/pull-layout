package com.pulllayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * Created by kai.wang on 3/17/14.
 */
class PullBase extends LinearLayout{

    private View headerView;
    private int headerViewHeight;

    protected View contentView;

    protected boolean headerShowing = false;
    private int currentHeaderMargin = 0;

    protected boolean containAbsListView = false;

    public PullBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
    }

    protected void addHeaderView(View headerView) {
        this.headerView = headerView;
        addView(headerView);
        measureHeader(headerView);
        headerViewHeight = headerView.getMeasuredHeight();
        LayoutParams p = (LayoutParams) headerView.getLayoutParams();
        p.topMargin = -headerViewHeight;

        currentHeaderMargin = -headerViewHeight;
    }

    public void hideHeader() {
        LayoutParams p = (LayoutParams) headerView.getLayoutParams();
        p.topMargin = -headerViewHeight;
        headerView.setLayoutParams(p);
        currentHeaderMargin = -headerViewHeight;
        headerShowing = false;
    }

    public void showHeader() {
        LayoutParams p = (LayoutParams) headerView.getLayoutParams();
        p.topMargin = 0;
        headerView.setLayoutParams(p);
        currentHeaderMargin = 0;
        headerShowing = true;
    }

    public int getHeaderHeight() {
        return headerViewHeight;
    }

    public View getHeaderView() {
        return headerView;
    }

    protected void addContentView(View contentView) {
        this.contentView = contentView;
        if(contentView instanceof AbsListView){
            containAbsListView = true;
        }
        addView(contentView);
    }

    protected View getContentView() {
        return contentView;
    }

    /**
     * measure header view's width and height
     *
     * @param headerView
     */
    private void measureHeader(View headerView) {
        LayoutParams p = (LayoutParams) headerView.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        headerView.setLayoutParams(p);
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        headerView.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 設置header view 的 margin
     *
     * @param margin
     */
    public final void adjustHeader(int margin, boolean up) {
        if (up) {
            currentHeaderMargin = currentHeaderMargin - margin;
            if (currentHeaderMargin < -headerViewHeight) {
                currentHeaderMargin = -headerViewHeight;
            }
        } else {
            currentHeaderMargin = currentHeaderMargin + margin;
        }
        LayoutParams p = (LayoutParams) headerView.getLayoutParams();
        p.topMargin = currentHeaderMargin;
        headerView.setLayoutParams(p);
        headerShowing = currentHeaderMargin >= -headerViewHeight;
    }

    public int getCurrentHeaderMargin() {
        return currentHeaderMargin;
    }



}