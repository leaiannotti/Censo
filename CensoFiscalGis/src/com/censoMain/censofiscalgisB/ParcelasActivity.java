package com.censoMain.censofiscalgisB;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.censoMain.buttonListeners.AddParcelaButtonListener;
import com.censoMain.buttonListeners.DeleteGridListener;
import com.censoMain.buttonListeners.UpdateGridListener;
import com.censoMain.constantes.Constants;
import com.censoMain.customControlls.CustomDataTable;
import com.censoMain.customControlls.CustomDataTable.DataRow;
import com.censoMain.customControlls.CustomDatagrid;
import com.controller.ActivityControllers.ParcelaController;
import com.model.bussinesEntities.ActividadEntitie;
import com.model.bussinesEntities.CalleEntitie;
import com.model.bussinesEntities.DatosParcelaEntitie;

import java.util.ArrayList;
import java.util.List;

public class ParcelasActivity extends Activity {

    final Context con = this;
	private static Dialog pDialog;
    private ProgressDialog pDialogGuardado;
    private Long userId;

    private SendDatosParcelaAsync miTareaAsync;

    final int ENVIO_ACTUALIZACION = 1;
	final int RECIBIR_ACTUALIZACION = 2;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    // Declaro las listas fijas de la app...
	private List<String> circunscripciones;
	private List<ActividadEntitie> actividades;
	private List<CalleEntitie> calles;
	
	private CalleEntitie callePreSeleccionada;
	
    //Declaro los controles de la actividad
    private Spinner circunsSp;
    private Spinner seccionSp;
    private Spinner manzSp;

    private String _circunscripcion;
    private String _seccion;
    private String _manzana;

    private static CustomDatagrid gridParcelas;
	private Button addParcelaButton;
    private static CustomDataTable dtDataSource = new CustomDataTable();

    // Controlador (Este hace to do el laburo)
	private static ParcelaController controller;


    public String path_imagen = null;

    private LocationManager locManager;
    private LocationListener locListener;


    private String latitudActual;
    private String longitudActual;


    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<String> getCircunscripciones() {
        return circunscripciones;
    }
    public List<ActividadEntitie> getActividades() {
        return actividades;
    }
    public List<CalleEntitie> getCalles() {
        return calles;
    }
    public Spinner getCircunsSp() {
        return circunsSp;
    }
    public Spinner getSeccionSp() {
        return seccionSp;
    }
    public Spinner getManzSp() {
        return manzSp;
    }
    public CustomDataTable getDtDataSource() {
        return dtDataSource;
    }
    public String getPath_imagen() {
        return path_imagen;
    }
    public void setPath_imagen(String path_imagen) {
        this.path_imagen = path_imagen;
    }
    public ParcelaController getController() {
        return controller;
    }
    public CustomDatagrid getGridParcelas() {
        return gridParcelas;
    }
    public Button getAddParcelaButton() {
        return addParcelaButton;
    }
    public String get_circunscripcion() {
        return _circunscripcion;
    }
    public void set_circunscripcion(String _circunscripcion) {
        this._circunscripcion = _circunscripcion;
    }
    public String get_seccion() {
        return _seccion;
    }
    public void set_seccion(String _seccion) {
        this._seccion = _seccion;
    }
    public String get_manzana() {
        return _manzana;
    }
    public void set_manzana(String _manzana) {
        this._manzana = _manzana;
    }
    public String getLatitudActual() {
        return latitudActual;
    }
    public void setLatitudActual(String latitudActual) {
        this.latitudActual = latitudActual;
    }
    public String getLongitudActual() {
        return longitudActual;
    }
    public void setLongitudActual(String longitudActual) {
        this.longitudActual = longitudActual;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parcelas);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
        //setUserId(savedInstanceState.getLong("UsrId"));
        //TODO:DESELECCIONAR LA PARTE DE ARRIBA PARA  TOMAR EL ID DE LA PANTALLA DE LOGIN
        setUserId(1L);

        //Creo el controlador.
		controller = new ParcelaController(con,getUserId());

		circunsSp = (Spinner) findViewById(R.id.circSpinner);
		seccionSp = (Spinner) findViewById(R.id.seccSpinner);
		manzSp = (Spinner) findViewById(R.id.manzSpinner);

		//Creo el dialog de espera mientras se carga la grilla
		pDialog = new ProgressDialog(con);
		pDialog.setTitle(Constants.TITLE_ACTUALIZARGRILLA);
		pDialog.setCancelable(false);

		// Carga de datos
		circunscripciones = controller.CargarCircunscripciones();
		actividades = controller.CargarActividades();
		calles = controller.CargarCalles();
		//

        //Boton agregarParcela
        addParcelaButton = (Button) findViewById(R.id.agregarParcela);
        addParcelaButton.setOnClickListener(new AddParcelaButtonListener(ParcelasActivity.this));

		// Preparo la grilla
		gridParcelas = (CustomDatagrid) findViewById(R.id.grid);

		gridParcelas.setNoDataText(Constants.MESSAGE_NOPARCELAS);
		gridParcelas.addColumnStyle(new CustomDatagrid.ColumnStyle("Parcela","parcela", 70));
		gridParcelas.addColumnStyle(new CustomDatagrid.ColumnStyle("Calle","calle", 140));
		gridParcelas.addColumnStyle(new CustomDatagrid.ColumnStyle("Altura","altura", 100));
		gridParcelas.addColumnStyle(new CustomDatagrid.ColumnStyle("Categoria","categoria", 150));
		gridParcelas.addColumnStyle(new CustomDatagrid.ColumnStyle("Actividad","actividad", 150));
		gridParcelas.addColumnStyle(new CustomDatagrid.ColumnStyle("Calle modificada", "calleModif", 140));
		gridParcelas.addColumnStyle(new CustomDatagrid.ColumnStyle("Altura modificada", "alturaModif", 100));
		gridParcelas.addColumnStyle(new CustomDatagrid.ColumnStyle("Categoria modificada", "categoriaModif", 150));
		gridParcelas.addColumnStyle(new CustomDatagrid.ColumnStyle("Actividad modificada", "actividadModif", 150));
		gridParcelas.addColumnStyle(new CustomDatagrid.ColumnStyle("Observaciones", "observaciones", 200));
		gridParcelas.setDataSource(dtDataSource);
		gridParcelas.refresh();

		gridParcelas.setLvOnItemClickListener(new UpdateGridListener(ParcelasActivity.this));
		gridParcelas.setLvOnItemLongClickListener(new DeleteGridListener(ParcelasActivity.this));

		//Seteo y le doy accion al spinner de la circunscripcion
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spinner_layout,
				circunscripciones);
		circunsSp.setAdapter(adapter);

		circunsSp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {

                set_circunscripcion((String) circunsSp
                        .getSelectedItem());

				List<String> secciones = controller
						.CargarSeccionesPorCircunscripcion(get_circunscripcion());

				ArrayAdapter<String> adapterSecciones = new ArrayAdapter<String>(
						getApplicationContext(),
						R.layout.spinner_layout, secciones);

				seccionSp.setAdapter(adapterSecciones);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		
		//Seteo y le doy accion al spinner de la seccion
		seccionSp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {

                set_seccion((String) seccionSp.getSelectedItem());

				List<String> manzanas = controller.CargarManzanasPorSeccion(
						get_circunscripcion(),
						get_seccion());

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getApplicationContext(),
						R.layout.spinner_layout, manzanas);
				manzSp.setAdapter(adapter);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		//Seteo y le doy accion al spinner de la manzana o fraccion
		manzSp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {

                set_manzana(manzSp.getSelectedItem().toString());
				RefreshGrid(get_circunscripcion(), get_seccion(), get_manzana());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		//Arranco con el rastreo de la localizacion, esto se hace para saber en que posicion esta el 
		//fiscalizador en el momento de censar la parcela (medio buchon)
        comenzarLocalizacion();
    }

    //Refresco de grilla cuando se cambia algun spinner
	public static void RefreshGrid(String circ, String secc, String manzana) {
		new RefreshGridAsynTask().execute(circ, secc, manzana,null,null);
	}

    public static void RefreshGrid(String circ, String secc, String manzana,String datoParcelaId,String action) {
        new RefreshGridAsynTask().execute(circ, secc, manzana,datoParcelaId,action);
    }


    //Tomando el evento al sacar una foto con el boton "TOMAR FOTO"
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Uri photoUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(photoUri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                path_imagen = filePath;
            }
        }
    }

    //Tarea asincrona que refresca la grilla segun lo que se haya hecho (INSERT,UPDATE,DELETE)
	public static class RefreshGridAsynTask extends AsyncTask<String, Integer, Void> {
		@Override
		protected void onPreExecute() {
			pDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {

            if(params[3] != null){

                String accion = params[4];
                DatosParcelaEntitie datosParcelaEntitie = controller.CargarDatosParcelasById(params[3]);

                if(!accion.equals(Constants.ACTION_INSERT))
                {
                    if(accion.equals(Constants.ACTION_UPDATE) || accion.equals(Constants.ACTION_DELETE)){

                        for(DataRow dr : dtDataSource.getAllRows()){
                            if(((DatosParcelaEntitie) dr.getTag()).get_id().toString().equals(datosParcelaEntitie.get_id().toString())){
                                //TODO Encapsular sets en nuevo metodo.
                            	dr.setEstado(accion);
                                dr.setTag(datosParcelaEntitie);
                                dr.set("parcela", datosParcelaEntitie.get_nomenclatura().get_parcela(), accion);
                                dr.set("calle", datosParcelaEntitie.get_calle().get_calle(), accion);
                                dr.set("calleModif", (datosParcelaEntitie.get_calleModificada() != null && datosParcelaEntitie.get_calleModificada().equals(datosParcelaEntitie.get_calle())) ? "" : datosParcelaEntitie.get_calleModificada().get_calle(), accion);
                                dr.set("altura", datosParcelaEntitie.get_altura(), accion);
                                dr.set("alturaModif",(datosParcelaEntitie.get_alturaModificada() != null && datosParcelaEntitie.get_alturaModificada().equals(datosParcelaEntitie.get_altura())) ? "" : datosParcelaEntitie.get_alturaModificada(),accion);
                                String catsAMostrar = datosParcelaEntitie.get_categoriasAmostrar();
                                dr.set("categoria", catsAMostrar, accion);
                                dr.set("categoriaModif", datosParcelaEntitie.get_categoriasModificadasAmostrar().equals(catsAMostrar) ? "" : datosParcelaEntitie.get_categoriasModificadasAmostrar(),accion);
                                dr.set("actividad", datosParcelaEntitie.getActividadAMostrar(), accion);
                                dr.set("actividadModif", datosParcelaEntitie.getActividadModificadaAMostrar().equals(datosParcelaEntitie.getActividadAMostrar()) ? " " : datosParcelaEntitie.getActividadModificadaAMostrar(),accion);
                                dr.set("observaciones",datosParcelaEntitie.get_observaciones(),accion);
                                break;
                            }
                        }
                    }else if(accion.equals(Constants.ACTION_DELETE_PERMANENTLY)){
                        for(DataRow dr : dtDataSource.getAllRows()){
                            if(((DatosParcelaEntitie) dr.getTag()).get_id().toString().equals(params[3])){
                                dtDataSource.remove(dr);
                                break;
                            }
                        }
                    }else if(accion.equals(Constants.NO_ACTION)){
                        for(DataRow dr : dtDataSource.getAllRows()){
                            if(((DatosParcelaEntitie) dr.getTag()).get_id().toString().equals(datosParcelaEntitie.get_id().toString())){
                                dr.setEstado(accion);
                                dr.setTag(datosParcelaEntitie);
                                dr.set("parcela", datosParcelaEntitie.get_nomenclatura().get_parcela(), accion);
                                dr.set("calle", datosParcelaEntitie.get_calle().get_calle(), accion);
                                dr.set("calleModif", (datosParcelaEntitie.get_calleModificada() != null && datosParcelaEntitie.get_calleModificada().equals(datosParcelaEntitie.get_calle())) ? "" : datosParcelaEntitie.get_calleModificada().get_calle(), accion);
                                dr.set("altura", datosParcelaEntitie.get_altura(), accion);
                                dr.set("alturaModif",(datosParcelaEntitie.get_alturaModificada() != null && datosParcelaEntitie.get_alturaModificada().equals(datosParcelaEntitie.get_altura())) ? "" : datosParcelaEntitie.get_alturaModificada(),accion);
                                String catsAMostrar = datosParcelaEntitie.get_categoriasAmostrar();
                                dr.set("categoria", catsAMostrar, accion);
                                dr.set("categoriaModif", datosParcelaEntitie.get_categoriasModificadasAmostrar().equals(catsAMostrar) ? "" : datosParcelaEntitie.get_categoriasModificadasAmostrar(),accion);
                                dr.set("actividad", datosParcelaEntitie.getActividadAMostrar(), accion);
                                dr.set("actividadModif", datosParcelaEntitie.getActividadModificadaAMostrar().equals(datosParcelaEntitie.getActividadAMostrar()) ? " " : datosParcelaEntitie.getActividadModificadaAMostrar(),accion);
                                dr.set("observaciones",datosParcelaEntitie.get_observaciones(),accion);
                                break;
                            }
                        }
                    }
                }else{
                    DataRow dr;
                    dr = dtDataSource.newRow();
                    dr.setEstado(Constants.ACTION_INSERT);
                    dr.setTag(datosParcelaEntitie);
                    dr.set("parcela", datosParcelaEntitie.get_nomenclatura().get_parcela(), Constants.ACTION_INSERT);
                    dr.set("calle", datosParcelaEntitie.get_calle().get_calle(), Constants.ACTION_INSERT);
                    dr.set("calleModif", (datosParcelaEntitie.get_calleModificada() != null && datosParcelaEntitie.get_calleModificada().equals(datosParcelaEntitie.get_calle())) ? "" : datosParcelaEntitie.get_calleModificada().get_calle(), Constants.ACTION_INSERT);
                    dr.set("altura", datosParcelaEntitie.get_altura(), Constants.ACTION_INSERT);
                    dr.set("alturaModif",(datosParcelaEntitie.get_alturaModificada() != null && datosParcelaEntitie.get_alturaModificada().equals(datosParcelaEntitie.get_altura())) ? "" : datosParcelaEntitie.get_alturaModificada(),Constants.ACTION_INSERT);
                    String catsAMostrar = datosParcelaEntitie.get_categoriasAmostrar();
                    dr.set("categoria", catsAMostrar, Constants.ACTION_INSERT);
                    dr.set("categoriaModif", datosParcelaEntitie.get_categoriasModificadasAmostrar().equals(catsAMostrar) ? "" : datosParcelaEntitie.get_categoriasModificadasAmostrar(),Constants.ACTION_INSERT);
                    dr.set("actividad", datosParcelaEntitie.getActividadAMostrar(), Constants.ACTION_INSERT);
                    dr.set("actividadModif", datosParcelaEntitie.getActividadModificadaAMostrar().equals(datosParcelaEntitie.getActividadAMostrar()) ? " " : datosParcelaEntitie.getActividadModificadaAMostrar(),Constants.ACTION_INSERT);
                    dr.set("observaciones",datosParcelaEntitie.get_observaciones(),Constants.ACTION_INSERT);
                    dtDataSource.add(dr);
                }


                publishProgress(1);
                return null;

            }else{
			// Get data de la DB
			ArrayList<DatosParcelaEntitie> datosPar = controller
					.CargarDatosParcelas(params[0], params[1], params[2]);
			
			// Crear el datasource
			CustomDataTable dataSource = new CustomDataTable();

			// Definir columnas
			dataSource.addAllColumns(new String[]{"parcela", "calle",
                    "calleModif", "altura", "alturaModif",
                    "categoria", "categoriaModif",
                    "actividad", "actividadModif", "observaciones"});

			// create DataRow
			CustomDataTable.DataRow dr;

			if (datosPar == null) {
				dtDataSource = new CustomDataTable();
				publishProgress(0);
				return null;
			}

			for (DatosParcelaEntitie datosParcelaEntitie : datosPar) {
				dr = dataSource.newRow();

                String estado = datosParcelaEntitie.get_estado();
                dr.setEstado(estado);

				//accionCalleModificada = !(datosParcelaEntitie.get_calleModificada() == null || datosParcelaEntitie.get_calleModificada().esVacia()) ? Constants.ACTION_UPDATE: Constants.NO_ACTION;
				//accionAlturaModificada = (datosParcelaEntitie.get_alturaModificada() != null && !datosParcelaEntitie.get_alturaModificada().isEmpty()) ? Constants.ACTION_UPDATE: Constants.NO_ACTION;
				//accionCategoriaModificada = !datosParcelaEntitie.get_categoriasModificadasAmostrar().isEmpty() ? Constants.ACTION_UPDATE: Constants.NO_ACTION;
				//accionActividadModificada = !datosParcelaEntitie.getActividadModificadaAMostrar().isEmpty() ? Constants.ACTION_UPDATE: Constants.NO_ACTION;
				dr.setTag(datosParcelaEntitie);
				dr.set("parcela", datosParcelaEntitie.get_nomenclatura().get_parcela(), estado);
				dr.set("calle", datosParcelaEntitie.get_calle().get_calle(), estado);
				dr.set("calleModif", (datosParcelaEntitie.get_calleModificada() != null && datosParcelaEntitie.get_calleModificada().equals(datosParcelaEntitie.get_calle())) ? "" : datosParcelaEntitie.get_calleModificada().get_calle(), estado);
				dr.set("altura", datosParcelaEntitie.get_altura(), estado);
				dr.set("alturaModif",(datosParcelaEntitie.get_alturaModificada() != null && datosParcelaEntitie.get_alturaModificada().equals(datosParcelaEntitie.get_altura())) ? "" : datosParcelaEntitie.get_alturaModificada(),estado);
				String catsAMostrar = datosParcelaEntitie.get_categoriasAmostrar();
                dr.set("categoria", catsAMostrar, estado);
				dr.set("categoriaModif", datosParcelaEntitie.get_categoriasModificadasAmostrar().equals(catsAMostrar) ? "" : datosParcelaEntitie.get_categoriasModificadasAmostrar(),estado);
				dr.set("actividad", datosParcelaEntitie.getActividadAMostrar(), estado);
				dr.set("actividadModif", datosParcelaEntitie.getActividadModificadaAMostrar().equals(datosParcelaEntitie.getActividadAMostrar()) ? " " : datosParcelaEntitie.getActividadModificadaAMostrar(),estado);
				dr.set("observaciones",datosParcelaEntitie.get_observaciones(),estado);

				dataSource.add(dr);
			}

			dtDataSource = dataSource;
			publishProgress(1);

			return null;

            }
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			gridParcelas.setDataSource(dtDataSource);
			gridParcelas.refresh();
		}

		@Override
		protected void onPostExecute(Void result) {
			pDialog.dismiss();
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	//Crea las opciones de menu para enviar los cambios por WS
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0,ENVIO_ACTUALIZACION,1,"Enviar datos").setIcon(android.R.drawable.ic_menu_share).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {

                int cantidad = 0;

                final List<DatosParcelaEntitie> datosAGuardar = controller.getDatosAGuardar();

                if(datosAGuardar.size() == 0){
                    Toast.makeText(getApplicationContext(),"No hay datos para guardar",Toast.LENGTH_LONG).show();
                    return true;
                }

                if(datosAGuardar != null) cantidad = datosAGuardar.size();

                AlertDialog.Builder alertDialogGuardar = new AlertDialog.Builder(ParcelasActivity.this);
                // set title
                alertDialogGuardar.setTitle("GUARDAR DATOS");

                alertDialogGuardar.setMessage("Se guardara(n) " + cantidad + " dato(s) ¿Desea continuar?");
                alertDialogGuardar.setCancelable(false);

                alertDialogGuardar.setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {

                                dialog.dismiss();

                                pDialogGuardado = new ProgressDialog(ParcelasActivity.this);
                                pDialogGuardado.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                pDialogGuardado.setMessage("Esto puede tardar varios minutos");
                                pDialogGuardado.setTitle("Guardando datos...");
                                pDialogGuardado.setCancelable(false);
                                pDialogGuardado.setMax(100);

                                miTareaAsync = new SendDatosParcelaAsync();
                                miTareaAsync.execute(datosAGuardar);
                            }
                        });

                alertDialogGuardar.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogGuardar.create();

                alertDialog.show();

                return true;
			}
		});
		menu.add(0,RECIBIR_ACTUALIZACION,2,"Recibir datos").setIcon(android.R.drawable.ic_menu_rotate);
		return true;	
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		closeOptionsMenu();
		return super.onPrepareOptionsMenu(menu);
	}


    private void comenzarLocalizacion()
    {
        //Obtenemos una referencia al LocationManager
        locManager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Nos registramos para recibir actualizaciones de la posiciÃ³n
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                guardarPosicion(location);
            }
            public void onProviderDisabled(String provider){
            }
            public void onProviderEnabled(String provider){
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                Log.i("", "Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 4000, 0, locListener);
    }

    private void guardarPosicion(Location loc) {
        if(loc != null)
        {
            latitudActual = String.valueOf(loc.getLatitude());
            longitudActual = String.valueOf(loc.getLongitude());
            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
        else
        {
            latitudActual = "(sin_datos)";
            longitudActual = "(sin_datos)";
        }
    }

    public CalleEntitie getCallePreSeleccionada() {
		return callePreSeleccionada;
	}
	public void setCallePreSeleccionada(CalleEntitie callePreSeleccionada) {
		this.callePreSeleccionada = callePreSeleccionada;
	}

	/**
     *Envio de datos de parcelas por WS
     *
     */
    public class SendDatosParcelaAsync extends AsyncTask<List<DatosParcelaEntitie>, Integer, Boolean> {

        protected Boolean doInBackground(List<DatosParcelaEntitie>... params) {

            List<DatosParcelaEntitie> datos = params[0];

            String exito = controller.enviarDatosParcelasWS(datos);
            publishProgress(100);

            return exito.equals("0");
        }

        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            pDialogGuardado.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {

            pDialogGuardado.setProgress(0);
            pDialogGuardado.show();
        }

        protected void onPostExecute(Boolean result) {

            pDialogGuardado.dismiss();
            if(result)
                Toast.makeText(getBaseContext(), "Los datos fueron guardados exitosamente" , Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getBaseContext(), "Hubo errores al guardar algunos datos. Consulte con el administrador" , Toast.LENGTH_LONG).show();
        }

    }


    
    
}
