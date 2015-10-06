package com.model.bussinesEntities;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class CalleEntitie implements KvmSerializable {

	private Long _id;
	private String _codigoCalle;
	private String _calle;

	/**
	 * Constructor por defecto
	 */
	public CalleEntitie() {
	}

	/**
	 * Constructor con parametros
	 * 
	 * @param codigo
	 * @param calle
	 */
	public CalleEntitie(String codigo, String calle) {

        this._id = Long.parseLong(codigo);
		this._calle = calle;
		this._codigoCalle = codigo;
	}

	/**
	 * @return the _codigoCalle
	 */
	public String get_codigoCalle() {
		return _codigoCalle;
	}

	/**
	 * @param _codigoCalle
	 *            the _codigoCalle to set
	 */
	public void set_codigoCalle(String _codigoCalle) {
		this._codigoCalle = _codigoCalle;
	}

	/**
	 * @return the _calle
	 */
	public String get_calle() {
		return _calle;
	}

	/**
	 * @param _calle
	 *            the _calle to set
	 */
	public void set_calle(String _calle) {
		this._calle = _calle;
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

	@Override
	public boolean equals(Object o) {
		CalleEntitie otraCalle = (CalleEntitie) o;
		boolean res = (this.get_codigoCalle().trim().equals(otraCalle
				.get_codigoCalle().trim()));

		return res;
	}

    @Override
	public String toString() {
		return this.get_calle();
	}

	public boolean esVacia() {

		return this.get_calle() == null || this.get_calle().isEmpty()
				|| this.get_codigoCalle() == null
				|| this.get_codigoCalle().isEmpty();
	}

	@Override
	public Object getProperty(int param) {
		Object o = null;

		switch (param) {
		case 0:
			o = _id;
			break;
		case 1:
			o = _codigoCalle;
			break;
		case 2:
			o = _calle;
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
			propInfo.name = "CodCalle";
			break;
		case 2:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "DescCalle";
		}

	}

	@Override
	public void setProperty(int param, Object obj) {
		switch (param) {
		case 0:
			_id = (Long) obj;
			break;
		case 1:
			_codigoCalle = (String) obj;
			break;
		case 2:
			_calle = (String) obj;
			break;
		}

	}


    public boolean isNull() {
        return this._calle == null && this._codigoCalle == null && this._id == null;
    }
}
