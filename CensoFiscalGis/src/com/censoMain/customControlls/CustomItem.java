package com.censoMain.customControlls;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.censoMain.constantes.Constants;
import com.censoMain.customControlls.CustomDataGridStyle.DataCell;
import com.censoMain.customControlls.CustomDataGridStyle.HeaderCell;

class CustomItem extends LinearLayout{

	private TextView[] artTextView;
    private Context mContext;
	private Spliter2 mTxtContent;
	private CustomDatagrid.MemberCollection mc;
	
    public CustomItem(Context context, CustomDatagrid.MemberCollection mc, CustomDataTable.DataRow data) {
        super(context);
        mContext = context;
        this.mc = mc;
        setOrientation(HORIZONTAL);

        artTextView = new TextView[data.getColumnSize()];
        int intCellSpliter = 0;
        
        for(int i = 0; i < mc.COLUMN_STYLE.size(); i++)
        {
        	artTextView[i] = new TextView(mContext);
        	artTextView[i].setTextSize(DataCell.FontSize);
        	artTextView[i].setPadding(DataCell.LPadding, DataCell.TPadding, DataCell.RPadding, DataCell.BPadding);
        	artTextView[i].setBackgroundColor(DataCell.BackgroundColor);
        	artTextView[i].setText(data.get(mc.COLUMN_STYLE.get(i).getFieldName()).getContentColumn());
        	artTextView[i].setWidth(mc.COLUMN_STYLE.get(i).getWitdh());
        	artTextView[i].setSingleLine(true);
        	artTextView[i].setGravity(DataCell.Gravity);
        	
        	mc.ITEM_VIEW.get(i).add(artTextView[i]);

            addView(artTextView[i], new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, DataCell.Height));
            
			if(intCellSpliter < mc.COLUMN_STYLE.size())
			{
				mTxtContent = new Spliter2(getContext(),mc, i);
				mc.SPLITER_VIEW.get(i).add(mTxtContent);
				addView(mTxtContent,new LinearLayout.LayoutParams(HeaderCell.Width, DataCell.Height));
				intCellSpliter++;
			}
        }
    }
    
    public void populate(CustomDataTable.DataRow data)
    {
        if(data.getEstado().equals(Constants.ACTION_DELETE))
        {
            for(int i = 0; i <  mc.COLUMN_STYLE.size(); i++)
            {
                artTextView[i].setWidth(mc.COLUMN_STYLE.get(i).getWitdh());
                artTextView[i].setText(data.get(mc.COLUMN_STYLE.get(i).getFieldName()).getContentColumn());
                artTextView[i].setBackgroundColor(Color.RED);
                artTextView[i].setTextColor(Color.BLACK);
            }

        }else if(data.getEstado().equals(Constants.ACTION_UPDATE)){

            for(int i = 0; i <  mc.COLUMN_STYLE.size(); i++)
            {
                artTextView[i].setWidth(mc.COLUMN_STYLE.get(i).getWitdh());
                artTextView[i].setText(data.get(mc.COLUMN_STYLE.get(i).getFieldName()).getContentColumn());
                artTextView[i].setBackgroundColor(Color.YELLOW);
                artTextView[i].setTextColor(Color.BLACK);
            }

        }else if(data.getEstado().equals(Constants.ACTION_INSERT)){

            for(int i = 0; i <  mc.COLUMN_STYLE.size(); i++)
            {
                artTextView[i].setWidth(mc.COLUMN_STYLE.get(i).getWitdh());
                artTextView[i].setText(data.get(mc.COLUMN_STYLE.get(i).getFieldName()).getContentColumn());
                artTextView[i].setBackgroundColor(Color.GREEN);
                artTextView[i].setTextColor(Color.BLACK);
            }

        }else{
            for(int i = 0; i <  mc.COLUMN_STYLE.size(); i++)
            {
                artTextView[i].setWidth(mc.COLUMN_STYLE.get(i).getWitdh());
                artTextView[i].setText(data.get(mc.COLUMN_STYLE.get(i).getFieldName()).getContentColumn());
                artTextView[i].setBackgroundColor(Color.WHITE);
                artTextView[i].setTextColor(Color.BLACK);
            }
        }


    }

}