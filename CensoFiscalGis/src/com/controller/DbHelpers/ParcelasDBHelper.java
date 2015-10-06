package com.controller.DbHelpers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.controller.SQLiteHelpers.SQLiteHelper;
import com.model.bussinesEntities.ParcelaEntitie;
import com.model.tablasEntidades.ParcelaTablaEntidad;

public class ParcelasDBHelper extends HelperBase {

	public Context myContext;
	public SQLiteHelper openHelper;
	public SQLiteDatabase myDatabase;
    public UsuariosDBHelper usrDbHelper;
	
	/**
	 * Constructor class
	 * @param con
	 */
	public ParcelasDBHelper(Context con){
        this.myContext = con;
		this.openHelper = new SQLiteHelper(this.myContext);
        this.usrDbHelper = new UsuariosDBHelper(con);
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
	public ParcelasDBHelper open(){
		try {
			this.myDatabase = this.openHelper.openDataBase();
	 	}catch(SQLException sqle){
	 		throw sqle;
	 	}
		
		return this;
	}
	
	/**
	 * Close the database
	 * 
	 */
	public void close(){
		this.myDatabase.close();
	}

	/**
	 * 
	 * Load all by tipo (Circunscripcion,Seccion o Manzana)
	 * @return
	 */
	public List<String> LoadAllByTipo(String columna){

		String elem;
		
		Cursor cursor = this.myDatabase.query(true,ParcelaTablaEntidad.nombre_tabla,new String[]{columna},
				null,null,null,null,columna,null);
		cursor.moveToFirst();
		
		ArrayList<String> lista = null;
		
		if (cursor != null ) {
			lista = new ArrayList<String>();
		    if  (cursor.moveToFirst()) {
		        do {
		        	elem = cursor.getString(cursor.getColumnIndex(columna));
		        	lista.add(elem);
		        }while (cursor.moveToNext());
		    }
		    
		    cursor.close();
		}
		return lista;
	}

    /**
     *Retorna las parcelas para una nomenclatura dada
     * @param circ,secc,manz
     * @return List<ParcelaEntitie>
     */
    public List<ParcelaEntitie> LoadParcelasEntitiesByNomenclatura(String circ,String secc,String manz){

        Cursor cursor = this.myDatabase.query(ParcelaTablaEntidad.nombre_tabla,new String[]{ParcelaTablaEntidad.id,ParcelaTablaEntidad.parcela_featid_server,
                ParcelaTablaEntidad.parcela_parcela,ParcelaTablaEntidad.parcela_circunscripcion,
                ParcelaTablaEntidad.parcela_seccion,ParcelaTablaEntidad.parcela_tipoManzana,
                ParcelaTablaEntidad.parcela_manzanaFraccion,ParcelaTablaEntidad.id_usuario_alta,
                ParcelaTablaEntidad.fecha_alta,ParcelaTablaEntidad.id_usuario_modif,
                ParcelaTablaEntidad.fecha_modif,ParcelaTablaEntidad.id_usuario_baja,
                ParcelaTablaEntidad.fecha_baja,ParcelaTablaEntidad.parcela_nroorden},ParcelaTablaEntidad.parcela_circunscripcion + "=? AND " +
                ParcelaTablaEntidad.parcela_seccion + "=? AND " + ParcelaTablaEntidad.parcela_manzanaFraccion + "=?"
                ,new String[]{circ.trim(),secc.trim(),manz.trim()},ParcelaTablaEntidad.parcela_parcela,null,ParcelaTablaEntidad.parcela_nroorden);

        cursor.moveToFirst();

        ParcelaEntitie parcela = null;
        List<ParcelaEntitie> parcelas = new ArrayList<ParcelaEntitie>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    parcela = new ParcelaEntitie();
                    parcela.set_id(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id)));
                    parcela.set_idParcelaServer(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_featid_server)));
                    parcela.set_circunscripcion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_circunscripcion)));
                    parcela.set_seccion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_seccion)));
                    parcela.set_tipoManz(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_tipoManzana)));
                    parcela.set_manzOFracc(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_manzanaFraccion)));
                    parcela.set_parcela(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_parcela)));
                    parcela.set_usrCreacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id_usuario_alta)),this.myDatabase));
                    parcela.set_fechaCreacion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.fecha_alta)));
                    parcela.set_usrModificacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id_usuario_modif)),this.myDatabase));
                    parcela.set_fechaModificacion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.fecha_modif)));
                    parcela.set_usrEliminacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id_usuario_baja)),this.myDatabase));
                    parcela.set_fechaEliminacion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.fecha_baja)));
                    parcela.set_nroOrden(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_nroorden)));

                    parcelas.add(parcela);
                }while (cursor.moveToNext());
            }
            
            cursor.close();
        }
        return parcelas;
    }


    /**
     *
     * @param id
     * @return
     */
    public ParcelaEntitie LoadParcelaById(Long id,SQLiteDatabase db){

        SQLiteDatabase database = getSqLiteDatabase(db,this.myDatabase);

        Cursor cursor = database.query(ParcelaTablaEntidad.nombre_tabla,new String[]{ParcelaTablaEntidad.id,ParcelaTablaEntidad.parcela_featid_server,
                                                ParcelaTablaEntidad.parcela_parcela,ParcelaTablaEntidad.parcela_circunscripcion,
                                                ParcelaTablaEntidad.parcela_seccion,ParcelaTablaEntidad.parcela_tipoManzana,
                                                ParcelaTablaEntidad.parcela_manzanaFraccion,ParcelaTablaEntidad.id_usuario_alta,
                                                ParcelaTablaEntidad.fecha_alta,ParcelaTablaEntidad.id_usuario_modif,
                                                ParcelaTablaEntidad.fecha_modif,ParcelaTablaEntidad.id_usuario_baja,
                                                ParcelaTablaEntidad.fecha_baja,ParcelaTablaEntidad.parcela_nroorden},ParcelaTablaEntidad.id + "=?",new String[]{id.toString()},null,null,null);

        cursor.moveToFirst();

        ParcelaEntitie parcela = null;

        if (cursor != null ) {
            parcela = new ParcelaEntitie();
            if  (cursor.moveToFirst()) {
                do {
                    parcela.set_id(id);
                    parcela.set_idParcelaServer(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_featid_server)));
                    parcela.set_circunscripcion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_circunscripcion)));
                    parcela.set_seccion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_seccion)));
                    parcela.set_tipoManz(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_tipoManzana)));
                    parcela.set_manzOFracc(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_manzanaFraccion)));
                    parcela.set_parcela(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_parcela)));
                    parcela.set_usrCreacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id_usuario_alta)),database));
                    parcela.set_fechaCreacion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.fecha_alta)));
                    parcela.set_usrModificacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id_usuario_modif)),database));
                    parcela.set_fechaModificacion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.fecha_modif)));
                    parcela.set_usrEliminacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id_usuario_baja)),database));
                    parcela.set_fechaEliminacion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.fecha_baja)));
                    parcela.set_nroOrden(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_nroorden)));

                }while (cursor.moveToNext());
            }
            
            cursor.close();
        }
        return parcela;

    }


    public ParcelaEntitie LoadParcelaEntitieByNomenclatura(String circ,String secc,String manz){
        return LoadParcelaEntitieByNomenclatura(circ,secc,manz,null);
    }

    public ParcelaEntitie LoadParcelaEntitieByNomenclatura(String circ,String secc,String manz,SQLiteDatabase db){

        SQLiteDatabase database = getSqLiteDatabase(db,this.myDatabase);

        Cursor cursor = database.query(ParcelaTablaEntidad.nombre_tabla,new String[]{ParcelaTablaEntidad.id,ParcelaTablaEntidad.parcela_featid_server,
                ParcelaTablaEntidad.parcela_parcela,ParcelaTablaEntidad.parcela_circunscripcion,
                ParcelaTablaEntidad.parcela_seccion,ParcelaTablaEntidad.parcela_tipoManzana,
                ParcelaTablaEntidad.parcela_manzanaFraccion,ParcelaTablaEntidad.id_usuario_alta,
                ParcelaTablaEntidad.fecha_alta,ParcelaTablaEntidad.id_usuario_modif,
                ParcelaTablaEntidad.fecha_modif,ParcelaTablaEntidad.id_usuario_baja,
                ParcelaTablaEntidad.fecha_baja,ParcelaTablaEntidad.parcela_nroorden},ParcelaTablaEntidad.parcela_circunscripcion + "=? AND " +
                ParcelaTablaEntidad.parcela_seccion + "=? AND " + ParcelaTablaEntidad.parcela_manzanaFraccion + "=?"
                ,new String[]{circ.trim(),secc.trim(),manz.trim()},null,null,null);

        cursor.moveToFirst();

        ParcelaEntitie parcela = null;

        if (cursor != null ) {
            parcela = new ParcelaEntitie();
            if  (cursor.moveToFirst()) {
                do {
                    parcela.set_id(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id)));
                    parcela.set_idParcelaServer(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_featid_server)));
                    parcela.set_circunscripcion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_circunscripcion)));
                    parcela.set_seccion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_seccion)));
                    parcela.set_tipoManz(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_tipoManzana)));
                    parcela.set_manzOFracc(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_manzanaFraccion)));
                    parcela.set_parcela(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_parcela)));
                    parcela.set_usrCreacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id_usuario_alta)),database));
                    parcela.set_fechaCreacion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.fecha_alta)));
                    parcela.set_usrModificacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id_usuario_modif)),database));
                    parcela.set_fechaModificacion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.fecha_modif)));
                    parcela.set_usrEliminacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(ParcelaTablaEntidad.id_usuario_baja)),database));
                    parcela.set_fechaEliminacion(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.fecha_baja)));
                    parcela.set_nroOrden(cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_nroorden)));

                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return parcela;

    }

  

	/**
	 * Carga todas las secciones segun su circunscripcion
	 * @param circuns
	 * @return lista de secciones para circuns
	 */
	public List<String> LoadSeccionesByCircuns(String circuns){
		
		String elem;
		
		Cursor cursor = this.myDatabase.query(true,ParcelaTablaEntidad.nombre_tabla,
				        new String[] {ParcelaTablaEntidad.parcela_seccion}, ParcelaTablaEntidad.parcela_circunscripcion + "= ?" ,
						new String[] {circuns},null,null,ParcelaTablaEntidad.parcela_seccion,null);
		
		cursor.moveToFirst();
		
		ArrayList<String> lista = null;
		
		if (cursor != null ) {
			lista = new ArrayList<String>();
		    if  (cursor.moveToFirst()) {
		        do {
		        	elem = cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_seccion));
		        	lista.add(elem);
		        }while (cursor.moveToNext());
		    }
		    cursor.close();
		}
		return lista;
	}
	
	/**
	 * Carga todas las parcelas segun su nomenclatura
	 * @param circuns
	 * @param seccion
	 * @param manzana
	 * @return
	 */
	public List<String> LoadParcelasByNomenclatura(String circuns, String seccion, String manzana){
		
		
		String elem;
		
		Cursor cursor = this.myDatabase.query(ParcelaTablaEntidad.nombre_tabla,
				        new String[] {ParcelaTablaEntidad.parcela_parcela}, ParcelaTablaEntidad.parcela_circunscripcion + "= ? AND "+ 
						ParcelaTablaEntidad.parcela_seccion + "= ? AND " + ParcelaTablaEntidad.parcela_manzanaFraccion + "= ?",
						new String[] {circuns,seccion,manzana},null,null,ParcelaTablaEntidad.parcela_parcela);
		
		cursor.moveToFirst();
		
		ArrayList<String> lista = null;
		
		if (cursor != null ) {
			lista = new ArrayList<String>();
		    if  (cursor.moveToFirst()) {
		        do {
		        	elem = cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_parcela));
		        	lista.add(elem);
		        }while (cursor.moveToNext());
		    }
		    cursor.close();
		}
		return lista;
	}
	
	/**
	 * Carga todas las manzanas para la cirunscripcion y seccion dadas
	 * @param circuns
	 * @param seccion
	 * @return
	 */
	public List<String> LoadManzanasByNomenclatura(String circuns, String seccion){

		String elem;
		
		Cursor cursor = this.myDatabase.query(true,ParcelaTablaEntidad.nombre_tabla,
				        new String[] {ParcelaTablaEntidad.parcela_manzanaFraccion}, ParcelaTablaEntidad.parcela_circunscripcion + "= ? AND "+ 
						ParcelaTablaEntidad.parcela_seccion + "= ?",
						new String[] {circuns,seccion},null,null,ParcelaTablaEntidad.parcela_manzanaFraccion,null);
		
		cursor.moveToFirst();
		
		ArrayList<String> lista = null;
		
		if (cursor != null ) {
			lista = new ArrayList<String>();
		    if  (cursor.moveToFirst()) {
		        do {
		        	elem = cursor.getString(cursor.getColumnIndex(ParcelaTablaEntidad.parcela_manzanaFraccion));
		        	lista.add(elem);
		        }while (cursor.moveToNext());
		    }
		    cursor.close();
		}
		return lista;
	}
	
	/**
	 * Inserta un registro en la tabla de parcelas, y devuelve el id.
	 * @param parcela
	 * @return id
	 */
	public long insertParcela(ParcelaEntitie parcela,Long usrActual){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
		ContentValues values = new ContentValues();
        values.put(ParcelaTablaEntidad.id, parcela.get_id());
        values.put(ParcelaTablaEntidad.parcela_parcela, parcela.get_parcela().trim());
		values.put(ParcelaTablaEntidad.parcela_circunscripcion, parcela.get_circunscripcion().trim());
		values.put(ParcelaTablaEntidad.parcela_manzanaFraccion, parcela.get_manzOFracc().trim());
		values.put(ParcelaTablaEntidad.parcela_tipoManzana, parcela.get_tipoManz().trim());
		values.put(ParcelaTablaEntidad.parcela_seccion, parcela.get_seccion().trim());
        values.put(ParcelaTablaEntidad.parcela_featid_server, parcela.get_idParcelaServer());
        values.put(ParcelaTablaEntidad.id_usuario_alta,usrActual);
        values.put(ParcelaTablaEntidad.fecha_alta,currentDateandTime.toString());
        //TODO Agregar el numero de orden...
        //values.put(ParcelaTablaEntidad.fecha_alta,currentDateandTime.toString());


        long rownum = this.myDatabase.insert(ParcelaTablaEntidad.nombre_tabla, null, values);
		
		return rownum;
	}

    public boolean eliminarParcelaPermanente(Long id) {

        int result;
        try{
            result = this.myDatabase.delete(ParcelaTablaEntidad.nombre_tabla,ParcelaTablaEntidad.id + "=?",new String[]{id.toString()});
        }catch (Exception e){
            Log.e("Al eliminar permanentemente la parcela", e.getMessage());
            return false;
        }
        return result != -1;

    }

    public long getNextIdParcela() {

        Long res = -1L;
        Cursor cursor = this.myDatabase.rawQuery("SELECT MAX(ID) FROM CENSO_PARCELA",null);

        cursor.moveToFirst();

        if (cursor != null ) {

            if  (cursor.moveToFirst()) {
                res = cursor.getLong(cursor.getColumnIndex("MAX(ID)"));
                res++;
            }
            
            cursor.close();
        }
        return res;
    }
}
