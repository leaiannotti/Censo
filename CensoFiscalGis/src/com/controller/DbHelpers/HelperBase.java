package com.controller.DbHelpers;

import android.database.sqlite.SQLiteDatabase;

public class HelperBase {
	
	 protected SQLiteDatabase getSqLiteDatabase(SQLiteDatabase db,SQLiteDatabase myDB) {
	        SQLiteDatabase database;

	        if(db != null)
	            database = db;
	        else
	            database = myDB;
	        return database;
	    }
	
}
