package com.anyi.door.choosecity.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ContactListView extends ListView
{

	protected boolean mIsFastScrollEnabled = false;
	protected IndexScroller mScroller = null;
	protected GestureDetector mGestureDetector = null;

	// additional customization
	protected boolean inSearchMode = false; // whether is in search mode
	protected boolean autoHide = false; // alway show the scroller

	public ContactListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public ContactListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public IndexScroller getScroller()
	{
		return mScroller;
	}

	@Override
	public boolean isFastScrollEnabled()
	{
		return mIsFastScrollEnabled;
	}

	// override this if necessary for custom scroller
	public void createScroller()
	{
		mScroller = new IndexScroller(getContext(), this);
		mScroller.setAutoHide(autoHide);
		mScroller.setShowIndexContainer(true);

		if (autoHide)
			mScroller.hide();
		else
			mScroller.show();
	}

	@Override
	public void setFastScrollEnabled(boolean enabled)
	{
		mIsFastScrollEnabled = enabled;
		if (mIsFastScrollEnabled)
		{
			if (mScroller == null)
			{
				createScroller();
			}
		} else
		{
			if (mScroller != null)
			{
				mScroller.hide();
				mScroller = null;
			}
		}
	}

	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);

		// Overlay index bar
		try {
			if (!inSearchMode) // dun draw the scroller if not in search mode
            {
                if (mScroller != null)
                    mScroller.draw(canvas);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		// Intercept ListView's touch event
		try {
			if (mScroller != null && mScroller.onTouchEvent(ev))
                return true;

			if (mGestureDetector == null)
            {
                mGestureDetector = new GestureDetector(getContext(),
                        new GestureDetector.SimpleOnGestureListener()
                        {

                            @Override
                            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                    float velocityX, float velocityY)
                            {
                                // If fling happens, index bar shows
                                mScroller.show();
                                return super.onFling(e1, e2, velocityX, velocityY);
                            }

                        });
            }
			mGestureDetector.onTouchEvent(ev);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		return true;
	}

	@Override
	public void setAdapter(ListAdapter adapter)
	{
		super.setAdapter(adapter);
		if (mScroller != null)
			mScroller.setAdapter(adapter);
	}
	private int height = 0;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);

		if (oldh == 0) {// ��ʼ��ʱΪ0 Ȼ��Ҫ��¼�´�ʱ�ĸ߶�
			height = h;
		}
		if (mScroller != null)
			mScroller.onSizeChanged(w, height, oldw, oldh);
	}

	public boolean isInSearchMode()
	{
		return inSearchMode;
	}

	public void setInSearchMode(boolean inSearchMode)
	{
		this.inSearchMode = inSearchMode;
	}

}
