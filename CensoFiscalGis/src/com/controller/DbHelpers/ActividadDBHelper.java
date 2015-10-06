package com.controller.DbHelpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.controller.DbHelpers.HelperBase;
import com.controller.SQLiteHelpers.SQLiteHelper;
import com.model.bussinesEntities.ActividadEntitie;
import com.model.tablasEntidades.ActividadTablaEntidad;
import com.model.tablasEntidades.CategoriaTablaEntidad;
import com.model.tablasEntidades.RelActividadParcelaTablaEntidad;

public class ActividadDBHelper extends HelperBase {

	public Context myContext;
	public SQLiteHelper openHelper;
	public SQLiteDatabase myDatabase;
	
	/**
	 * Constructor class
	 * @param con
	 */
	public ActividadDBHelper(Context con){
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
	public ActividadDBHelper open(){
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

    public List<ActividadEntitie> LoadActividadesByDatosParcela(Long idDatosParcela,boolean modificadas,SQLiteDatabase db){

        SQLiteDatabase database = getSqLiteDatabase(db,this.myDatabase);
        String modif = modificadas ? "S" : "N";

        Cursor cursor = database.query(RelActividadParcelaTablaEntidad.nombre_tabla,
                new String[] {RelActividadParcelaTablaEntidad.relacionActividades_idDatos,RelActividadParcelaTablaEntidad.relacionActividades_idActividad,
                RelActividadParcelaTablaEntidad.relacionActividades_modificada},
                RelActividadParcelaTablaEntidad.relacionActividades_idDatos + "= ? AND " + RelActividadParcelaTablaEntidad.relacionActividades_modificada +  "=?" ,
                new String[] {idDatosParcela.toString(),modif},null,null,RelActividadParcelaTablaEntidad.relacionActividades_idActividad);

        cursor.moveToFirst();

        //Si son las modificadas y no encuentro ninguna, es por q puedo estar en el caso inicial, entonces cargo todas las actividades
        //del datoparcela.
        Cursor cursor2 = null;
        if(cursor.getCount() == 0 && modificadas){

            cursor2 = database.query(RelActividadParcelaTablaEntidad.nombre_tabla,
                    new String[] {RelActividadParcelaTablaEntidad.relacionActividades_idDatos,RelActividadParcelaTablaEntidad.relacionActividades_idActividad,
                            RelActividadParcelaTablaEntidad.relacionActividades_modificada},
                    RelActividadParcelaTablaEntidad.relacionActividades_idDatos + "= ? ",
                    new String[] {idDatosParcela.toString()},null,null,RelActividadParcelaTablaEntidad.relacionActividades_idActividad);

            cursor2.moveToFirst();

            cursor = cursor2;
        }

        ActividadEntitie datos;
        ArrayList<ActividadEntitie> actividadList = null;

        if (cursor != null ) {

            actividadList = new ArrayList<ActividadEntitie>();

            if  (cursor.moveToFirst()) {
                do {
                    datos = this.LoadActividadById(cursor.getString(cursor.getColumnIndex(RelActividadParcelaTablaEntidad.relacionActividades_idActividad)),database);
                    if(datos != null)
                        actividadList.add(datos);
                }while (cursor.moveToNext());
            }
        }
        
        if(cursor != null)
        	cursor.close();
        
        if(cursor2 != null)
            cursor2.close();
        
        return actividadList;
    }

	/**
	 * Inserta un registro en la tabla de actividades, y devuelve el id.
	 * @param actividad
	 * @return id
	 */
	public long insertActividad(ActividadEntitie actividad){
		
		ContentValues values = new ContentValues();
		values.put(ActividadTablaEntidad.actividad_codigo, actividad.get_codigoActividad());
		values.put(ActividadTablaEntidad.actividad_descripcion, actividad.get_descripcionActividad());
		long id = this.myDatabase.insert(ActividadTablaEntidad.nombre_tabla, null, values);
		
		return id;
	}
	
	/**
	 * Retorna la actividad asociada al codigo pasado por parametro
	 * @param id
	 * @return
	 */
	public ActividadEntitie LoadActividadById(String id,SQLiteDatabase db){

        SQLiteDatabase database = getSqLiteDatabase(db,this.myDatabase);

		Cursor cursor = database.query(ActividadTablaEntidad.nombre_tabla,
		        new String[] {ActividadTablaEntidad.id,ActividadTablaEntidad.actividad_codigo,ActividadTablaEntidad.actividad_descripcion}, 
		        ActividadTablaEntidad.id + "= ? ",
				new String[] {id},null,null,ActividadTablaEntidad.actividad_codigo);

		cursor.moveToFirst();
		
		ActividadEntitie res = null;
		
		if (cursor != null ) {
			
			res = new ActividadEntitie();
			
		    if  (cursor.moveToFirst()) {
	        	res.set_codigoActividad(cursor.getString(cursor.getColumnIndex(ActividadTablaEntidad.actividad_codigo)));
	        	res.set_descripcionActividad(cursor.getString(cursor.getColumnIndex(ActividadTablaEntidad.actividad_descripcion)));
	        	res.set_id(cursor.getLong(cursor.getColumnIndex(CategoriaTablaEntidad.id)));
		    }
		}
		
		if(cursor != null)
            cursor.close();
		
		return res;
	}
	
	
	/**
	 * Retorna todas las actividades guardadas en la base de datos. 
	 * 
	 * @return
	 */
	public List<ActividadEntitie> LoadAllActividades() {
		
		Cursor cursor = this.myDatabase.query(ActividadTablaEntidad.nombre_tabla,
				        new String[] {ActividadTablaEntidad.id,ActividadTablaEntidad.actividad_codigo,ActividadTablaEntidad.actividad_descripcion}, 
				        null,null,null,null,ActividadTablaEntidad.actividad_codigo);
		
		cursor.moveToFirst();
		
		ActividadEntitie datos = null;
		ArrayList<ActividadEntitie> actividadList = null;
		
		if (cursor != null ) {
			
			actividadList = new ArrayList<ActividadEntitie>();
			
			
		    if  (cursor.moveToFirst()) {
		        do {
		        	
		        	datos = new ActividadEntitie();
		        	datos.set_codigoActividad(cursor.getString(cursor.getColumnIndex(ActividadTablaEntidad.actividad_codigo)));
		        	datos.set_descripcionActividad(cursor.getString(cursor.getColumnIndex(ActividadTablaEntidad.actividad_descripcion)));
		        	datos.set_id(cursor.getLong(cursor.getColumnIndex(ActividadTablaEntidad.id)));
		        	
		        	actividadList.add(datos);
		        	
		        }while (cursor.moveToNext());
		    }
		}
		
		if(cursor != null)
            cursor.close();
		
		return actividadList;
	}

    public boolean existeActividad(Long idActividad, Long idDatosParcela,SQLiteDatabase db) {

        SQLiteDatabase database = getSqLiteDatabase(db,this.myDatabase);

        boolean existe = true;

        Cursor cursor = database.query(RelActividadParcelaTablaEntidad.nombre_tabla,
                new String[] {RelActividadParcelaTablaEntidad.relacionActividades_idActividad,RelActividadParcelaTablaEntidad.relacionActividades_idDatos},
                RelActividadParcelaTablaEntidad.relacionActividades_idDatos + "= ? AND " + RelActividadParcelaTablaEntidad.relacionActividades_idActividad + "= ?",
                new String[] {idDatosParcela.toString(),idActividad.toString()},null,null,RelActividadParcelaTablaEntidad.relacionActividades_idActividad);

        cursor.moveToFirst();

        if (cursor.getCount() == 0){
            existe = false;
        }
        
		if(cursor != null)
            cursor.close();

        return  existe;

    }

    public boolean updateRel(Long idActividad, Long idDatosParcela, boolean modificada,SQLiteDatabase db) {


        SQLiteDatabase database = getSqLiteDatabase(db,this.myDatabase);

        ContentValues values = new ContentValues();

        values.put(RelActividadParcelaTablaEntidad.relacionActividades_idActividad, idActividad);
        values.put(RelActividadParcelaTablaEntidad.relacionActividades_idDatos, idDatosParcela);
        values.put(RelActividadParcelaTablaEntidad.relacionActividades_modificada, modificada? "S":"N");

        int modRow = database.update(RelActividadParcelaTablaEntidad.nombre_tabla,
                values, RelActividadParcelaTablaEntidad.relacionActividades_idActividad + " = ? AND " +
                RelActividadParcelaTablaEntidad.relacionActividades_idDatos + " = ?",
                new String[]{idActividad.toString(),idDatosParcela.toString()});

        return modRow == 1;

    }

    public boolean insertRel(Long idActividad, Long idDatosParcela, boolean modificada,SQLiteDatabase db) {

        SQLiteDatabase database = getSqLiteDatabase(db,this.myDatabase);

        ContentValues values = new ContentValues();

        values.put(RelActividadParcelaTablaEntidad.relacionActividades_idActividad, idActividad);
        values.put(RelActividadParcelaTablaEntidad.relacionActividades_idDatos, idDatosParcela);
        values.put(RelActividadParcelaTablaEntidad.relacionActividades_modificada, modificada? "S":"N");

        long id = database.insert(RelActividadParcelaTablaEntidad.nombre_tabla, null, values);

        return id != -1;


    }

    public ActividadEntitie LoadActividadByDesc(String act) {

        //Codigo-Actividad
        String[] codDesc = act.split("-");

        if(codDesc.length != 2) return null;

        Cursor cursor = this.myDatabase.query(ActividadTablaEntidad.nombre_tabla,
                new String[] {ActividadTablaEntidad.id,ActividadTablaEntidad.actividad_codigo,ActividadTablaEntidad.actividad_descripcion},
                ActividadTablaEntidad.actividad_codigo + "= ? AND " + ActividadTablaEntidad.actividad_descripcion + "=?",
                new String[] {codDesc[0].trim(),codDesc[1].trim()},null,null,ActividadTablaEntidad.actividad_codigo);

        cursor.moveToFirst();

        ActividadEntitie res = null;

        if (cursor != null ) {

            res = new ActividadEntitie();

            if  (cursor.moveToFirst()) {
                res.set_codigoActividad(cursor.getString(cursor.getColumnIndex(ActividadTablaEntidad.actividad_codigo)));
                res.set_descripcionActividad(cursor.getString(cursor.getColumnIndex(ActividadTablaEntidad.actividad_descripcion)));
                res.set_id(cursor.getLong(cursor.getColumnIndex(CategoriaTablaEntidad.id)));
            }
        }
        
		if(cursor != null)
            cursor.close();
		
        return res;
    }



    public boolean eliminarRelacionesDatos(Long id) {

        int result;
        try{
            result = this.myDatabase.delete(RelActividadParcelaTablaEntidad.nombre_tabla,RelActividadParcelaTablaEntidad.relacionActividades_idDatos + "=?",new String[]{id.toString()});
        }catch (Exception e){
            Log.e("Al eliminar permanentemente los datos de relacion con actividades de la parcela", e.getMessage());
            return false;
        }
        return result != -1;


    }


}
