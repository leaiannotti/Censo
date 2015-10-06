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
import com.model.bussinesEntities.CategoriaEntitie;
import com.model.tablasEntidades.CategoriaTablaEntidad;

public class CategoriaDBHelper {

	public Context myContext;
	public SQLiteHelper openHelper;
	public SQLiteDatabase myDatabase;
	
	/**
	 * Constructor class
	 * @param con
	 */
	public CategoriaDBHelper(Context con){
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
	public CategoriaDBHelper open(){
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
	 * Inserta un registro en la tabla de categorias, y devuelve el id.
	 * @param categoria
	 * @return id
	 */
	public long insertCategoria(CategoriaEntitie categoria){
		
		ContentValues values = new ContentValues();
		values.put(CategoriaTablaEntidad.categoria_codigo, categoria.get_codCategoria());
		values.put(CategoriaTablaEntidad.categoria_descripcion, categoria.get_descCategoria());
		long id = this.myDatabase.insert(CategoriaTablaEntidad.nombre_tabla, null, values);
		
		return id;
	}
	
	/**
	 * Retorna la categoria asociada al codigo pasado por parametro
	 * @param id
	 * @return
	 */
	public CategoriaEntitie LoadCategoriaById(String id){
		
		Cursor cursor = this.myDatabase.query(CategoriaTablaEntidad.nombre_tabla,
		        new String[] {CategoriaTablaEntidad.id,CategoriaTablaEntidad.categoria_codigo,CategoriaTablaEntidad.categoria_descripcion}, 
		        CategoriaTablaEntidad.categoria_codigo + "= ? ",
				new String[] {id},null,null,CategoriaTablaEntidad.categoria_codigo);

		cursor.moveToFirst();
		
		CategoriaEntitie res = null;
		
		if (cursor != null ) {
			
			res = new CategoriaEntitie();
			
		    if  (cursor.moveToFirst()) {
		    	res.set_codCategoria(cursor.getString(cursor.getColumnIndex(CategoriaTablaEntidad.categoria_codigo)));
		    	res.set_descCategoria(cursor.getString(cursor.getColumnIndex(CategoriaTablaEntidad.categoria_descripcion)));
		    	res.set_id(cursor.getLong(cursor.getColumnIndex(CategoriaTablaEntidad.id)));
		    }
		    
		    cursor.close();
		}
		return res;
	}
	
	
	/**
	 * Retorna todas las categorias guardadas en la base de datos. 
	 * 
	 * @return
	 */
	public List<CategoriaEntitie> LoadAllCategorias() {
		
		Cursor cursor = this.myDatabase.query(CategoriaTablaEntidad.nombre_tabla,
				        new String[] {CategoriaTablaEntidad.id,CategoriaTablaEntidad.categoria_codigo,CategoriaTablaEntidad.categoria_descripcion}, 
				        null,null,null,null,CategoriaTablaEntidad.categoria_codigo);
		
		cursor.moveToFirst();
		
		CategoriaEntitie datos = null;
		ArrayList<CategoriaEntitie> categoriaList = null;
		
		if (cursor != null ) {
			
			categoriaList = new ArrayList<CategoriaEntitie>();
			
			
		    if  (cursor.moveToFirst()) {
		        do {
		        	
		        	datos = new CategoriaEntitie();
		        	datos.set_codCategoria(cursor.getString(cursor.getColumnIndex(CategoriaTablaEntidad.categoria_codigo)));
		        	datos.set_descCategoria(cursor.getString(cursor.getColumnIndex(CategoriaTablaEntidad.categoria_descripcion)));
		        	datos.set_id(cursor.getLong(cursor.getColumnIndex(CategoriaTablaEntidad.id)));
		        	
		        	categoriaList.add(datos);
		        	
		        }while (cursor.moveToNext());
		    }
		    
		    cursor.close();
		    
		}
		return categoriaList;
	}
}
