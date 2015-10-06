package com.censoMain.customControlls;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


class DataGridAdapter extends BaseAdapter {

    private Context mContext;
    private CustomDatagrid.MemberCollection mc;
    
    public DataGridAdapter(Context context, CustomDatagrid.MemberCollection mc) {
        mContext = context;
        this.mc = mc;
    }


	public int getCount() {
        return mc.DATA_SOURCE.getRowSize();
    }

    public Object getItem(int position) {
    	return mc.DATA_SOURCE.getRow(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * Make a SpeechView to hold each row.
     * 
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
    	CustomItem ri; 
    	
    	CustomDataTable.DataRow data = mc.DATA_SOURCE.getRow(position);
    	
        if(convertView == null)
        {
        	ri = new CustomItem(mContext, mc, data);	
        }
        else	
        {
        	ri = (CustomItem)convertView;
        	ri.populate(data);
        }
        
        return ri;
    }
}