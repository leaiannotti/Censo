package com.model.bussinesEntities.bases;

import com.model.bussinesEntities.UsuarioEntitie;

/**
 * Created by liannotti on 10/07/14.
 */
public class AuditoriaEntitie {

    private UsuarioEntitie _usrCreacion;
    private String _fechaCreacion;
    private UsuarioEntitie _usrModificacion;
    private String _fechaModificacion;
    private UsuarioEntitie _usrEliminacion;
    private String _fechaEliminacion;

    public AuditoriaEntitie() {
    }

    public UsuarioEntitie get_usrCreacion() {
        return _usrCreacion;
    }

    public void set_usrCreacion(UsuarioEntitie usrCreacion) {
        this._usrCreacion = usrCreacion;
    }

    public String get_fechaCreacion() {
        return _fechaCreacion;
    }

    public void set_fechaCreacion(String _fechaCreacion) {
        this._fechaCreacion = _fechaCreacion;
    }

    public String get_fechaModificacion() {
        return _fechaModificacion;
    }

    public void set_fechaModificacion(String _fechaModificacion) {
        this._fechaModificacion = _fechaModificacion;
    }

    public UsuarioEntitie get_usrModificacion() {
        return _usrModificacion;
    }

    public void set_usrModificacion(UsuarioEntitie _usrModificacion) {
        this._usrModificacion = _usrModificacion;
    }

    public UsuarioEntitie get_usrEliminacion() {
        return _usrEliminacion;
    }

    public void set_usrEliminacion(UsuarioEntitie _usrEliminacion) {
        this._usrEliminacion = _usrEliminacion;
    }

    public String get_fechaEliminacion() {
        return _fechaEliminacion;
    }

    public void set_fechaEliminacion(String _fechaEliminacion) {
        this._fechaEliminacion = _fechaEliminacion;
    }


}
