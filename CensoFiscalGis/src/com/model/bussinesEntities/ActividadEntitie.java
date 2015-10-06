package com.model.bussinesEntities;

import java.io.Serializable;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class ActividadEntitie implements KvmSerializable,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long _id;
    public String _codigoActividad;
    public String _descripcionActividad;
	
	/**
	 * Constructor default
	 */
	public ActividadEntitie(){}
	
	/**
	 * Constructor con parametros
	 * @param codigo
	 * @param desc
	 */
	public ActividadEntitie(String codigo, String desc){

		this._codigoActividad = codigo;
		this._descripcionActividad = desc;
	}
	
	
	/**
	 * @return the _codigoActividad
	 */
	public String get_codigoActividad() {
		return _codigoActividad;
	}
	/**
	 * @param _codigoActividad the _codigoActividad to set
	 */
	public void set_codigoActividad(String _codigoActividad) {
		this._codigoActividad = _codigoActividad;
	}
	/**
	 * @return the _descripcionActividad
	 */
	public String get_descripcionActividad() {
		return _descripcionActividad;
	}
	/**
	 * @param _descripcionActividad the _descripcionActividad to set
	 */
	public void set_descripcionActividad(String _descripcionActividad) {
		this._descripcionActividad = _descripcionActividad;
	}

	/**
	 * @return the _id
	 */
	public Long get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(Long _id) {
		this._id = _id;
	}
	
	@Override
	public boolean equals(Object o) {
		
		ActividadEntitie otraActividad = (ActividadEntitie) o;
		boolean res = (this.get_codigoActividad().trim().equals(otraActividad.get_codigoActividad().trim()) &&
				this.get_descripcionActividad().trim().equals(otraActividad.get_descripcionActividad().trim()));
		
		return res;
	}

    @Override
	public String toString() {
		return this.get_codigoActividad() + " - " + this.get_descripcionActividad();
	}

	public boolean esVacia() {
		return this.get_codigoActividad() == null || this.get_codigoActividad().isEmpty() || this.get_descripcionActividad() == null || this.get_descripcionActividad().isEmpty();
	}

	@Override
	public Object getProperty(int param) {
		
		Object o = null;
		
		switch (param) {
		case 0:
			o = _id;
			break;
		case 1:
			o = _codigoActividad;
			break;
		case 2:
			o = _descripcionActividad;
			break;
		}
		
		return o;
	}

	@Override
	public int getPropertyCount() {
		return 3;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int param, Hashtable arg1, PropertyInfo propInfo) {
		switch (param) {
		case 0:
			propInfo.type = PropertyInfo.LONG_CLASS;
			propInfo.name = "Id";
			break;
		case 1:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "CodActividad";
			break;
		case 2:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "DescActividad";
		}
	}

	@Override
	public void setProperty(int param, Object obj) {
		switch (param) {
		case 0:
			_id = (Long) obj;
			break;
		case 1:
			_codigoActividad = (String) obj;
			break;
		case 2:
			_descripcionActividad = (String) obj;
			break;
		}
		
	}
	
}
