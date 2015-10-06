package com.model.bussinesEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.censoMain.constantes.Constants;
import com.model.bussinesEntities.bases.AuditoriaEntitie;

public class DatosParcelaEntitie extends AuditoriaEntitie implements KvmSerializable {

	private Long _id;
    private ParcelaEntitie _nomenclatura;
	private CalleEntitie _calle;
	private CalleEntitie _calleModificada;
	private String _altura;
	private String _alturaModificada;
	private HashMap<String,Integer> _categorias;
	private HashMap<String,Integer> _categoriasModificadas;
	private List<ActividadEntitie> _actividades;
	private List<ActividadEntitie> _actividadesModificadas;
	private String _observaciones;
	private String _estado;
    private String _enviado;
	private String _pathImagen;

    private String _latitud;
    private String _longitud;

    private String _deviceNumber;

    public String get_deviceNumber() {
        return _deviceNumber;
    }

    public void set_deviceNumber(String _deviceNumber) {
        this._deviceNumber = _deviceNumber;
    }

    public String get_latitud() {
        return _latitud;
    }

    public void set_latitud(String _latitud) {
        this._latitud = _latitud;
    }

    public String get_longitud() {
        return _longitud;
    }

    public void set_longitud(String _longitud) {
        this._longitud = _longitud;
    }

    public String get_enviado() {
        return _enviado;
    }

    public void set_enviado(String _enviado) {
        this._enviado = _enviado;
    }

    public DatosParcelaEntitie() {
        this.set_id(0L);
        this.set_nomenclatura(new ParcelaEntitie());
        this.set_calle(new CalleEntitie());
        this.set_calleModificada(new CalleEntitie());
        this.set_altura("");
        this.set_alturaModificada("");
        this.set_categorias(new HashMap<String, Integer>());
        this.set_categoriasModificadas(new HashMap<String, Integer>());
        this.set_actividades(new ArrayList<ActividadEntitie>());
        this.set_actividadesModificadas(new ArrayList<ActividadEntitie>());
        this.set_observaciones("");
        this.set_estado(Constants.NO_ACTION);
        this.set_pathImagen("");
        this.set_enviado("0");
	}

	/**
	 * 
	 * @param parcela
	 * @param calle
	 * @param categorias
	 * @param actividades
	 * @param observaciones
	 */
	public DatosParcelaEntitie(ParcelaEntitie parcela, CalleEntitie calle,
			String altura, HashMap<String,Integer> categorias,
			List<ActividadEntitie> actividades, String observaciones, String estado) {
		this.set_nomenclatura(parcela);
		this.set_altura(altura);
		this.set_calle(calle);
		this.set_categorias(categorias);
		this.set_actividades(actividades);
		this.set_observaciones(observaciones);
		this.set_estado(estado);

	}

	/**
	 * 
	 * @param parcela
	 * @param calle
	 * @param categorias
	 * @param actividades
	 * @param observaciones
	 */
	public DatosParcelaEntitie(ParcelaEntitie parcela, CalleEntitie calle,
			CalleEntitie calleModif, HashMap<String,Integer> categorias,
            HashMap<String,Integer> categModif, String altura, String alturaModif,
			List<ActividadEntitie> actividades, List<ActividadEntitie> actividadesModif,
			String observaciones) {
		this.set_nomenclatura(parcela);
		this.set_calle(calle);
		this.set_categorias(categorias);
		this.set_actividades(actividades);
		this.set_altura(altura);
		this.set_calleModificada(calleModif);
		this.set_actividadesModificadas(actividadesModif);
		this.set_categoriasModificadas(categModif);
		this.set_alturaModificada(alturaModif);
		this.set_observaciones(observaciones);

	}

	/**
	 * @return the _id
	 */
	public Long get_id() {
		return _id;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void set_id(Long _id) {
		this._id = _id;
	}


	/**
	 * @return the _calle
	 */
	public CalleEntitie get_calle() {
		return _calle;
	}

	/**
	 * @param _calle
	 *            the _calle to set
	 */
	public void set_calle(CalleEntitie _calle) {
		this._calle = _calle;
	}

	/**
	 * @return the _categorias
	 */
	public HashMap<String,Integer> get_categorias() {
		return _categorias;
	}

	/**
	 * @param _categorias
	 *            the _categorias to set
	 */
	public void set_categorias(HashMap<String,Integer> _categorias) {
		this._categorias = _categorias;
	}

	/**
	 * @return the _actividades
	 */
	public List<ActividadEntitie> get_actividades() {
		return _actividades;
	}

	/**
	 * @param _actividades
	 *            the _actividades to set
	 */
	public void set_actividades(List<ActividadEntitie> _actividades) {
		this._actividades = _actividades;
	}

	/**
	 * @return the _nomenclatura
	 */
	public ParcelaEntitie get_nomenclatura() {
		return _nomenclatura;
	}

	/**
	 * @param _nomenclatura
	 *            the _nomenclatura to set
	 */
	public void set_nomenclatura(ParcelaEntitie _nomenclatura) {
		this._nomenclatura = _nomenclatura;
	}

	/**
	 * @return the _altura
	 */
	public String get_altura() {
		return _altura;
	}

	/**
	 * @param _altura
	 *            the _altura to set
	 */
	public void set_altura(String _altura) {
		this._altura = _altura;
	}

	/**
	 * @return the _observaciones
	 */
	public String get_observaciones() {
		return _observaciones;
	}

	/**
	 * @param _observaciones
	 *            the _observaciones to set
	 */
	public void set_observaciones(String _observaciones) {
		this._observaciones = _observaciones;
	}

	/**
	 * @return the _calleModificada
	 */
	public CalleEntitie get_calleModificada() {
		return _calleModificada;
	}

	/**
	 * @param _calleModificada
	 *            the _calleModificada to set
	 */
	public void set_calleModificada(CalleEntitie _calleModificada) {
		this._calleModificada = _calleModificada;
	}

	/**
	 * @return the _alturaModificada
	 */
	public String get_alturaModificada() {
		return _alturaModificada;
	}

	/**
	 * @param _alturaModificada
	 *            the _alturaModificada to set
	 */
	public void set_alturaModificada(String _alturaModificada) {
		this._alturaModificada = _alturaModificada;
	}

	/**
	 * @return the _categoriasModificadas
	 */
	public HashMap<String,Integer> get_categoriasModificadas() {
		return _categoriasModificadas;
	}

	/**
	 * @param _categoriasModificadas
	 *            the _categoriaModificada to set
	 */
	public void set_categoriasModificadas(HashMap<String,Integer> _categoriasModificadas) {
		this._categoriasModificadas = _categoriasModificadas;
	}

	/**
	 * @return the _actividadesModificadas
	 */
	public List<ActividadEntitie> get_actividadesModificadas() {
		return _actividadesModificadas;
	}

	/**
	 * @param _actividadesModificadas
	 *            the _actividadesModificadas to set
	 */
	public void set_actividadesModificadas(List<ActividadEntitie> _actividadesModificadas) {
		this._actividadesModificadas = _actividadesModificadas;
	}

	public String get_estado() {
		return _estado;
	}

	public void set_estado(String _estado) {
		this._estado = _estado;
	}

	//@Override
	public Object getProperty(int param) {

		Object object = null;
		switch (param) {

		case 0:
			object = _id;
			break;
		case 1:
			object = _calle;
			break;
		case 2:
			object = _calleModificada;
			break;
		case 3:
			object = _altura;
			break;
		case 4:
			object = _alturaModificada;
			break;
		case 5:
			object = _categorias;
			break;
		case 6:
			object = _categoriasModificadas;
			break;
		case 7:
			object = _actividades;
			break;
		case 8:
			object = _actividadesModificadas;
			break;
		case 9:
			object = _observaciones;
			break;
		case 10:
			object = _estado;
			break;
		case 11:
			object = _nomenclatura;
			break;

		}
		return object;

	}

	//@Override
	public int getPropertyCount() {
		return 12;
	}

	@SuppressWarnings("rawtypes")
	//@Override
	public void getPropertyInfo(int param, Hashtable arg1, PropertyInfo propInfo) {

        switch (param) {
		case 0:
			propInfo.type = PropertyInfo.LONG_CLASS;
			propInfo.name = "Id";
			break;
		case 1:
			propInfo.type = PropertyInfo.OBJECT_TYPE;
			propInfo.name = "Calle";
			break;
		case 2:
			propInfo.type = PropertyInfo.OBJECT_CLASS;
			propInfo.name = "CalleModificada";
			break;
		case 3:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "Altura";
			break;
		case 4:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "AlturaModificada";
			break;
		case 5:
			propInfo.type = PropertyInfo.OBJECT_CLASS;
			propInfo.name = "Categoria";
			break;
		case 6:
			propInfo.type = PropertyInfo.OBJECT_CLASS;
			propInfo.name = "CategoriaModificada";
			break;
		case 7:
			propInfo.type = PropertyInfo.OBJECT_CLASS;
			propInfo.name = "Actividad";
			break;
		case 8:
			propInfo.type = PropertyInfo.OBJECT_CLASS;
			propInfo.name = "ActividadModificada";
			break;
		case 9:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "Observaciones";
			break;
		case 10:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "Estado";
			break;
		case 11:
			propInfo.type = PropertyInfo.OBJECT_CLASS;
			propInfo.name = "Nomenclatura";
			break;
		}

	}

	//@Override
	@SuppressWarnings("unchecked")
	public void setProperty(int param, Object obj) {

		switch (param) {
		case 0:
			_id = (Long) obj;
			break;
		case 1:
			_calle = (CalleEntitie) obj;
			break;
		case 2:
			_calleModificada = (CalleEntitie) obj;
			break;
		case 3:
			_altura = (String) obj;
			break;
		case 4:
			_alturaModificada = (String) obj;
			break;
		case 5:
			_categorias = (HashMap<String,Integer>) obj;
			break;
		case 6:
			_categoriasModificadas = (HashMap<String,Integer>) obj;
			break;
		case 7:
			_actividades = (List<ActividadEntitie>) obj;
			break;
		case 8:
			_actividadesModificadas = (List<ActividadEntitie>) obj;
			break;
		case 9:
			_observaciones = (String) obj;
			break;
		case 10:
			_estado = (String) obj;
			break;
		case 11:
			_nomenclatura = (ParcelaEntitie) obj;
			break;
		}

	}

	public String get_pathImagen() {
		return _pathImagen;
	}

	public void set_pathImagen(String _pathImagen) {
		this._pathImagen = _pathImagen;
	}

	
	
	/**
	 * Vista para el datasource
	 */

    public String get_categoriasAmostrar(){

        String res = "";
        Set<String> e = get_categorias().keySet();
        Iterator<String> it = e.iterator();
        while (it.hasNext()){
            String next = it.next();
            String k = getDescripcionCategoria(next);
            Integer cant = get_categorias().get(next);

            if(cant != 0)
                res += k + " | ";

        }
        return res;
    }

    public String get_categoriasModificadasAmostrar(){

        String res = "";
        Set<String> e = get_categorias().keySet();
        Iterator<String> it = e.iterator();
        while (it.hasNext()){
            String next = it.next();
            String k = getDescripcionCategoria(next);
            Integer cant = get_categoriasModificadas().get(next);
            if(cant != 0)
                res += k + " | ";
        }
        return res;
    }

    private String getDescripcionCategoria(String cat) {

        if(cat.equals(Constants.VIVIENDA_MULTIFAMILIAR_ABV)) return "Multifamiliar";
        else if(cat.equals(Constants.VIVIENDA_UNIFAMILIAR_ABV)) return "Unifamiliar";
        else if(cat.equals(Constants.COMERCIO_ABV)) return "Comercio";
        else if(cat.equals(Constants.INDUSTRIA_ABV)) return "Industria";
        else if(cat.equals(Constants.BALDIO_ABV)) return "Baldio";
        else if(cat.equals(Constants.OBRACONST_ABV)) return "Obra";
        else return "";
    }

    public String getActividadAMostrar(){

        String res ="";

        if(get_actividades() != null){
            if(!get_actividades().isEmpty()){
                //Devuelvo la primer actividad q veo
                res = get_actividades().get(0).get_descripcionActividad();
            }
        }

        //parche rapido, nunca sucede, salvo con las pruebas anteriores
        if(res == null) res = "";
        return  res;
    }

    public String getActividadModificadaAMostrar(){

        String res ="";

        if(get_actividadesModificadas() != null){
            if(!get_actividadesModificadas().isEmpty()){
                //Devuelvo la primer actividad q veo
                res = get_actividadesModificadas().get(0).get_descripcionActividad();
            }
        }


        //parche rapido, nunca sucede, salvo con las pruebas anteriores
        if(res == null) res = "";
        return  res;
    }

}
