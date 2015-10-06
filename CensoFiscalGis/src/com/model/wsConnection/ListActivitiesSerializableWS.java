package com.model.wsConnection;

import com.model.bussinesEntities.ActividadEntitie;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * Created by liannotti on 07/08/14.
 */
public class ListActivitiesSerializableWS extends Vector<ActividadEntitie> implements KvmSerializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListActivitiesSerializableWS(List<ActividadEntitie> acts){
        super(acts);
    }

    @Override
    public Object getProperty(int i) {
        return this.get(i);
    }

    @Override
    public int getPropertyCount() {
        return this.size();
    }

    @Override
    public void setProperty(int i, Object o) {
        SoapObject soapObject = (SoapObject) o;

        ActividadEntitie act = new ActividadEntitie();
        act.setProperty(0, soapObject.getProperty("_id"));
        act.setProperty(1, soapObject.getProperty("_codigoActividad"));
        act.setProperty(2, soapObject.getProperty("_descripcionActividad"));

        this.add(act);
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        propertyInfo.name = "ActividadEntitie";
        propertyInfo.type = new ActividadEntitie().getClass();
    }
}
