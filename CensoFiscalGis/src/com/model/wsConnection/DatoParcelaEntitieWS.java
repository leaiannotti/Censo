package com.model.wsConnection;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.model.bussinesEntities.CalleEntitie;
import com.model.bussinesEntities.DatosParcelaEntitie;
import com.model.bussinesEntities.ParcelaEntitie;

/**
 * Created by liannotti on 06/08/14.
 */
public class DatoParcelaEntitieWS implements KvmSerializable{

    private Long _id;
    private ParcelaEntitie _nomenclatura;
    private CalleEntitie _calle;
    private CalleEntitie _calleModificada;
    private String _altura;
    private String _alturaModificada;
    private String _categorias;
    private String _categoriasModificadas;
    private ListActivitiesSerializableWS _actividades;
    private ListActivitiesSerializableWS _actividadesModificadas;
    private String _observaciones;
    private String _estado;
    private String user_alta;
    private String user_modif;
    private String user_baja;
    private String fecha_alta;
    private String fecha_modif;
    private String fecha_baja;

    private String latitud;
    private String longitud;

    private String nroTablet;

    public DatoParcelaEntitieWS() {
    }


    public DatoParcelaEntitieWS(DatosParcelaEntitie dp){
        _id = dp.get_id();
        _nomenclatura = dp.get_nomenclatura();
        _calle = dp.get_calle();
        _calleModificada = dp.get_calleModificada();
        _altura = dp.get_altura();
        _alturaModificada = dp.get_alturaModificada();
        _categorias = DicToString(dp.get_categorias());
        _categoriasModificadas = DicToString(dp.get_categoriasModificadas());
        _actividades = new ListActivitiesSerializableWS(dp.get_actividades());
        _actividadesModificadas = new ListActivitiesSerializableWS(dp.get_actividadesModificadas());
        _observaciones = dp.get_observaciones();
        _estado = dp.get_estado();
        dp.get_pathImagen();
        user_alta = dp.get_usrCreacion() == null ? "" : dp.get_usrCreacion().get_id().toString();
        fecha_alta = dp.get_fechaCreacion() == null ? "" : dp.get_fechaCreacion().toString();
        user_modif = dp.get_usrModificacion() == null ? "" : dp.get_usrModificacion().get_id().toString();
        fecha_modif = dp.get_fechaModificacion() == null ? "" : dp.get_fechaModificacion().toString();
        user_baja = dp.get_usrEliminacion() == null ? "" : dp.get_usrEliminacion().get_id().toString();
        fecha_baja = dp.get_fechaEliminacion() == null ? "" : dp.get_fechaEliminacion().toString();
        longitud = dp.get_longitud();
        latitud = dp.get_latitud();
        nroTablet = dp.get_deviceNumber();
    }

    public String getUser_alta() {
        return user_alta;
    }

    public void setUser_alta(String user_alta) {
        this.user_alta = user_alta;
    }

    public String getUser_modif() {
        return user_modif;
    }

    public void setUser_modif(String user_modif) {
        this.user_modif = user_modif;
    }

    public String getUser_baja() {
        return user_baja;
    }

    public void setUser_baja(String user_baja) {
        this.user_baja = user_baja;
    }

    public String getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(String fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getFecha_modif() {
        return fecha_modif;
    }

    public void setFecha_modif(String fecha_modif) {
        this.fecha_modif = fecha_modif;
    }

    public String getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(String fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNroTablet() {
        return nroTablet;
    }

    public void setNroTablet(String nroTablet) {
        this.nroTablet = nroTablet;
    }

    private String DicToString(HashMap<String,Integer> categorias) {

        String categs = "";
        Set<String> setKeys = categorias.keySet();

        for (String k : setKeys){
            Integer j = categorias.get(k);
            categs += j.toString() + "-";
            //categs.add(j.toString());
        }

        return categs;
    }


    @Override
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
            case 12:
                object = user_alta;
                break;
            case 13:
                object = user_modif;
                break;
            case 14:
                object = user_baja;
                break;
            case 15:
                object = fecha_alta;
                break;
            case 16:
                object = fecha_modif;
                break;
            case 17:
                object = fecha_baja;
                break;
            case 18:
                object = longitud;
                break;
            case 19:
                object = latitud;
                break;
            case 20:
                object = nroTablet;
                break;
        }
        return object;

    }

    @Override
    public int getPropertyCount() {
        return 21;
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
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "Categorias";
                break;
            case 6:
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "CategoriasModificadas";
                break;
            case 7:
                propInfo.type = PropertyInfo.OBJECT_CLASS;
                propInfo.name = "Actividades";
                break;
            case 8:
                propInfo.type = PropertyInfo.OBJECT_CLASS;
                propInfo.name = "ActividadesModificadas";
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
            case 12:
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "UsrAlta";
                break;
            case 13:
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "UsrModif";
                break;
            case 14:
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "UsrBaja";
                break;
            case 15:
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "FechaAlta";
                break;
            case 16:
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "FechaModif";
                break;
            case 17:
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "FechaBaja";
                break;
            case 18:
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "Longitud";
                break;
            case 19:
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "Latitud";
                break;
            case 20:
                propInfo.type = PropertyInfo.STRING_CLASS;
                propInfo.name = "NroTablet";
                break;
        }

    }

    @Override
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
                _categorias = (String) obj;
                break;
            case 6:
                _categoriasModificadas = (String) obj;
                break;
            case 7:
                _actividades = (ListActivitiesSerializableWS) obj;
                break;
            case 8:
                _actividadesModificadas = (ListActivitiesSerializableWS) obj;
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
            case 12:
                user_alta = (String) obj;
                break;
            case 13:
                user_modif = (String) obj;
                break;
            case 14:
                user_baja = (String) obj;
                break;
            case 15:
                fecha_alta = (String) obj;
                break;
            case 16:
                fecha_modif = (String) obj;
                break;
            case 17:
                fecha_baja = (String) obj;
                break;
            case 18:
                longitud = (String) obj;
                break;
            case 19:
                latitud = (String) obj;
                break;
            case 20:
                nroTablet = (String) obj;
                break;
            }

        }

}


