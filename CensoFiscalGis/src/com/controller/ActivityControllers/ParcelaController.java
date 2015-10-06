package com.controller.ActivityControllers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.censoMain.constantes.Constants;
import com.controller.ReadFile;
import com.controller.DbHelpers.ActividadDBHelper;
import com.controller.DbHelpers.CalleDBHelper;
import com.controller.DbHelpers.CategoriaDBHelper;
import com.controller.DbHelpers.DatosParcelasDBHelper;
import com.controller.DbHelpers.ParcelasDBHelper;
import com.controller.DbHelpers.UsuariosDBHelper;
import com.model.bussinesEntities.ActividadEntitie;
import com.model.bussinesEntities.CalleEntitie;
import com.model.bussinesEntities.CategoriaEntitie;
import com.model.bussinesEntities.DatosParcelaEntitie;
import com.model.bussinesEntities.ParcelaEntitie;
import com.model.tablasEntidades.ParcelaTablaEntidad;
import com.model.wsConnection.CallSoap;


public class ParcelaController {

    private Context con;

	private ParcelasDBHelper parcelasDbHelper;
    private DatosParcelasDBHelper datosParcelaDbHelper;
    private CategoriaDBHelper categoriasDbHelper;
    private ActividadDBHelper actividadDbHelper;
    private CalleDBHelper calleDbHelper;
    private UsuariosDBHelper usuariosDbHelper;

    private Long usrActual;
	
	/**
	 * Abro todas las conexiones
	 * @param con
	 */
	public ParcelaController(Context con, Long usrId){
        this.usrActual = usrId;
        this.con = con;
		parcelasDbHelper = new ParcelasDBHelper(con);
		datosParcelaDbHelper = new DatosParcelasDBHelper(con);
		categoriasDbHelper = new CategoriaDBHelper(con);
		actividadDbHelper = new ActividadDBHelper(con);
		calleDbHelper = new CalleDBHelper(con);
		usuariosDbHelper = new UsuariosDBHelper(con);
		usuariosDbHelper.open();
		categoriasDbHelper.open();
		actividadDbHelper.open();
		calleDbHelper.open();
		datosParcelaDbHelper.open();
		parcelasDbHelper.open();
	}
	
	/**
	 * Retorna la lista de las circunscripciones cargadas
	 * @return
	 */
	public List<String> CargarCircunscripciones()
	{
		List<String> circunscripciones;
		
		try {
			circunscripciones = parcelasDbHelper.LoadAllByTipo(ParcelaTablaEntidad.parcela_circunscripcion);
		} catch (Exception e) {
			Log.e("ParcelaController", "Al cargar las circunscripciones " + e.getMessage());
			circunscripciones = null;
		}
		
		return circunscripciones;
	}
	
	/**
	 * Retorna las secciones para la circunscripcion pasada por parametro
	 * @param circuns
	 * @return
	 */
	public List<String> CargarSeccionesPorCircunscripcion(String circuns)
	{
		List<String> secciones = null;
		
		try {
			secciones =	parcelasDbHelper.LoadSeccionesByCircuns(circuns);
		} catch (Exception e) {
			Log.e("ParcelaController", "Al cargar las secciones " + e.getMessage() );
		}
		
		return secciones;
	}

	/**
	 * Retorna las manzanas para la circunscripcion y seccion dadas
	 * @param circuns
	 * @param seccion
	 * @return
	 */
	public List<String> CargarManzanasPorSeccion (String circuns,String seccion)
	{
		List<String> manzanas = null;
		
		try {
			manzanas = parcelasDbHelper.LoadManzanasByNomenclatura(circuns, seccion); 
		} catch (Exception e) {
			Log.e("ParcelaController", "Al cargar las manzanas " + e.getMessage());
		}
		
		return manzanas;
		
	}
	
	/**
	 * Retorna la lista de los datos para cada parcela (Se puede obtener a partir de la tabla de datosparcela)
	 * @param circuns
	 * @param seccion
	 * @param manzana
	 * @return
	 */
	public ArrayList<DatosParcelaEntitie> CargarDatosParcelas (String circuns,String seccion, String manzana)
	{
		
		List<ParcelaEntitie> parcela = parcelasDbHelper.LoadParcelasEntitiesByNomenclatura(circuns, seccion, manzana);

        ArrayList<DatosParcelaEntitie> listaDatos = new ArrayList<DatosParcelaEntitie>();

        for(ParcelaEntitie p : parcela)
        {
            try{
                listaDatos.addAll(datosParcelaDbHelper.LoadDatosParcelaByParcela(p.get_id()));
            }catch(Exception e){
                Log.e("ParcelaController-CargarDatosParcelas", "Al cargar los datos de la parcela" + e.getMessage());
            }
        }
		return listaDatos;
		
	}

    /**
     * Retorna la lista de los datos para cada parcela (Se puede obtener a partir de la tabla de datosparcela)
     * @return
     */
    public DatosParcelaEntitie CargarDatosParcelasById (String id)
    {
        DatosParcelaEntitie parcela = datosParcelaDbHelper.LoadDatosParcelaById(Long.parseLong(id));
        return parcela;
    }
	
	/**
	 * Retorna todas las actividades cargadas en la base de datos
	 * @return
	 */
	public List<ActividadEntitie> CargarActividades()
	{
		List<ActividadEntitie> actividades = null;
		
		try {
			actividades = actividadDbHelper.LoadAllActividades(); 
		} catch (Exception e) {
			Log.e("ParcelaController", "Al cargar las actividades " + e.getMessage());
		}
		
		actividades.add(new ActividadEntitie("0",""));
		
		return actividades;
		
	}
	
	/**
	 * Retorna todas las calles cargadas en la base de datos
	 * @return
	 */
	public List<CalleEntitie> CargarCalles()
	{
		List<CalleEntitie> calles = null;
		
		try {
			calles = calleDbHelper.LoadAllCalles(); 
		} catch (Exception e) {
			Log.e("ParcelaController", "Al cargar las calles " + e.getMessage());
		}

		calles.add(new CalleEntitie("0",""));

		return calles;
		
	}
	
	
	/**
	 * Retorna todas las calles cargadas en la base de datos para la manzana.
	 * @return
	 */
	public List<CalleEntitie> CargarCallesPorManzana(String manz,String circ,String sec)
	{
		List<CalleEntitie> calles = null;
		List<CalleEntitie> callesNormalizadas = null;
		
		try {
			calles = calleDbHelper.LoadAllCallesByManzana(circ, sec, manz); 
			callesNormalizadas = calleDbHelper.LoadAllCallesNormalizadas();
			callesNormalizadas.removeAll(calles);
			calles.addAll(callesNormalizadas);
		} catch (Exception e) {
			Log.e("ParcelaController", "Al cargar las calles por manzana " + e.getMessage());
		}
		return calles;
		
	}
	
	/**
	 * Retorna todas las categorias cargadas en la base de datos
	 * @return
	 */
	public List<CategoriaEntitie> CargarCategorias()
	{
		List<CategoriaEntitie> categorias = null;
		
		try {
			categorias = categoriasDbHelper.LoadAllCategorias();
		} catch (Exception e) {
			Log.e("ParcelaController", "Al cargar las categorias " + e.getMessage());
		}
		
		categorias.add(new CategoriaEntitie("0",""));
		
		return categorias;
		
	}
	
	
	public void InicializarDatos()
	{
		this.InicializarCalles();
		//this.InicializarActividades();
		//this.InicializarCategorias();
		//this.InicializarParcelas();
		//this.InicializarDatosParcelas();
		
	}
	
	/*
	//select idcalle || ' @ ' || ncalle || ' @ ' || naltura || ' @ ' || nomenclatura_parcela || ' @ ' ||
	//xvalor || ' @ ' || xvalat || ' @ ' || c_codact || ' @ ' || c_actividad || ' @ ' || observacion  from mda_censo
	@SuppressWarnings("unused")
	private void InicializarDatosParcelas() {
		
		ArrayList<DatosParcelaEntitie> datosParcelas = ReadFile.LeerArchivoDatosParcela("/ArchivosCenso/datosParcelaCenso1.txt");
		
		for (DatosParcelaEntitie d : datosParcelas) {
			
			try{
				datosParcelaDbHelper.insertDatosParcela(d,this.usrActual) ;
			}catch(Exception e){
				Log.e("ParcelaController", "Error al insertar los datos de parcela");
				
			}
			
		}
		
	}

	@SuppressWarnings("unused")
	private void InicializarParcelas() {
		
		ArrayList<ParcelaEntitie> parcelas = ReadFile.LeerArchivoParcela("/ArchivosCenso/parcelaCenso.txt");
		
		for (ParcelaEntitie p : parcelas) {
			try{
				parcelasDbHelper.insertParcela(p,this.usrActual) ;
			}catch(Exception e){
				Log.e("ParcelaController", "Error al insertar la parcela");
			}
		}
	}

	@SuppressWarnings("unused")
	private void InicializarCategorias() {
		
		ArrayList<CategoriaEntitie> categorias = ReadFile.LeerArchivoCategoria("/ArchivosCenso/categoriasCenso.txt");
		
		for (CategoriaEntitie c : categorias) {
			
			try{
				categoriasDbHelper.insertCategoria(c);
			}catch(Exception e){
				Log.e("ParcelaController", "Error al insertar la categoria");
				
			}
		}
	}

	@SuppressWarnings("unused")
	private void InicializarActividades() {
		ArrayList<ActividadEntitie> actividades = ReadFile.LeerArchivoActividades("/ArchivosCenso/actividadesCenso.txt");
		
		for (ActividadEntitie a : actividades) {
			
			try{
				actividadDbHelper.insertActividad(a);
			}catch(Exception e){
				Log.e("ParcelaController", "Error al insertar la actividad");
				
			}
			
		}
		
	}
*/
	private void InicializarCalles() {
		
		ArrayList<CalleEntitie> calles = ReadFile.LeerArchivoCalles	("/ArchivosCenso/callesCenso.txt");
		
		for (CalleEntitie c : calles) {
			
			try{
				calleDbHelper.insertCalle(c);
			}catch(Exception e){
				Log.e("ParcelaController", "Error al insertar la calle");
				
			}
			
		}
		
	}

	public boolean UpdateDatosParcela(DatosParcelaEntitie datos){
		
		long cantidad;
		
		try {
			cantidad = datosParcelaDbHelper.updateDatosParcela(datos,Constants.ACTION_UPDATE,this.usrActual);
		} catch (Exception e) {
			cantidad = -1;
			Log.e("ParcelaController","Error al actualizar los datos de la parcela");
		}
		
		return cantidad == 1;
		
	}
	
	public boolean EliminarDatosParcela(DatosParcelaEntitie datos){
		
		long cantidad;
		
		try {
			cantidad = datosParcelaDbHelper.updateDatosParcela(datos, Constants.ACTION_DELETE,this.usrActual);
		} catch (Exception e) {
			cantidad = -1;
			Log.e("ParcelaController", "Error al eliminar los datos de la parcela");
		}
		
		return cantidad == 1;
		
		
	}
	
	public boolean DeshacerEliminarDatosParcela(DatosParcelaEntitie datos){
		
		long cantidad;
		
		try {
			cantidad = datosParcelaDbHelper.updateDatosParcela(datos, Constants.NO_ACTION,this.usrActual);
		} catch (Exception e) {
			cantidad = -1;
			Log.e("ParcelaController", "Error al deshacer eliminar los datos de la parcela");
		}
		
		return cantidad == 1;
	}
	
	
	public Long AgregarParcela(DatosParcelaEntitie datos){
		
		long rownumParcela,idParcela,idDatosParcela = -1;
		
		try {

            idParcela = parcelasDbHelper.getNextIdParcela();

            if(idParcela != -1){

                datos.get_nomenclatura().set_id(idParcela);
                rownumParcela = parcelasDbHelper.insertParcela(datos.get_nomenclatura(),this.usrActual);

                if(rownumParcela != -1){

                    datos.set_nomenclatura(parcelasDbHelper.LoadParcelaById(idParcela,null));
                    idDatosParcela = datosParcelaDbHelper.insertDatosParcela(datos,this.usrActual);
                }
            }
			
		} catch (Exception e) {
			idParcela = -1;
			idDatosParcela = -1;
			Log.e("ParcelaController", "Error al agregar los datos de la parcela");
		}finally{
		}
		
		return idDatosParcela;
		
	}
	
	@SuppressWarnings("unused")
	private String ManzanaOFraccion(String manzFracc) {
		
		String res="";
		
		if(manzFracc.length() == 4)
		{
			if(manzFracc.substring(1, 2).equals("8"))
				res = "F";
			else
				res = "M";
		}
		
		return res;
	}

	public String enviarDatosParcelasWS(List<DatosParcelaEntitie> lista)
	{

        if(lista.size() == 0) {
            Toast.makeText(con,"No hay datos para enviar",Toast.LENGTH_LONG).show();
            return "0";
        }

		CallSoap cs = new CallSoap();
		String res = cs.EnviarDatosParcela(lista,this);

		return res;
	}

    public List<ActividadEntitie> getActividadesFromStrings(List<String> actividadesString) {

        List<ActividadEntitie> actividades = new ArrayList<ActividadEntitie>();

        for(String s : actividadesString){
            actividades.add(actividadDbHelper.LoadActividadByDesc(s));
        }

        return actividades;

    }

    public ParcelaEntitie CargarParcela(String circ, String secc, String manz) {

        ParcelaEntitie p = parcelasDbHelper.LoadParcelaEntitieByNomenclatura(circ,secc,manz);
        return p;

    }

    public List<String> CargarParcelas(String circunscripcion, String seccion, String manzana) {

        List<String> parcelas = parcelasDbHelper.LoadParcelasByNomenclatura(circunscripcion, seccion, manzana);
        return parcelas;

    }

    public boolean EliminarDatosParcelaPermanente(DatosParcelaEntitie dp) {

        boolean parcelaEliminada = false;
        boolean datosParcelaEliminados = false;

        //Primero elimino las relaciones con las actividades
        boolean relacionesActividadesEliminadas = actividadDbHelper.eliminarRelacionesDatos(dp.get_id());

        if(relacionesActividadesEliminadas)

            //Luego eliminar los datos de parcela.
            datosParcelaEliminados = datosParcelaDbHelper.eliminarDatosParcelaPermanente(dp.get_id());

        if(datosParcelaEliminados)
            parcelaEliminada = parcelasDbHelper.eliminarParcelaPermanente(dp.get_nomenclatura().get_id());

        return parcelaEliminada;

    }

    public Long GetNextIdDato() {

        Long id = datosParcelaDbHelper.getNextIdDato();

        return id;
    }

    public String GetNroTablet() {

        String nro = datosParcelaDbHelper.getNroTablet();

        return nro;
    }

    public boolean updateEnviado(DatosParcelaEntitie datosParcelaEntitie) {

        boolean exito = datosParcelaDbHelper.updateEnviado(datosParcelaEntitie);

        return exito;
    }

    public List<DatosParcelaEntitie> getDatosAGuardar() {

        List<DatosParcelaEntitie> datosParcelasModificadas = datosParcelaDbHelper.LoadDatosParcelaModificados();

        List<DatosParcelaEntitie> datosParcelasEliminadas = datosParcelaDbHelper.LoadDatosParcelaEliminados();

        List<DatosParcelaEntitie> datosParcelasInsertadas = datosParcelaDbHelper.LoadDatosParcelaInsertados();


        List<DatosParcelaEntitie> lista = new ArrayList<DatosParcelaEntitie>();
        lista.addAll(datosParcelasEliminadas);
        lista.addAll(datosParcelasModificadas);
        lista.addAll(datosParcelasInsertadas);

        return lista;

    }
}
