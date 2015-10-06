package com.controller.DbHelpers;

import java.io.IOException;

import com.controller.SQLiteHelpers.SQLiteHelper;
import com.model.bussinesEntities.UsuarioEntitie;
import com.model.tablasEntidades.UsuariosTablaEntidad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UsuariosDBHelper {

	public Context myContext;
	public SQLiteHelper openHelper;
	public SQLiteDatabase myDatabase;

    public static UsuariosDBHelper usrDbHelper = null;
	
	/**
	 * Constructor class
	 * @param con
	 */
	public UsuariosDBHelper(Context con){
		this.myContext = con;
		this.openHelper = new SQLiteHelper(this.myContext);
		try {
			this.openHelper.createDataBase();
	 	} catch (IOException ioe) {
	 		throw new Error("Unable to create database");
	 	}
	}
	
	/*
	 * Open the database
	 * 
	 */
	public UsuariosDBHelper open(){
		try {
	 		this.myDatabase = this.openHelper.openDataBase();
	 	}catch(SQLException sqle){
	 		throw sqle;
	 	}
		return this;
	}
	
	/*
	 * Close the database
	 * 
	 */
	public void close(){
		this.myDatabase.close();
    }

	/**
	 * 
	 * @param id
	 * @return
	 */
	public UsuarioEntitie loadUsuarioById(long id,SQLiteDatabase db){
        //Si el valor en la base es nulo...
        if(id == 0) return null;

        //Si ya tengo una database uso la q me dan
        SQLiteDatabase database;
        if(db != null)
            database = db;
        else
            database = this.myDatabase;


        UsuarioEntitie user = null;
		
		Cursor cursor = database.query(UsuariosTablaEntidad.nombre_tabla,
				null, UsuariosTablaEntidad.id + "=?" , new String[] {Long.toString(id)},
				null, null, null);
		cursor.moveToFirst();
		
		user = new UsuarioEntitie();
		user.set_id(cursor.getLong(0));
		user.set_usuario(cursor.getString(1));
		user.set_password(cursor.getString(2));
		user.set_nombre(cursor.getString(3));
		user.set_apellido(cursor.getString(4));
		user.set_email(cursor.getString(5));
		user.set_fechaRegistro(cursor.getString(6));
		user.set_idRemoto(cursor.getString(7));
		
		cursor.close();
		
		return user;
	}
	
	/*
	 * 
	 */
	public UsuarioEntitie loadUsuarioByUser(String usuario,SQLiteDatabase db){

        SQLiteDatabase database;

        if(db != null)
            database = db;
        else
            database = this.myDatabase;


		UsuarioEntitie user = null;
		
		Cursor cursor = database.query(UsuariosTablaEntidad.nombre_tabla,
				null, UsuariosTablaEntidad.usuario_user + "=?" , new String[] {usuario},
				null, null, null);
		cursor.moveToFirst();
		
		if(cursor.getCount() != 0){
			user = new UsuarioEntitie();
			user.set_id(cursor.getLong(0));
			user.set_usuario(cursor.getString(1));
			user.set_password(cursor.getString(2));
			user.set_nombre(cursor.getString(3));
			user.set_apellido(cursor.getString(4));
			user.set_email(cursor.getString(5));
			user.set_fechaRegistro(cursor.getString(6));
			user.set_idRemoto(cursor.getString(7));
		}
		
		if(cursor != null)
			cursor.close();
		
		return user;
	}
	
	/*
	 * Se usa al actualizar desde el webservice
	 */
	public long insertUser(UsuarioEntitie user){
		
		ContentValues values = new ContentValues();
		values.put(UsuariosTablaEntidad.usuario_user, user.get_usuario());
		values.put(UsuariosTablaEntidad.usuario_password, user.get_password());
		values.put(UsuariosTablaEntidad.usuario_nombre, user.get_nombre());
		values.put(UsuariosTablaEntidad.usuario_apellido, user.get_apellido());
		values.put(UsuariosTablaEntidad.usuario_email, user.get_email());
		values.put(UsuariosTablaEntidad.usuario_fecharegistro, user.get_fechaRegistro());
		values.put(UsuariosTablaEntidad.usuario_idRemoto,user.get_idRemoto());
		long id = this.myDatabase.insert(UsuariosTablaEntidad.nombre_tabla, null, values);
		
		return id;
	}
	
}
