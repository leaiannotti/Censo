package com.censoMain.buttonListeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.censoMain.censofiscalgisB.ParcelasActivity;
import com.censoMain.censofiscalgisB.R;
import com.censoMain.constantes.Constants;
import com.censoMain.lists.ActividadesListDialog;
import com.model.bussinesEntities.ActividadEntitie;
import com.model.bussinesEntities.CalleEntitie;
import com.model.bussinesEntities.DatosParcelaEntitie;
import com.model.bussinesEntities.ParcelaEntitie;

/**
 * Created by liannotti on 22/07/14.
 */
public class AddParcelaButtonListener extends Activity implements OnClickListener{

    ParcelasActivity _pActivity;
    private List<ActividadEntitie> _actividadesSeleccionadas;
    private EditText cantIndustrias;
    private EditText cantComercios;
    private CalleEntitie _calleSeleccionada;

    public EditText getCantIndustrias() {
        return cantIndustrias;
    }
    public void setCantIndustrias(EditText cantIndustrias) {
        this.cantIndustrias = cantIndustrias;
    }
    public EditText getCantComercios() {
        return cantComercios;
    }
    public void setCantComercios(EditText cantComercios) {
        this.cantComercios = cantComercios;
    }

    public List<ActividadEntitie> get_actividadesSeleccionadas() {
        return _actividadesSeleccionadas;
    }
    public void set_actividadesSeleccionadas(List<ActividadEntitie> _actividadesSeleccionadas) {
        this._actividadesSeleccionadas = _actividadesSeleccionadas;
    }

    /**
     *
      */
    public AddParcelaButtonListener(ParcelasActivity p){
        this._pActivity = p;
    }

    @Override
    public void onClick(View view) {

        //TODO AGREGAR EL LISTENER DEL BOTON DE SACAR FOTO, AGREGAR EL CAMPO DE OBSERVACIONES

        final ParcelaEntitie nomenclatura = _pActivity.getController().CargarParcela(
                _pActivity.getCircunsSp().getSelectedItem().toString(), _pActivity.getSeccionSp().getSelectedItem().toString(),
                _pActivity.getManzSp().getSelectedItem().toString());

        final Dialog dialog = new Dialog(_pActivity);

        final List<CalleEntitie> calles = _pActivity.getController().CargarCallesPorManzana(_pActivity.getManzSp().getSelectedItem().toString(),
        		_pActivity.getCircunsSp().getSelectedItem().toString(),  _pActivity.getSeccionSp().getSelectedItem().toString());
        //final List<ActividadEntitie> actividades = _pActivity.getActividades();

        dialog.setContentView(R.layout.popup_add_parcela);
        dialog.setTitle(Constants.TITLE_AGREGARPARCELA);
        dialog.setCancelable(false);

        //-----------------CATEGORIAS---------------------
        final CheckBox chkCategViviUni = (CheckBox) dialog.findViewById(R.id.chkCategViviUni);
        final CheckBox chkCategViviMulti = (CheckBox) dialog.findViewById(R.id.chkCategViviMulti);
        final CheckBox chkCategComercio = (CheckBox) dialog.findViewById(R.id.chkCategComercio);
        final CheckBox chkCategIndustria = (CheckBox) dialog.findViewById(R.id.chkCategIndustria);
        final CheckBox chkCategBaldio = (CheckBox) dialog.findViewById(R.id.chkCategBaldio);
        final CheckBox chkCategObraConst = (CheckBox) dialog.findViewById(R.id.chkCategObraConst);
        final EditText txtCategIndustrias = (EditText) dialog.findViewById(R.id.txtCategIndustria);
        this.setCantIndustrias(txtCategIndustrias);
        final EditText txtCategComercios = (EditText) dialog.findViewById(R.id.txtCategComercio);
        this.setCantComercios(txtCategComercios);
        //---------------------------------------------------

        final Spinner cmbCalles = (Spinner) dialog.findViewById(R.id.cmbCalles);
        final EditText txtAltura = (EditText) dialog.findViewById(R.id.alturaAdd);

        ArrayAdapter<CalleEntitie> adapterCalles = new ArrayAdapter<CalleEntitie>(_pActivity, android.R.layout.simple_spinner_item, calles);
        cmbCalles.setAdapter(adapterCalles);
        cmbCalles.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View v,
					int i, long l) {
                _calleSeleccionada = (CalleEntitie) adapterView.getAdapter().getItem(i);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		
        });

        final EditText parcelaText = (EditText) dialog.findViewById(R.id.nroParcelaAdd);

        Button btnGuardar = (Button) dialog.findViewById(R.id.guardarParcelaButtonAdd);
        Button btnCancelar = (Button) dialog.findViewById(R.id.cancelarParcelaButtonAdd);
        Button tomarFoto = (Button) dialog.findViewById(R.id.tomarFotoButton);
        Button btnActividades = (Button) dialog.findViewById(R.id.btnActividades);

        //TODO VER SI SE PUEDE HACER LO DE BUSCAR Y QUE VAYA FILTRANDO (PARCELAS EN PYP)

        //Set enable/disable edittext for each categ.
        chkCategComercio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                txtCategComercios.setEnabled(b);
                txtCategComercios.setText(b ? "1" : "");
            }
        });

        chkCategIndustria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                txtCategIndustrias.setEnabled(b);
                txtCategIndustrias.setText(b? "1" : "");

            }
        });

        //Set Camera button
        tomarFoto.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                _pActivity.startActivityForResult(intent, ParcelasActivity.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        set_actividadesSeleccionadas(new ArrayList<ActividadEntitie>());
        btnActividades.setOnClickListener(new ActividadesListDialog(_pActivity,this));

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Supongo q cuando deselecciono el combo se borra el valor
                //final int cantidadIndustrias = (txtCategIndustrias.getText() != null && !txtCategIndustrias.getText().toString().isEmpty()) ?Integer.parseInt(txtCategIndustrias.getText().toString()) : 0;
                //final int cantidadComercios = (txtCategComercios.getText() != null && !txtCategComercios.getText().toString().isEmpty()) ?Integer.parseInt(txtCategComercios.getText().toString()) : 0;

                //TODO: Verificar que la parcela que agrego no este
                if(!DatosValidos()) return;

                DatosParcelaEntitie dp = new DatosParcelaEntitie();

                dp.set_calle(_calleSeleccionada);
                dp.set_calleModificada(_calleSeleccionada);

                int cantidad;
                HashMap<String,Integer> dicCategorias = new HashMap<String, Integer>();
                dicCategorias.put(Constants.VIVIENDA_UNIFAMILIAR_ABV,chkCategViviUni.isChecked() ? 1 : 0);
                dicCategorias.put(Constants.VIVIENDA_MULTIFAMILIAR_ABV,chkCategViviMulti.isChecked() ? 1 : 0);
                cantidad = getCantidadCateg(Constants.COMERCIO_ABV);
                dicCategorias.put(Constants.COMERCIO_ABV,chkCategComercio.isChecked() ? cantidad : 0);
                cantidad = getCantidadCateg(Constants.INDUSTRIA_ABV);
                dicCategorias.put(Constants.INDUSTRIA_ABV,chkCategIndustria.isChecked() ? cantidad : 0);
                dicCategorias.put(Constants.BALDIO_ABV, chkCategBaldio.isChecked() ? 1 : 0);
                dicCategorias.put(Constants.OBRACONST_ABV,chkCategObraConst.isChecked() ? 1 : 0);

                String uid = _pActivity.getController().GetNroTablet();
                dp.set_deviceNumber(uid);

                //Agrego ambos diccionarios a los datos.
                dp.set_categorias(dicCategorias);
                dp.set_categoriasModificadas(dicCategorias);

                dp.set_estado(Constants.ACTION_INSERT);

                dp.set_altura(txtAltura.getText().toString());
                dp.set_alturaModificada(txtAltura.getText().toString());

                dp.set_actividades(get_actividadesSeleccionadas());
                dp.set_actividadesModificadas(get_actividadesSeleccionadas());

                ParcelaEntitie nomenclaturaNueva = new ParcelaEntitie();
                nomenclaturaNueva.set_circunscripcion(nomenclatura.get_circunscripcion());
                nomenclaturaNueva.set_seccion(nomenclatura.get_seccion());
                nomenclaturaNueva.set_manzOFracc(nomenclatura.get_manzOFracc());
                nomenclaturaNueva.set_tipoManz(nomenclatura.get_tipoManz());
                nomenclaturaNueva.set_parcela(parcelaText.getText().toString().trim());

                dp.set_nomenclatura(nomenclaturaNueva);

                dp.set_actividadesModificadas(get_actividadesSeleccionadas());
                dp.set_actividades(get_actividadesSeleccionadas());


                dp.set_latitud(_pActivity.getLatitudActual() == null ? "" : _pActivity.getLatitudActual());
                dp.set_longitud(_pActivity.getLongitudActual() == null ? "" : _pActivity.getLongitudActual());

                Long idDato = _pActivity.getController().GetNextIdDato();

                if(idDato != -1) dp.set_id(idDato);
                else{
                    Toast.makeText(_pActivity.getApplicationContext(), "Error al obtener el id para el dato", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }

                Long rowNumDato = _pActivity.getController().AgregarParcela(dp);

                boolean exito = rowNumDato != -1;

                if (!exito)
                    Toast.makeText(_pActivity.getApplicationContext(), "Error al agregarParcela", Toast.LENGTH_LONG).show();
                else {
                    ParcelasActivity.RefreshGrid(dp.get_nomenclatura().get_circunscripcion(), dp.get_nomenclatura().get_seccion(),
                            dp.get_nomenclatura().get_manzOFracc(),dp.get_id().toString(),Constants.ACTION_INSERT);
                }
                dialog.dismiss();
            }

            private boolean DatosValidos() {

                boolean ok = true;
                String error = new String();

                if(parcelaText.getText().toString().trim().length() == 0){
                    error += "-Debe indicar el numero de parcela \n";
                    ok = false;
                }

                if(chkCategIndustria.isChecked()){
                    if(txtCategIndustrias.getText().toString().length() == 0 || txtCategIndustrias.getText().toString().equals("0")){
                        error += "-Debe indicar la cantidad de industrias \n";
                        ok = false;
                    }
                }

                if(chkCategComercio.isChecked()){
                    if(txtCategComercios.getText().toString().length() == 0 || txtCategComercios.getText().toString().equals("0")){
                        error += "-Debe indicar la cantidad de comercios \n";
                        ok = false;
                    }
                }

                if(!ok)
                    Toast.makeText(_pActivity.getApplicationContext(),error,Toast.LENGTH_LONG).show();

                return ok;
            }

            private int getCantidadCateg(String categ) {

                int reg = 0;

                if (categ.equals(Constants.COMERCIO_ABV)){
                    if(chkCategComercio.isChecked())
                        reg = Integer.parseInt(txtCategComercios.getText().toString());
                }
                else if(categ.equals(Constants.INDUSTRIA_ABV)){
                    if(chkCategIndustria.isChecked())
                        reg = Integer.parseInt(txtCategIndustrias.getText().toString());
                }

                return reg;
            }

        });
        dialog.show();
    }
}
