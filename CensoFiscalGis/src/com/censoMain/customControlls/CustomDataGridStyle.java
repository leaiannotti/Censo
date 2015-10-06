package com.censoMain.customControlls;

import com.censoMain.censofiscalgisB.R;

import android.graphics.Color;

public class CustomDataGridStyle {
	
		public class DataGrid {
			public static final int MinWidth = 400;
			public static final int MaxHeight = 600;  
			public static final int BackgroundColor = Color.TRANSPARENT;
		}
		
		public class HeaderContainer{
			public static final int Height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
			public static final int BackgroundColor = Color.DKGRAY;
			public static final int Background = R.drawable.gradient;
		}
		
		public class HeaderCell{
			public static final int Height = 40;
			public static final int Width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
			public static final int LPadding = 5;
			public static final int RPadding = 5;
			public static final int TPadding = 3;
			public static final int BPadding = 3;
			public static final int FontSize = 16;
			public static final int BackgroundColor = Color.TRANSPARENT;
			public static final int OnClickBackgroundColor = Color.LTGRAY;
			public static final int ForegroundColor = Color.BLACK;
			public static final int OnClickForegroundColor = HeaderContainer.BackgroundColor;
			public static final int Gravity= android.view.Gravity.CENTER_VERTICAL;
		}
		
		public class SpliterCell{
			public static final int BackgroundColor = Color.BLACK;
			public static final int OnClickBackgroundColor = Color.DKGRAY;
			public static final int LPadding = 1;
			public static final int RPadding = 1;
			public static final int TPadding = HeaderCell.TPadding+9;
			public static final int BPadding = HeaderCell.BPadding;
			public static final int FontSize = HeaderCell.FontSize;
		}
		
		public class HeaderSpliterCell{
			public static final int BackgroundColor = Color.RED;
			public static final int OnClickBackgroundColor = Color.DKGRAY;
			public static final int LPadding = 8;
			public static final int RPadding = 8;
			public static final int TPadding = HeaderCell.TPadding+9;
			public static final int BPadding = HeaderCell.BPadding+9;
			public static final int FontSize = HeaderCell.FontSize;
		}
		
		public class DataContainer{
			public static final int BackgroundColor = Color.TRANSPARENT;
		}
		
		public class DataCell{
			public static final int Height = 50;
			public static final int Gravity= HeaderCell.Gravity;
			public static final int FontSize = HeaderCell.FontSize;
			public static final int LPadding = 5;
			public static final int RPadding = 5;
			public static final int TPadding = 3;
			public static final int BPadding = 3;
			public static final int BackgroundColor = Color.WHITE;
			public static final int ForegroundColor = Color.BLACK;
            public static final int OnClickBackgroundColor = Color.BLUE;
        }
	}