package com.model.bussinesEntities;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class CategoriaEntitie implements KvmSerializable {

	private Long _id;
	private String _codCategoria;
	private String _descCategoria;
	
	/**
	 * Constructor por defecto
	 */
	public CategoriaEntitie(){}
	
	/**
	 * Constructor con parametros
	 * @param codigo
	 * @param desc
	 */
	public CategoriaEntitie(String codigo,String desc){

		this._codCategoria = codigo;
		this._descCategoria = desc;
	}

	/**
	 * @return the _codCategoria
	 */
	public String get_codCategoria() {
		return _codCategoria;
	}

	/**
	 * @param _codCategoria the _codCategoria to set
	 */
	public void set_codCategoria(String _codCategoria) {
		this._codCategoria = _codCategoria;
	}

	/**
	 * @return the _descCategoria
	 */
	public String get_descCategoria() {
		return _descCategoria;
	}

	/**
	 * @param _descCategoria the _descCategoria to set
	 */
	public void set_descCategoria(String _descCategoria) {
		this._descCategoria = _descCategoria;
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
		CategoriaEntitie otraCategoria = (CategoriaEntitie) o;
		boolean res = (this.get_codCategoria().trim().equals(otraCategoria.get_codCategoria().trim()) &&
				this.get_descCategoria().trim().equals(otraCategoria.get_descCategoria().trim()));
		
		return res;
	}
	
	
	@Override
	public String toString() {
		return this.get_codCategoria() + "-" + this.get_descCategoria();
	}

	public boolean esVacia() {
		return this.get_codCategoria() == null || this.get_codCategoria().isEmpty() || this.get_descCategoria() == null || this.get_descCategoria().isEmpty();
	}

	@Override
	public Object getProperty(int param) {
		Object o = null;

		switch (param) {
		case 0:
			o = _id;
			break;
		case 1:
			o = _codCategoria;
			break;
		case 2:
			o = _descCategoria;
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
			propInfo.name = "CodCategoria";
			break;
		case 2:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "DescCategoria";
		}

	}

	@Override
	public void setProperty(int param, Object obj) {
		switch (param) {
		case 0:
			_id = (Long) obj;
			break;
		case 1:
			_codCategoria = (String) obj;
			break;
		case 2:
			_descCategoria = (String) obj;
			break;
		}

	}
}
