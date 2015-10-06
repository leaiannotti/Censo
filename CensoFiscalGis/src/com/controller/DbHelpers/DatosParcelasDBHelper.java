package com.controller.DbHelpers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.censoMain.constantes.Constants;
import com.controller.SQLiteHelpers.SQLiteHelper;
import com.model.bussinesEntities.ActividadEntitie;
import com.model.bussinesEntities.DatosParcelaEntitie;
import com.model.tablasEntidades.DatosParcelaTablaEntidad;

public class DatosParcelasDBHelper {

	public Context myContext;
	public SQLiteHelper openHelper;
	public SQLiteDatabase myDatabase;
    public UsuariosDBHelper usrDbHelper;
    public ParcelasDBHelper parcelasDBHelper;
    public CalleDBHelper calleDBHelper;
    public ActividadDBHelper actividadDBHelper;

    protected String[] columns =  new String[] {DatosParcelaTablaEntidad.id, DatosParcelaTablaEntidad.datos_parcela_idparcela,DatosParcelaTablaEntidad.datos_parcela_idcalle,
            DatosParcelaTablaEntidad.datos_parcela_idcalleModif,
            DatosParcelaTablaEntidad.datos_parcela_categViviUni,DatosParcelaTablaEntidad.datos_parcela_categViviUniModif,
            DatosParcelaTablaEntidad.datos_parcela_categViviMulti,DatosParcelaTablaEntidad.datos_parcela_categViviMultiModif,
            DatosParcelaTablaEntidad.datos_parcela_categComercio,DatosParcelaTablaEntidad.datos_parcela_categComercioModif,
            DatosParcelaTablaEntidad.datos_parcela_categIndustria,DatosParcelaTablaEntidad.datos_parcela_categIndustriaModif,
            DatosParcelaTablaEntidad.datos_parcela_categBaldio,DatosParcelaTablaEntidad.datos_parcela_categBaldioModif,
            DatosParcelaTablaEntidad.datos_parcela_categObraConst,DatosParcelaTablaEntidad.datos_parcela_categObraConstModif,
            DatosParcelaTablaEntidad.datos_parcela_altura,DatosParcelaTablaEntidad.datos_parcela_alturaModificada,
            DatosParcelaTablaEntidad.datos_parcela_accion,DatosParcelaTablaEntidad.datos_parcela_longitud,DatosParcelaTablaEntidad.datos_parcela_latitud,
            DatosParcelaTablaEntidad.datos_parcela_observaciones,DatosParcelaTablaEntidad.datos_parcela_pathPhoto,DatosParcelaTablaEntidad.datos_parcela_deviceNumber,
            DatosParcelaTablaEntidad.id_usuario_alta,DatosParcelaTablaEntidad.id_usuario_modif,DatosParcelaTablaEntidad.id_usuario_baja,
            DatosParcelaTablaEntidad.fecha_alta,DatosParcelaTablaEntidad.fecha_modif,DatosParcelaTablaEntidad.fecha_baja,DatosParcelaTablaEntidad.datos_parcela_enviado};
	
	/**
	 * Constructor class
	 * @param con
	 */
	public DatosParcelasDBHelper(Context con){
		this.myContext = con;
		
		this.openHelper = new SQLiteHelper(this.myContext);
		try {
			this.openHelper.createDataBase();
	 	} catch (IOException ioe) {
	 		throw new Error("Unable to create database");
	 	}
        this.usrDbHelper = new UsuariosDBHelper(con);
        this.parcelasDBHelper = new ParcelasDBHelper(con);
        this.calleDBHelper = new CalleDBHelper(con);
        this.actividadDBHelper = new ActividadDBHelper(con);
	}	
	
	/**
	 * Open the database
	 * 
	 */
	public DatosParcelasDBHelper open(){
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
	 * Inserta un registro en la tabla de datos parcela, y devuelve el id.
	 * @param datosParcela
	 * @return id
	 */
	public long insertDatosParcela(DatosParcelaEntitie datosParcela,Long usrId){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDateandTime = sdf.format(new Date());
		ContentValues values = new ContentValues();
		values.put(DatosParcelaTablaEntidad.datos_parcela_accion, Constants.ACTION_INSERT);

        values.put(DatosParcelaTablaEntidad.id,datosParcela.get_id());
		values.put(DatosParcelaTablaEntidad.datos_parcela_idparcela, datosParcela.get_nomenclatura() != null ? datosParcela.get_nomenclatura().get_id() : 0L );
		values.put(DatosParcelaTablaEntidad.datos_parcela_idcalle, datosParcela.get_calle().get_id());
        //TODO CalleModificada puede ser nula (segun mi esquema de datos NO)
		values.put(DatosParcelaTablaEntidad.datos_parcela_idcalleModif, datosParcela.get_calleModificada().get_id());
        //TODO datosParcela.get_categorias() es null
		values.put(DatosParcelaTablaEntidad.datos_parcela_categViviUni, datosParcela.get_categorias().get(Constants.VIVIENDA_UNIFAMILIAR_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categViviMulti, datosParcela.get_categorias().get(Constants.VIVIENDA_MULTIFAMILIAR_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categBaldio, datosParcela.get_categorias().get(Constants.BALDIO_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categObraConst, datosParcela.get_categorias().get(Constants.OBRACONST_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categComercio, datosParcela.get_categorias().get(Constants.COMERCIO_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categIndustria, datosParcela.get_categorias().get(Constants.INDUSTRIA_ABV));
        //TODO datosParcela.get_categoriasModificadas() es null
        values.put(DatosParcelaTablaEntidad.datos_parcela_categViviUniModif, datosParcela.get_categoriasModificadas().get(Constants.VIVIENDA_UNIFAMILIAR_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categViviMultiModif, datosParcela.get_categoriasModificadas().get(Constants.VIVIENDA_MULTIFAMILIAR_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categBaldioModif, datosParcela.get_categoriasModificadas().get(Constants.BALDIO_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categObraConstModif, datosParcela.get_categoriasModificadas().get(Constants.OBRACONST_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categComercioModif, datosParcela.get_categoriasModificadas().get(Constants.COMERCIO_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categIndustriaModif, datosParcela.get_categoriasModificadas().get(Constants.INDUSTRIA_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_altura, datosParcela.get_altura());
        values.put(DatosParcelaTablaEntidad.datos_parcela_alturaModificada,datosParcela.get_alturaModificada());
        values.put(DatosParcelaTablaEntidad.datos_parcela_observaciones,datosParcela.get_observaciones());
		values.put(DatosParcelaTablaEntidad.datos_parcela_pathPhoto,datosParcela.get_pathImagen());
        values.put(DatosParcelaTablaEntidad.datos_parcela_accion,datosParcela.get_estado());
        values.put(DatosParcelaTablaEntidad.datos_parcela_deviceNumber,datosParcela.get_deviceNumber());
        values.put(DatosParcelaTablaEntidad.id_usuario_alta,usrId);
        values.put(DatosParcelaTablaEntidad.fecha_alta,currentDateandTime.toString());
        values.put(DatosParcelaTablaEntidad.datos_parcela_longitud,datosParcela.get_longitud());
        values.put(DatosParcelaTablaEntidad.datos_parcela_latitud,datosParcela.get_latitud());
        values.put(DatosParcelaTablaEntidad.datos_parcela_enviado,"0");//No enviado


        long rownum = this.myDatabase.insert(DatosParcelaTablaEntidad.nombre_tabla, null, values);

        if(rownum != -1){
        /**Update actividades*/
            List<ActividadEntitie> actividades = datosParcela.get_actividades();
            boolean ok;
            for (ActividadEntitie a : actividades){
                ok = actividadDBHelper.insertRel(a.get_id(),datosParcela.get_id(),false,this.myDatabase);
                if(!ok) break;
            }
        }
        return rownum;
	}

	/**
	 * Retorna los datos para la parcela. 
	 * @param featIdParcela
	 * @return
	 */
	public ArrayList<DatosParcelaEntitie> LoadDatosParcelaByParcela(Long featIdParcela) {

        Cursor cursor = this.myDatabase.query(DatosParcelaTablaEntidad.nombre_tabla,columns,
                                DatosParcelaTablaEntidad.datos_parcela_idparcela + " =? ",
						        new String[]{featIdParcela.toString()},null,null,DatosParcelaTablaEntidad.datos_parcela_idcalle);
		
		cursor.moveToFirst();
		
		DatosParcelaEntitie datos = null;
		ArrayList<DatosParcelaEntitie> listDatos = null;
		
		
		if (cursor != null ) {
		    if  (cursor.moveToFirst()) {
		    	listDatos = new ArrayList<DatosParcelaEntitie>();
		        do {
		        	datos = new DatosParcelaEntitie();
		        	datos.set_nomenclatura(parcelasDBHelper.LoadParcelaById(featIdParcela,this.myDatabase));
		        	datos.set_id(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id)));
		        	datos.set_calle(calleDBHelper.LoadCalleById(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idcalle)),this.myDatabase));
                    datos.set_calleModificada(calleDBHelper.LoadCalleById(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idcalleModif)),this.myDatabase));
                    datos.set_altura(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_altura)));
                    datos.set_alturaModificada(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_alturaModificada)));
		        	datos.set_categorias(crearDiccionarioCategorias(cursor,false));
		        	datos.set_categoriasModificadas(crearDiccionarioCategorias(cursor, true));
                    datos.set_actividades(actividadDBHelper.LoadActividadesByDatosParcela(datos.get_id(),false,this.myDatabase));
                    datos.set_actividadesModificadas(actividadDBHelper.LoadActividadesByDatosParcela(datos.get_id(), true,this.myDatabase));
		        	datos.set_observaciones(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_observaciones)));
		        	datos.set_estado(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_accion)));
		        	datos.set_enviado(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_enviado)));
                    datos.set_pathImagen(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_pathPhoto)));
		        	datos.set_latitud(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_latitud)));
                    datos.set_longitud(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_longitud)));
                    datos.set_deviceNumber(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_deviceNumber)));
                    datos.set_usrCreacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_alta)),this.myDatabase));
                    datos.set_usrModificacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_modif)),this.myDatabase));
                    datos.set_usrEliminacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_baja)),this.myDatabase));
                    datos.set_fechaCreacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_alta)));
                    datos.set_fechaModificacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_modif)));
                    datos.set_fechaEliminacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_baja)));


		        	listDatos.add(datos);
		        }while (cursor.moveToNext());
		    }
		    
		    cursor.close();
		    
		}
		return listDatos;
	}

    /**
	 * Retorna los datos para la parcela con el id parametrizado. 
	 * @param id
	 * @return
	 */
	public DatosParcelaEntitie LoadDatosParcelaById(Long id) {
		
		Cursor cursor = this.myDatabase.query(DatosParcelaTablaEntidad.nombre_tabla,columns
				        , DatosParcelaTablaEntidad.id + "= ? ",
						new String[] {id.toString()},null,null,DatosParcelaTablaEntidad.id);
		
		cursor.moveToFirst();
		
		DatosParcelaEntitie datos = null;
		
		if (cursor != null ) {
			
		    if  (cursor.moveToFirst()) {
		        do {
		        	datos = new DatosParcelaEntitie();
                    datos.set_nomenclatura(parcelasDBHelper.LoadParcelaById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idparcela)),this.myDatabase));
                    datos.set_id(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id)));
                    datos.set_calle(calleDBHelper.LoadCalleById(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idcalle)),this.myDatabase));
                    datos.set_calleModificada(calleDBHelper.LoadCalleById(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idcalleModif)),this.myDatabase));
                    datos.set_altura(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_altura)));
                    datos.set_alturaModificada(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_alturaModificada)));
                    datos.set_categorias(crearDiccionarioCategorias(cursor, false));
                    datos.set_categoriasModificadas(crearDiccionarioCategorias(cursor, true));
                    datos.set_actividades(actividadDBHelper.LoadActividadesByDatosParcela(datos.get_id(), false,this.myDatabase));
                    datos.set_actividadesModificadas(actividadDBHelper.LoadActividadesByDatosParcela(datos.get_id(), true,this.myDatabase));
                    datos.set_observaciones(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_observaciones)));
                    datos.set_estado(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_accion)));
                    datos.set_enviado(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_enviado)));
                    datos.set_pathImagen(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_pathPhoto)));
                    datos.set_latitud(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_latitud)));
                    datos.set_longitud(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_longitud)));
                    datos.set_deviceNumber(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_deviceNumber)));
                    datos.set_usrCreacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_alta)),this.myDatabase));
                    datos.set_usrModificacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_modif)),this.myDatabase));
                    datos.set_usrEliminacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_baja)),this.myDatabase));
                    datos.set_fechaCreacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_alta)));
                    datos.set_fechaModificacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_modif)));
                    datos.set_fechaEliminacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_baja)));

		        }while (cursor.moveToNext());
		    }
		    
		    cursor.close();
		}
		return datos;
	}

	/**
	 * Retorna los datos para la parcela que fueron modificados.
	 * @return
	 */
	public List<DatosParcelaEntitie> LoadDatosParcelaModificados() {
		
		List<DatosParcelaEntitie> datosList = new ArrayList<DatosParcelaEntitie>();
		Cursor cursor = null;

		try{
		
			cursor = this.myDatabase.query(DatosParcelaTablaEntidad.nombre_tabla,
					       columns, DatosParcelaTablaEntidad.datos_parcela_accion + "= ? AND " + DatosParcelaTablaEntidad.datos_parcela_enviado + " =? ",
							new String[] {Constants.ACTION_UPDATE,"0"},null,null,DatosParcelaTablaEntidad.id);
			
			//cursor.moveToFirst();
			
			DatosParcelaEntitie datos = null;
			
			if (cursor != null ) {
				
			    if  (cursor.moveToFirst()) {
			    	
			        do {
			        	datos = new DatosParcelaEntitie();
	                    datos.set_nomenclatura(parcelasDBHelper.LoadParcelaById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idparcela)),this.myDatabase));
	                    datos.set_id(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id)));
	                    datos.set_calle(calleDBHelper.LoadCalleById(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idcalle)),this.myDatabase));
	                    datos.set_calleModificada(calleDBHelper.LoadCalleById(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idcalleModif)),this.myDatabase));
	                    datos.set_altura(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_altura)));
	                    datos.set_alturaModificada(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_alturaModificada)));
	                    datos.set_categorias(crearDiccionarioCategorias(cursor, false));
	                    datos.set_categoriasModificadas(crearDiccionarioCategorias(cursor, true));
	                    datos.set_actividades(actividadDBHelper.LoadActividadesByDatosParcela(datos.get_id(), false,this.myDatabase));
	                    datos.set_actividadesModificadas(actividadDBHelper.LoadActividadesByDatosParcela(datos.get_id(), true,this.myDatabase));
	                    datos.set_observaciones(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_observaciones)));
	                    datos.set_estado(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_accion)));
	                    datos.set_enviado(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_enviado)));
	                    datos.set_pathImagen(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_pathPhoto)));
	                    datos.set_latitud(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_latitud)));
	                    datos.set_longitud(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_longitud)));
	                    datos.set_deviceNumber(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_deviceNumber)));
	                    datos.set_usrCreacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_alta)),this.myDatabase));
	                    datos.set_usrModificacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_modif)),this.myDatabase));
	                    datos.set_usrEliminacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_baja)),this.myDatabase));
	                    datos.set_fechaCreacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_alta)));
	                    datos.set_fechaModificacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_modif)));
	                    datos.set_fechaEliminacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_baja)));
	
			        	datosList.add(datos);
	                    datos = null;
	                    
			        }while (cursor.moveToNext());
			    }
			}
		}
		catch(Exception e){
			Log.e("Error al cargar los modificados", e.getMessage());
			
		}
		finally{
		   if(cursor != null)
		        cursor.close();
		}
		
		return datosList;
	}
	
	/**
	 * Retorna los datos para la parcela que fueron insertados.
	 * @return
	 */
	public List<DatosParcelaEntitie> LoadDatosParcelaInsertados() {
		
		List<DatosParcelaEntitie> datosList = new ArrayList<DatosParcelaEntitie>();
		
		Cursor cursor = null;
		
		try{
			cursor = this.myDatabase.query(DatosParcelaTablaEntidad.nombre_tabla,
					      columns, DatosParcelaTablaEntidad.datos_parcela_accion + "= ?  AND " + DatosParcelaTablaEntidad.datos_parcela_enviado + " =? ",
							new String[] {Constants.ACTION_INSERT,"0"},null,null,DatosParcelaTablaEntidad.id);
			
			DatosParcelaEntitie datos = null;
			
			if (cursor != null ) {
				
			    if  (cursor.moveToFirst()) {
			    	
			        do {
			        	datos = new DatosParcelaEntitie();
	                    datos.set_nomenclatura(parcelasDBHelper.LoadParcelaById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idparcela)),this.myDatabase));
	                    datos.set_id(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id)));
	                    datos.set_calle(calleDBHelper.LoadCalleById(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idcalle)),this.myDatabase));
	                    datos.set_calleModificada(calleDBHelper.LoadCalleById(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idcalleModif)),this.myDatabase));
	                    datos.set_altura(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_altura)));
	                    datos.set_alturaModificada(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_alturaModificada)));
	                    datos.set_categorias(crearDiccionarioCategorias(cursor,false));
	                    datos.set_categoriasModificadas(crearDiccionarioCategorias(cursor,true));
	                    datos.set_actividades(actividadDBHelper.LoadActividadesByDatosParcela(datos.get_id(),false,this.myDatabase));
	                    datos.set_actividadesModificadas(actividadDBHelper.LoadActividadesByDatosParcela(datos.get_id(),true,this.myDatabase));
	                    datos.set_observaciones(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_observaciones)));
	                    datos.set_estado(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_accion)));
	                    datos.set_enviado(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_enviado)));
	                    datos.set_pathImagen(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_pathPhoto)));
	                    datos.set_latitud(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_latitud)));
	                    datos.set_longitud(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_longitud)));
	                    datos.set_deviceNumber(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_deviceNumber)));
	                    datos.set_usrCreacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_alta)),this.myDatabase));
	                    datos.set_usrModificacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_modif)),this.myDatabase));
	                    datos.set_usrEliminacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_baja)),this.myDatabase));
	                    datos.set_fechaCreacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_alta)));
	                    datos.set_fechaModificacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_modif)));
	                    datos.set_fechaEliminacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_baja)));
	
	                    datosList.add(datos);
	                    
	                    datos = null;
			        	
			        }while (cursor.moveToNext());
			    }
			}
		}catch(Exception e){
			Log.e("Error al obtener los insertados", e.getMessage());
			
		}finally{
		   if(cursor != null)
		        cursor.close();
		}
		return datosList;
	}
	
	/**
	 * Retorna los datos para la parcela que fueron eliminados.
	 * @return
	 */
	public List<DatosParcelaEntitie> LoadDatosParcelaEliminados() {
		
		List<DatosParcelaEntitie> datosList = new ArrayList<DatosParcelaEntitie>();
		
		Cursor cursor = null;
		
		try{
			
			cursor = this.myDatabase.query(DatosParcelaTablaEntidad.nombre_tabla,
					        columns, DatosParcelaTablaEntidad.datos_parcela_accion + "= ?  AND " + DatosParcelaTablaEntidad.datos_parcela_enviado + " =? ",
							new String[] {Constants.ACTION_DELETE,"0"},null,null,DatosParcelaTablaEntidad.id);
			
			DatosParcelaEntitie datos = null;
			
			if (cursor != null ) {
				
			    if  (cursor.moveToFirst()) {
			        do {
			        	datos = new DatosParcelaEntitie();
	                    datos.set_nomenclatura(parcelasDBHelper.LoadParcelaById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idparcela)),this.myDatabase));
	                    datos.set_id(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id)));
	                    datos.set_calle(calleDBHelper.LoadCalleById(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idcalle)),this.myDatabase));
	                    datos.set_calleModificada(calleDBHelper.LoadCalleById(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_idcalleModif)),this.myDatabase));
	                    datos.set_altura(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_altura)));
	                    datos.set_alturaModificada(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_alturaModificada)));
	                    datos.set_categorias(crearDiccionarioCategorias(cursor, false));
	                    datos.set_categoriasModificadas(crearDiccionarioCategorias(cursor, true));
	                    datos.set_actividades(actividadDBHelper.LoadActividadesByDatosParcela(datos.get_id(), false,this.myDatabase));
	                    datos.set_actividadesModificadas(actividadDBHelper.LoadActividadesByDatosParcela(datos.get_id(), true,this.myDatabase));
	                    datos.set_observaciones(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_observaciones)));
	                    datos.set_estado(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_accion)));
	                    datos.set_enviado(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_enviado)));
	                    datos.set_pathImagen(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_pathPhoto)));
	                    datos.set_latitud(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_latitud)));
	                    datos.set_longitud(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_longitud)));
	                    datos.set_deviceNumber(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_deviceNumber)));
	                    datos.set_usrCreacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_alta)),this.myDatabase));
	                    datos.set_usrModificacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_modif)),this.myDatabase));
	                    datos.set_usrEliminacion(usrDbHelper.loadUsuarioById(cursor.getLong(cursor.getColumnIndex(DatosParcelaTablaEntidad.id_usuario_baja)),this.myDatabase));
	                    datos.set_fechaCreacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_alta)));
	                    datos.set_fechaModificacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_modif)));
	                    datos.set_fechaEliminacion(cursor.getString(cursor.getColumnIndex(DatosParcelaTablaEntidad.fecha_baja)));
	
	                    datosList.add(datos);
	                    
	                    datos = null;
			        }while (cursor.moveToNext());
			    }
			}
		}catch(Exception e){
			Log.e("Error al obtener los eliminados", e.getMessage());
			
		}finally{
		   if(cursor != null)
		        cursor.close();
		}
		return datosList;
	}

	/**
	 * Realiza un update en la tabla pasando como parametros la entidad
	 * que contiene los datos a actualizar (notar que el id es el mismo)
	 * @param datosParcela
	 * @return filas afectadas
	 */
	public long updateDatosParcela(DatosParcelaEntitie datosParcela,String accion,Long userModif){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDateandTime = sdf.format(new Date());
		ContentValues values = new ContentValues();

        if(accion.equals(Constants.ACTION_UPDATE) && datosParcela.get_estado().equals(Constants.ACTION_INSERT))
            values.put(DatosParcelaTablaEntidad.datos_parcela_accion, Constants.ACTION_INSERT);
        else
		    values.put(DatosParcelaTablaEntidad.datos_parcela_accion, accion);

        values.put(DatosParcelaTablaEntidad.datos_parcela_idparcela,datosParcela.get_nomenclatura().get_id());
		//values.put(DatosParcelaTablaEntidad.datos_parcela_idcalle, datosParcela.get_calle().get_id());
		values.put(DatosParcelaTablaEntidad.datos_parcela_idcalleModif,datosParcela.get_calleModificada().get_id());
		//values.put(DatosParcelaTablaEntidad.datos_parcela_altura, datosParcela.get_altura());
        values.put(DatosParcelaTablaEntidad.datos_parcela_alturaModificada, datosParcela.get_alturaModificada());
		//values.put(DatosParcelaTablaEntidad.datos_parcela_categViviUni, datosParcela.get_categorias().get(Constants.VIVIENDA_UNIFAMILIAR_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categViviUniModif, datosParcela.get_categoriasModificadas().get(Constants.VIVIENDA_UNIFAMILIAR_ABV));
        //values.put(DatosParcelaTablaEntidad.datos_parcela_categViviMulti, datosParcela.get_categorias().get(Constants.VIVIENDA_MULTIFAMILIAR_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categViviMultiModif, datosParcela.get_categoriasModificadas().get(Constants.VIVIENDA_MULTIFAMILIAR_ABV));
        //values.put(DatosParcelaTablaEntidad.datos_parcela_categComercio, datosParcela.get_categorias().get(Constants.COMERCIO_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categComercioModif, datosParcela.get_categoriasModificadas().get(Constants.COMERCIO_ABV));
        //values.put(DatosParcelaTablaEntidad.datos_parcela_categIndustria, datosParcela.get_categorias().get(Constants.INDUSTRIA_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categIndustriaModif, datosParcela.get_categoriasModificadas().get(Constants.INDUSTRIA_ABV));
        //values.put(DatosParcelaTablaEntidad.datos_parcela_categBaldio, datosParcela.get_categorias().get(Constants.BALDIO_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categBaldioModif, datosParcela.get_categoriasModificadas().get(Constants.BALDIO_ABV));
        //values.put(DatosParcelaTablaEntidad.datos_parcela_categObraConst, datosParcela.get_categorias().get(Constants.OBRACONST_ABV));
        values.put(DatosParcelaTablaEntidad.datos_parcela_categObraConstModif, datosParcela.get_categoriasModificadas().get(Constants.OBRACONST_ABV));
      	values.put(DatosParcelaTablaEntidad.datos_parcela_observaciones, datosParcela.get_observaciones());
		values.put(DatosParcelaTablaEntidad.datos_parcela_pathPhoto, datosParcela.get_pathImagen());
        values.put(DatosParcelaTablaEntidad.datos_parcela_longitud, datosParcela.get_longitud());
        values.put(DatosParcelaTablaEntidad.datos_parcela_latitud, datosParcela.get_latitud());
        values.put(DatosParcelaTablaEntidad.datos_parcela_deviceNumber, datosParcela.get_deviceNumber());

        if(accion.equals(Constants.ACTION_DELETE)){
            values.put(DatosParcelaTablaEntidad.id_usuario_baja,userModif);
            values.put(DatosParcelaTablaEntidad.fecha_baja,currentDateandTime.toString());
        }else{
            values.put(DatosParcelaTablaEntidad.id_usuario_modif,userModif);
            values.put(DatosParcelaTablaEntidad.fecha_modif,currentDateandTime.toString());
            values.put(DatosParcelaTablaEntidad.id_usuario_baja," ");
            values.put(DatosParcelaTablaEntidad.fecha_baja," ");
        }

        /*Update actividades*/

        if(!accion.equals(Constants.ACTION_DELETE)){

            List<ActividadEntitie> actividadesModificadas = datosParcela.get_actividadesModificadas();

            boolean existe,ok;

            for (ActividadEntitie a : actividadesModificadas){
                existe= actividadDBHelper.existeActividad(a.get_id(),datosParcela.get_id(),this.myDatabase);
                if(existe)
                    ok = actividadDBHelper.updateRel(a.get_id(),datosParcela.get_id(),true,this.myDatabase);
                else
                    ok = actividadDBHelper.insertRel(a.get_id(),datosParcela.get_id(),true,this.myDatabase);

                if(!ok) break;
            }

        }
        
        int result = this.myDatabase.update(DatosParcelaTablaEntidad.nombre_tabla,
                values, DatosParcelaTablaEntidad.id + " = ? ",new String[]{datosParcela.get_id().toString()});
        
        return result;
	}

    /**
     *
     * @param cursor
     * @param categoriasModificadas
     * @return
     */
    private HashMap<String, Integer> crearDiccionarioCategorias(Cursor cursor,boolean categoriasModificadas) {

        HashMap<String,Integer> d = new HashMap<String, Integer>();

        if(!categoriasModificadas){
            d.put(Constants.VIVIENDA_UNIFAMILIAR_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categViviUni)));
            d.put(Constants.VIVIENDA_MULTIFAMILIAR_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categViviMulti)));
            d.put(Constants.COMERCIO_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categComercio)));
            d.put(Constants.INDUSTRIA_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categIndustria)));
            d.put(Constants.BALDIO_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categBaldio)));
            d.put(Constants.OBRACONST_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categObraConst)));
        }else{
            d.put(Constants.VIVIENDA_UNIFAMILIAR_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categViviUniModif)));
            d.put(Constants.VIVIENDA_MULTIFAMILIAR_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categViviMultiModif)));
            d.put(Constants.COMERCIO_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categComercioModif)));
            d.put(Constants.INDUSTRIA_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categIndustriaModif)));
            d.put(Constants.BALDIO_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categBaldioModif)));
            d.put(Constants.OBRACONST_ABV,cursor.getInt(cursor.getColumnIndex(DatosParcelaTablaEntidad.datos_parcela_categObraConstModif)));
        }

        return d;
    }

    public boolean eliminarDatosParcelaPermanente(Long id) {

        int result;
        try{
            result = this.myDatabase.delete(DatosParcelaTablaEntidad.nombre_tabla,DatosParcelaTablaEntidad.id + "=?",new String[]{id.toString()});
        }catch (Exception e){
            Log.e("Al eliminar permanentemente los datos de parcela", e.getMessage());
            return false;
        }
        return result != -1;
    }


    public Long getNextIdDato() {

        Long res = -1L;
        Cursor cursor = this.myDatabase.rawQuery("SELECT MAX(ID) FROM CENSO_DATOS_PARCELA", null);

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

    public String getNroTablet() {

        String res = "";
        Cursor cursor = this.myDatabase.rawQuery("SELECT PARAM_VALUE FROM CENSO_PARAMS WHERE PARAM_DESC = 'NROTABLET'", null);

        cursor.moveToFirst();

        if (cursor != null ) {

            if  (cursor.moveToFirst()) {
                res = cursor.getString(cursor.getColumnIndex("PARAM_VALUE"));
            }
            
            cursor.close();
        }
        
        return res;

    }

    public boolean updateEnviado(DatosParcelaEntitie datosParcelaEntitie) {

        ContentValues values = new ContentValues();

        values.put(DatosParcelaTablaEntidad.datos_parcela_enviado, "1");

        int cantidadReg = this.myDatabase.update(DatosParcelaTablaEntidad.nombre_tabla,
                values, DatosParcelaTablaEntidad.id + " = ? ",new String[]{datosParcelaEntitie.get_id().toString()});

        return cantidadReg == 1;
    }
}
