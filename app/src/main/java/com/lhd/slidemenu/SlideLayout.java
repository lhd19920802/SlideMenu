package com.lhd.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by lihuaidong on 2017/6/12 15:08.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：自定义ViewGroup: 侧滑菜单
 * 1. 正常的初始化显示
 * 得到menuView: onFinishInflate()
 * 得到menuView的宽高: onMeasure()
 * 对menuView进行重新布局: onLayout()
 * 2. 水平滑动菜单
 * 响应用户的touch操作: onTouchEvent() 返回true
 * 在move中: 计算事件的移动, 滚动当前布局
 * 在up时, 根据当前布局的偏移量, 判断是打开菜单/关闭菜单
 * 平滑打开/关闭菜单
 */
public class SlideLayout extends FrameLayout
{

    private View menuView;
    private int measuredWidth;
    private int measuredHeight;
    private int xScroll;

    private Scroller scroller;
    private boolean isOpen=false;

    public SlideLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        scroller=new Scroller(context);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        menuView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = menuView.getMeasuredWidth();
        measuredHeight = menuView.getMeasuredHeight();
        Log.e("TAG", "measuredWidth==" + measuredWidth + ",measuredHeight==" + measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        menuView.layout(-measuredWidth, 0, 0, measuredHeight);
    }

    private int lastX;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int eventX = (int) event.getX();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                lastX = eventX;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = eventX - lastX;
                //限制范围

                xScroll = getScrollX()-dx;
                if(xScroll >0) {
                 xScroll =0;
                }else if(xScroll <-measuredWidth) {
                    xScroll =-measuredWidth;
                }
                scrollTo(xScroll,getScrollY());
                lastX = eventX;
                break;
            case MotionEvent.ACTION_UP:
                //判断是打开还是关闭
                int currentX = getScrollX();
                if(currentX<-measuredWidth/2) {

                    //打开
                    openMenu();
                }
                else if(currentX>-measuredWidth/2) {
                    //关闭
                    closeMenu();
                }
                break;
        }
        return true;
    }

    /**
     * 打开菜单
     */
    public void openMenu()
    {
        //要做的偏移量（-measuredWidth,0）
        scroller.startScroll(getScrollX(),getScrollY(),-measuredWidth-getScrollX(),-getScrollY());
        invalidate();
        isOpen=true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu()
    {
        scroller.startScroll(getScrollX(),getScrollY(),-getScrollX(),-getScrollY());
        invalidate();
        isOpen = false;

    }

    @Override
    public void computeScroll()
    {
        super.computeScroll();
        if(scroller.computeScrollOffset())
        {
           scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }

    }

    /**
     * 打开或关闭菜单
     */
    public void switchMenu()
    {

        if(isOpen) {
            //关闭
            closeMenu();
        }
        else
        {
            //打开
            openMenu();
        }
    }
}
