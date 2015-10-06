package com.censoMain.customControlls;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.censoMain.customControlls.CustomDataGridStyle.HeaderCell;

class CustomHeader extends TextView implements OnGestureListener{

	private GestureDetector mGesture;
	private View.OnClickListener mOnclickListener = new View.OnClickListener() {@Override	public void onClick(View v) {mHeaderOnclick(v);	}}; 
	private CustomDatagrid.MemberCollection  mc;
	
	@SuppressWarnings("deprecation")
	public CustomHeader(Context context, CustomDatagrid.MemberCollection mc) {
		super(context);
		mGesture = new GestureDetector(this);
		this.mc = mc; 
		setBackgroundColor(HeaderCell.BackgroundColor);
		setTextSize(HeaderCell.FontSize);
		//setTextColor(HeaderCell.ForegroundColor);
		setPadding(HeaderCell.LPadding, HeaderCell.TPadding, HeaderCell.RPadding, HeaderCell.BPadding);
		setSingleLine(true);
		setOnClickListener(mOnclickListener);
		setGravity(HeaderCell.Gravity);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e){
		super.onTouchEvent(e);
		mGesture.onTouchEvent(e);
		switch (e.getAction()) {
	        case MotionEvent.ACTION_DOWN: {
	        	//this.setTextColor(HeaderCell.OnClickForegroundColor);
	    		this.setBackgroundColor(HeaderCell.OnClickBackgroundColor);
	            break;
	        }
	        case MotionEvent.ACTION_CANCEL:{
	    		//this.setTextColor(HeaderCell.ForegroundColor);
	    		this.setBackgroundColor(HeaderCell.BackgroundColor);
	            break;
	        }
	        case MotionEvent.ACTION_UP:{
	    		//this.setTextColor(HeaderCell.ForegroundColor);
	    		this.setBackgroundColor(HeaderCell.BackgroundColor);
	            break;
	        }
		}

		return true;
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	
	private void mHeaderOnclick(View v)
	{
		CustomDatagrid.ColumnStyle ColumnStyle = (CustomDatagrid.ColumnStyle)((TextView)v).getTag();
		
		if(ColumnStyle.getSortOder() == Sort.SORT_NOSORT || ColumnStyle.getSortOder() == Sort.SORT_DESC	)
		{
			ColumnStyle.setSortOrder(Sort.SORT_ASC);
			mc.SORT_ALGO.sortByColumn(mc.DATA_SOURCE.getColumnIndex(ColumnStyle.getFieldName()) ,Sort.SORT_ASC);
		}
		else 
		{
			ColumnStyle.setSortOrder(Sort.SORT_DESC);
			mc.SORT_ALGO.sortByColumn(mc.DATA_SOURCE.getColumnIndex(ColumnStyle.getFieldName()), Sort.SORT_DESC);
		}
		
		mc.DATAGRID_ADAPTER.notifyDataSetChanged();
	}
}
