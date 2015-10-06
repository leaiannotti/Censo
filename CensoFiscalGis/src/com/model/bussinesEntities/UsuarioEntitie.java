package com.model.bussinesEntities;

public class UsuarioEntitie {

	private Long _id;
	private String _usuario;
	private String _password;
	private String _nombre;
	private String _apellido;
	private String _email;
	private String _fechaRegistro;
	private String _idRemoto;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public String get_usuario() {
		return _usuario;
	}
	public void set_usuario(String _usuario) {
		this._usuario = _usuario;
	}
	public String get_password() {
		return _password;
	}
	public void set_password(String _password) {
		this._password = _password;
	}
	public String get_nombre() {
		return _nombre;
	}
	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}
	public String get_apellido() {
		return _apellido;
	}
	public void set_apellido(String _apellido) {
		this._apellido = _apellido;
	}
	public String get_email() {
		return _email;
	}
	public void set_email(String _email) {
		this._email = _email;
	}
	public String get_fechaRegistro() {
		return _fechaRegistro;
	}
	public void set_fechaRegistro(String _fechaRegistro) {
		this._fechaRegistro = _fechaRegistro;
	}
	/**
	 * @return the _idRemoto
	 */
	public String get_idRemoto() {
		return _idRemoto;
	}
	/**
	 * @param _idRemoto the _idRemoto to set
	 */
	public void set_idRemoto(String _idRemoto) {
		this._idRemoto = _idRemoto;
	}
	
}
