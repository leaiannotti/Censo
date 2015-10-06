package com.censoMain.constantes;

public class Constants {

	public static final String ENVIO_DATOS_OK = "0";
	
	/**
	 * Errores para login
	 */
	public static final int ERROR_USUARIO_NO_ENCONTRADO = 1;
	public static final int ERROR_PASSWORD_NO_COINCIDE = 2;
	public static final int ERROR_USUARIO_VACIO = 3;
	public static final int ERROR_PASSWORD_VACIO= 4;
	public static final int ERROR_OK = 0;
	
	
	/**
	 * Constantes de tipo de accion sobre los datos de parcela
	 */
	
	public static final String ACTION_INSERT = "I";
	public static final String ACTION_UPDATE = "U";
	public static final String ACTION_DELETE = "D";
    public static final String ACTION_DELETE_PERMANENTLY = "DP" ;
    public static final String ACTION_DELETE_UNDO = "DU";
	public static final String NO_ACTION = "N";


    /**
     * Constantes para las diferentes tipos de categorias
     */

    public static final String VIVIENDA_UNIFAMILIAR_ABV = "CAT_VUNI";
    public static final String VIVIENDA_MULTIFAMILIAR_ABV = "CAT_VMULTI";
    public static final String COMERCIO_ABV = "CAT_COM";
    public static final String INDUSTRIA_ABV = "CAT_IND";
    public static final String BALDIO_ABV = "CAT_BAL";
    public static final String OBRACONST_ABV = "CAT_OBCONST";

    public static final String TITLE_AGREGARPARCELA = "AGREGAR PARCELA";
    public static final String TITLE_MODIFICAR_DATOS = "MODIFICAR DATOS PARCELA";
    public static final String TITLE_ACTUALIZARGRILLA = "Actualizando datos...";
    public static final String TITLE_ADMINISTRARACTIVIDADES = "ADMINISTRAR ACTIVIDADES";

    public static final String ELEGIR_ACTIVIDADES = "ELEGIR ACTIVIDADES" ;


    public static final String ACTIVIDADES_TODAS = "ALLACTIVIDADES";
    public static final String ACTIVIDADES_SELECCIONADAS ="SELECTEDACTIVIDADES" ;
    public static final String ACTIVIDADES_CANTIDAD ="CANTIDADACT" ;
    public static final String OK = "OK" ;
    public static final String CANCEL = "CANCELAR" ;

    public static final String MESSAGE_NOPARCELAS = "NO HAY PARCELAS PARA MOSTRAR";


    public static final String ELIMINAR_DATOS = "Eliminar datos...";
}
