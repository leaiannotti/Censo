package com.model.bussinesEntities;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.model.bussinesEntities.bases.AuditoriaEntitie;

public class ParcelaEntitie extends AuditoriaEntitie implements KvmSerializable {

	private Long _id;
	private String _circunscripcion;
	private String _seccion;
	private String _tipoManz;
	private String _manzOFracc;
	private String _parcela;
    private String _idParcelaServer;

    private String _nroOrden;

	public ParcelaEntitie()
	{}
	
	public ParcelaEntitie(String circ,String secc,String manzana,String parcela)
	{
		this.set_id(0L);
		this.set_circunscripcion(circ);
		this.set_parcela(parcela);
		this.set_seccion(secc);
		this.set_manzOFracc(manzana);
		this.set_tipoManz("");
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
	/**
	 * @return the _circunscripcion
	 */
	public String get_circunscripcion() {
		return _circunscripcion;
	}
	/**
	 * @param _circunscripcion the _circunscripcion to set
	 */
	public void set_circunscripcion(String _circunscripcion) {
		this._circunscripcion = _circunscripcion;
	}
	/**
	 * @return the _seccion
	 */
	public String get_seccion() {
		return _seccion;
	}
	/**
	 * @param _seccion the _seccion to set
	 */
	public void set_seccion(String _seccion) {
		this._seccion = _seccion;
	}
	/**
	 * @return the _tipoManz
	 */
	public String get_tipoManz() {
		return _tipoManz;
	}
	/**
	 * @param _tipoManz the _tipoManz to set
	 */
	public void set_tipoManz(String _tipoManz) {
		this._tipoManz = _tipoManz;
	}
	/**
	 * @return the _manzOFracc
	 */
	public String get_manzOFracc() {
		return _manzOFracc;
	}
	/**
	 * @param _manzOFracc the _manzOFracc to set
	 */
	public void set_manzOFracc(String _manzOFracc) {
		this._manzOFracc = _manzOFracc;
	}
	/**
	 * @return the _parcela
	 */
	public String get_parcela() {
		return _parcela;
	}
	/**
	 * @param _parcela the _parcela to set
	 */
	public void set_parcela(String _parcela) {
		this._parcela = _parcela;
	}

    public String get_idParcelaServer() {
        return _idParcelaServer;
    }

    public void set_idParcelaServer(String _idParcelaServer) {
        this._idParcelaServer = _idParcelaServer;
    }

    public String get_nroOrden() {
        return _nroOrden;
    }

    public void set_nroOrden(String _nroOrden) {
        this._nroOrden = _nroOrden;
    }

    @Override
	public String toString() {
		
		String res = "Circ: " + this.get_circunscripcion() + " Secc: " + this.get_seccion() + 
				" Manz/Fracc: " + this.get_manzOFracc() + " Parc: ";

		return res;
	}

	//@Override
	public Object getProperty(int param) {
		/*private Long _id;
		private String _circunscripcion;
		private String _seccion;
		private String _tipoManz;
		private String _manzOFracc;
		private String _parcela;
		*/
		
		Object o = null;

		switch (param) {
		case 0:
			o = _id;
			break;
		case 1:
			o = _circunscripcion;
			break;
		case 2:
			o = _seccion;
			break;
		case 3:
			o = _tipoManz;
			break;
		case 4:
			o = _manzOFracc;
			break;
		case 5:
			o = _parcela;
			break;
        case 6:
            o = _idParcelaServer;
            break;
        case 7:
            o = _nroOrden;
            break;
			
		}

		return o;
		
	}

	//@Override
	public int getPropertyCount() {
		return 8;
	}

	//@Override
	public void getPropertyInfo(int param, Hashtable arg1, PropertyInfo propInfo) {
		switch (param) {
		case 0:
			propInfo.type = PropertyInfo.LONG_CLASS;
			propInfo.name = "Id";
			break;
		case 1:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "Circunscripcion";
			break;
		case 2:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "Seccion";
			break;
		case 3:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "TipoManzana";
			break;
		case 4:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "ManzanaFraccion";
			break;
		case 5:
			propInfo.type = PropertyInfo.STRING_CLASS;
			propInfo.name = "Parcela";
			break;
        case 6:
            propInfo.type = PropertyInfo.STRING_CLASS;
            propInfo.name = "IdParcelaServer";
            break;
        case 7:
            propInfo.type = PropertyInfo.STRING_CLASS;
            propInfo.name = "NroOrden";
            break;
        }
		
	}

	//@Override
	public void setProperty(int param, Object obj) {
		switch (param) {
		case 0:
			_id = (Long) obj;
			break;
		case 1:
			_circunscripcion = (String) obj;
			break;
		case 2:
			_seccion = (String) obj;
			break;
		case 3:
			_tipoManz = (String) obj;
			break;
		case 4:
			_manzOFracc = (String) obj;
			break;
		case 5:
			_parcela = (String) obj;
			break;
        case 6:
            _idParcelaServer = (String) obj;
            break;
        case 7:
            _nroOrden = (String) obj;
            break;
		}
	}
	
}
