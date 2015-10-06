package com.controller.DbHelpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.controller.SQLiteHelpers.SQLiteHelper;
import com.model.bussinesEntities.CalleEntitie;
import com.model.tablasEntidades.CalleTablaEntidad;

public class CalleDBHelper extends HelperBase {

	public Context myContext;
	public SQLiteHelper openHelper;
	public SQLiteDatabase myDatabase;
	
	/**
	 * Constructor class
	 * @param con
	 */
	public CalleDBHelper(Context con){
		this.myContext = con;
		this.openHelper = new SQLiteHelper(this.myContext);
		try {
			this.openHelper.createDataBase();
	 	} catch (IOException ioe) {
	 		throw new Error("Unable to create database");
	 	}
	}
	
	/**
	 * Open the database
	 * 
	 */
	public CalleDBHelper open(){
		try {
			this.myDatabase = this.openHelper.openDataBase();
	 	}catch(SQLException sqle){
	 		throw sqle;
	 	}return this;
	}
	
	/**
	 * Close the database
	 * 
	 */
	public void close(){
		this.myDatabase.close();
	}	
	
	/**
	 * Inserta un registro en la tabla de calles, y devuelve el id.
	 * @param calle
	 * @return id
	 */
	public long insertCalle(CalleEntitie calle){
		
		ContentValues values = new ContentValues();
		values.put(CalleTablaEntidad.calle_codigoCalle, calle.get_codigoCalle());
		values.put(CalleTablaEntidad.calle_descripcion, calle.get_calle());
		long id = this.myDatabase.insert(CalleTablaEntidad.nombre_tabla, null, values);
		
		return id;
	}
	
	/**
	 * Retorna la calle asociada al codigo pasado por parametro
	 * @param id
	 * @return
	 */
	public CalleEntitie LoadCalleById(String id,SQLiteDatabase db){

        //Para que devuelva SIN CALLE
        if(id == null) id="0";

        SQLiteDatabase database = getSqLiteDatabase(db,this.myDatabase);

		Cursor cursor = database.query(CalleTablaEntidad.nombre_tabla,
		        new String[] {CalleTablaEntidad.id,CalleTablaEntidad.calle_codigoCalle,CalleTablaEntidad.calle_descripcion}, 
		        CalleTablaEntidad.id + "= ? ",
				new String[] {id},null,null,CalleTablaEntidad.calle_codigoCalle);

		cursor.moveToFirst();
		
		CalleEntitie res = null;
		
		if (cursor != null ) {
			
			res = new CalleEntitie();

            if(cursor.moveToFirst()){
                res.set_calle(cursor.getString(cursor.getColumnIndex(CalleTablaEntidad.calle_descripcion)));
                res.set_codigoCalle(cursor.getString(cursor.getColumnIndex(CalleTablaEntidad.calle_codigoCalle)));
                res.set_id(cursor.getLong(cursor.getColumnIndex(CalleTablaEntidad.id)));
            }
            
            cursor.close();
		}

        if(res == null || res.isNull()) //Si no encontre la calle
            res = new CalleEntitie("0","SIN CALLE");
		return res;
	}
	
	
	/**
	 * Retorna todas las calles guardadas en la base de datos. 
	 * 
	 * @return
	 */
	public List<CalleEntitie> LoadAllCalles() {
		
		Cursor cursor = this.myDatabase.query(CalleTablaEntidad.nombre_tabla,
				        new String[] {CalleTablaEntidad.id,CalleTablaEntidad.calle_codigoCalle,CalleTablaEntidad.calle_descripcion}, 
				        null,null,null,null,CalleTablaEntidad.calle_codigoCalle);
		
		cursor.moveToFirst();
		
		CalleEntitie datos = null;
		ArrayList<CalleEntitie> calleList = null;
		
		if (cursor != null ) {
			
			calleList = new ArrayList<CalleEntitie>();
			
			
		    if  (cursor.moveToFirst()) {
		        do {
		        	
		        	datos = new CalleEntitie();
		        	datos.set_calle(cursor.getString(cursor.getColumnIndex(CalleTablaEntidad.calle_descripcion)));
		        	datos.set_codigoCalle(cursor.getString(cursor.getColumnIndex(CalleTablaEntidad.calle_codigoCalle)));
		        	datos.set_id(cursor.getLong(cursor.getColumnIndex(CalleTablaEntidad.id)));
		        	
		        	calleList.add(datos);
		        	
		        }while (cursor.moveToNext());
		    }
		    
		    cursor.close();
		    
		}
		return calleList;
	}
	
	/**
	 * Retorna todas las calles normalizadas  guardadas en la base de datos. 
	 * 
	 * @return
	 */
	public List<CalleEntitie> LoadAllCallesNormalizadas() {
		
		Cursor cursor = this.myDatabase.query(CalleTablaEntidad.nombre_tabla,
				        new String[] {CalleTablaEntidad.id,CalleTablaEntidad.calle_codigoCalle,CalleTablaEntidad.calle_descripcion}, 
				        CalleTablaEntidad.id + "> ? ",
						new String[] {"20000"},null,null,CalleTablaEntidad.calle_codigoCalle);
		
		cursor.moveToFirst();
		
		CalleEntitie datos = null;
		ArrayList<CalleEntitie> calleList = null;
		
		if (cursor != null ) {
			
			calleList = new ArrayList<CalleEntitie>();
			
			
		    if  (cursor.moveToFirst()) {
		        do {
		        	
		        	datos = new CalleEntitie();
		        	datos.set_calle(cursor.getString(cursor.getColumnIndex(CalleTablaEntidad.calle_descripcion)));
		        	datos.set_codigoCalle(cursor.getString(cursor.getColumnIndex(CalleTablaEntidad.calle_codigoCalle)));
		        	datos.set_id(cursor.getLong(cursor.getColumnIndex(CalleTablaEntidad.id)));
		        	
		        	calleList.add(datos);
		        	
		        }while (cursor.moveToNext());
		    }
		    
		    cursor.close();
		    
		}
		return calleList;
	}
	
	
	public List<CalleEntitie> LoadAllCallesByManzana(String circ,String sec,String manz) {
		
	     Cursor cursor = this.myDatabase.rawQuery("SELECT * FROM " +CalleTablaEntidad.nombre_tabla + " WHERE " + CalleTablaEntidad.id + " IN " +
		    		 	"(SELECT calman_idcalle FROM CENSO_CALLEMANZANA WHERE calman_manz = ? and calman_circ = ? and calman_sec = ?)", 
		    		 	new String[]{manz,circ,sec});
		
		cursor.moveToFirst();
		
		CalleEntitie datos = null;
		ArrayList<CalleEntitie> calleList = null;
		
		if (cursor != null ) {
			
			calleList = new ArrayList<CalleEntitie>();
			
			
		    if  (cursor.moveToFirst()) {
		        do {
		        	
		        	datos = new CalleEntitie();
		        	datos.set_calle(cursor.getString(cursor.getColumnIndex(CalleTablaEntidad.calle_descripcion)));
		        	datos.set_codigoCalle(cursor.getString(cursor.getColumnIndex(CalleTablaEntidad.calle_codigoCalle)));
		        	datos.set_id(cursor.getLong(cursor.getColumnIndex(CalleTablaEntidad.id)));
		        	
		        	calleList.add(datos);
		        	
		        }while (cursor.moveToNext());
		    }
		    
		    cursor.close();
		    
		}
		return calleList;
	}
}
