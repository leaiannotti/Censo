package com.censoMain.buttonListeners;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.censoMain.censofiscalgisB.ParcelasActivity;
import com.censoMain.censofiscalgisB.R;
import com.censoMain.constantes.Constants;
import com.censoMain.customControlls.CustomDataTable;
import com.censoMain.lists.ActividadesListDialog;
import com.model.bussinesEntities.ActividadEntitie;
import com.model.bussinesEntities.CalleEntitie;
import com.model.bussinesEntities.DatosParcelaEntitie;

/**
 * Created by liannotti on 22/07/14.
 */
public class UpdateGridListener extends Activity implements AdapterView.OnItemClickListener{

    private ParcelasActivity _pAct;
    private List<ActividadEntitie> actividadesSeleccionadas;
    private CalleEntitie _calleSeleccionada;
    private EditText cantIndustrias;
    private EditText cantComercios;

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

    public List<ActividadEntitie> getActividadesSeleccionadas() {
        return actividadesSeleccionadas;
    }
    public void setActividadesSeleccionadas(List<ActividadEntitie> actividadesSeleccionadas) {
        this.actividadesSeleccionadas = actividadesSeleccionadas;
    }

    public UpdateGridListener(ParcelasActivity p){
        this._pAct=p;
    }

    @Override
    public void onItemClick(AdapterView<?> a, View v, int pos,
                            long id) {

        CustomDataTable dt = _pAct.getGridParcelas().getDataSource();
        CustomDataTable.DataRow dr = dt.getRow(pos);

        //Obtengo el dato de la fila correspondiente
        final DatosParcelaEntitie dp = (DatosParcelaEntitie) dr.getTag();

        //Si el dato esta dado de baja, no hago nada.
        if (dp.get_estado().equals(Constants.ACTION_DELETE))return;

        final Dialog dialog = new Dialog(_pAct);
        dialog.setContentView(R.layout.popup_edit_parcela);
        dialog.setTitle(Constants.TITLE_MODIFICAR_DATOS);
        dialog.setCancelable(false);

        TextView txtNomenclatura = (TextView) dialog.findViewById(R.id.nomenclaturaText);
        TextView txtParcela = (TextView) dialog.findViewById(R.id.parcelaText);
        final EditText txtAltura = (EditText) dialog.findViewById(R.id.alturaEdit);
        final CheckBox chkViviUni = (CheckBox) dialog.findViewById(R.id.chkCategViviUni);
        final CheckBox chkViviMulti = (CheckBox) dialog.findViewById(R.id.chkCategViviMulti);
        final CheckBox chkComercio = (CheckBox) dialog.findViewById(R.id.chkCategComercio);
        final CheckBox chkIndustria = (CheckBox) dialog.findViewById(R.id.chkCategIndustria);
        final CheckBox chkBaldio = (CheckBox) dialog.findViewById(R.id.chkCategBaldio);
        final CheckBox chkObraconst = (CheckBox) dialog.findViewById(R.id.chkCategObraConst);

        final EditText txtComercioCant = (EditText) dialog.findViewById(R.id.txtCategComercio);
        final EditText txtIndustriaCant = (EditText) dialog.findViewById(R.id.txtCategIndustria);
        setCantComercios(txtComercioCant);
        setCantIndustrias(txtIndustriaCant);

        _pAct.setPath_imagen(null);

        Button btnGuardar = (Button) dialog.findViewById(R.id.guardarParcelaButton);
        Button btnCancelar = (Button) dialog.findViewById(R.id.cancelarParcelaButton);
        Button tomarFoto = (Button) dialog.findViewById(R.id.tomarFotoButton);
        Button selecActividad = (Button) dialog.findViewById(R.id.btnActividades);
    
        final List<CalleEntitie> calles = _pAct.getController().CargarCallesPorManzana(_pAct.getManzSp().getSelectedItem().toString(),
        		_pAct.getCircunsSp().getSelectedItem().toString(),  _pAct.getSeccionSp().getSelectedItem().toString());

        //Set enable/disable edittext for each categ.
        chkComercio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                txtComercioCant.setEnabled(b);
                txtComercioCant.setText(b ? "1" : " ");
            }
        });

        chkIndustria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                txtIndustriaCant.setEnabled(b);
                txtIndustriaCant.setText(b? "1" : " ");
            }
        });

        //Set Camera button
        tomarFoto.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                _pAct.startActivityForResult(intent, ParcelasActivity.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        final Spinner cmbCalles = (Spinner) dialog.findViewById(R.id.cmbCalles);

        ArrayAdapter<CalleEntitie> adapterCalles = new ArrayAdapter<CalleEntitie>(_pAct, android.R.layout.simple_spinner_item, 
        		calles);
        cmbCalles.setAdapter(adapterCalles);
        cmbCalles.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View v,
					int i, long l) {
					_calleSeleccionada = (CalleEntitie) adapterView.getAdapter().getItem(i);
                	_pAct.setCallePreSeleccionada(_calleSeleccionada);
				}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		
        });

        int spinnerPosition = 0;
        
        if(_pAct.getCallePreSeleccionada() != null)
        	spinnerPosition = adapterCalles.getPosition(_pAct.getCallePreSeleccionada());
        	
        if (dp.get_estado().equals(Constants.ACTION_INSERT) || dp.get_estado().equals(Constants.ACTION_UPDATE) ) {
            _calleSeleccionada = dp.get_calleModificada();
            spinnerPosition = adapterCalles.getPosition(_calleSeleccionada);
        }else
            _calleSeleccionada = null;
		

        cmbCalles.setSelection(spinnerPosition);
        

        if (dp.get_nomenclatura() != null){
            txtNomenclatura.setText(dp.get_nomenclatura().toString());
            txtParcela.setText(dp.get_nomenclatura().get_parcela());
        }
        else
            txtNomenclatura.setText("No hay datos para la nomenclatura");


        txtAltura.setText(dp.get_alturaModificada() == null ? "" : dp.get_alturaModificada().toString());

        final int cantidadComercios = dp.get_categoriasModificadas().get(Constants.COMERCIO_ABV).intValue();
        final int cantidadIndustrias = dp.get_categoriasModificadas().get(Constants.INDUSTRIA_ABV).intValue();

        chkViviUni.setChecked(dp.get_categoriasModificadas().get(Constants.VIVIENDA_UNIFAMILIAR_ABV).intValue() != 0);
        chkViviMulti.setChecked(dp.get_categoriasModificadas().get(Constants.VIVIENDA_MULTIFAMILIAR_ABV).intValue() != 0);
        chkComercio.setChecked(cantidadComercios != 0);
        chkIndustria.setChecked(cantidadIndustrias != 0);
        chkBaldio.setChecked(dp.get_categoriasModificadas().get(Constants.BALDIO_ABV).intValue() != 0);
        chkObraconst.setChecked(dp.get_categoriasModificadas().get(Constants.OBRACONST_ABV).intValue() != 0);

        if(chkComercio.isChecked()){
            txtComercioCant.setText(dp.get_categoriasModificadas().get(Constants.COMERCIO_ABV).toString());
        }
        if(chkIndustria.isChecked()){
            txtIndustriaCant.setText(dp.get_categoriasModificadas().get(Constants.INDUSTRIA_ABV).toString());
        }


        setActividadesSeleccionadas(dp.get_actividadesModificadas());
        selecActividad.setOnClickListener(new ActividadesListDialog(_pAct,this));

        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();

            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dp.set_actividadesModificadas(getActividadesSeleccionadas());

                if(!ValidarDatos(dp)) return;

                //if (!_calleSeleccionada.equals(dp.get_calle()))
                dp.set_calleModificada(_calleSeleccionada);
                //else
                //	dp.set_calleModificada(dp.get_calle());

                //if (!txtAltura.getText().toString().equals(dp.get_altura()))
                dp.set_alturaModificada(txtAltura.getText().toString());
                //else
                //	dp.set_alturaModificada("");

                this.SetCategsModificadas(dp);


                String uid = _pAct.getController().GetNroTablet();
                dp.set_deviceNumber(uid);

                dp.set_latitud(_pAct.getLatitudActual() == null ? "" : _pAct.getLatitudActual());
                dp.set_longitud(_pAct.getLongitudActual() == null ? "" : _pAct.getLongitudActual());

                if(_pAct.getPath_imagen() != null) dp.set_pathImagen(_pAct.getPath_imagen());

                boolean exito = _pAct.getController().UpdateDatosParcela(dp);

                if (!exito)
                    Toast.makeText(_pAct.getApplicationContext(),"Error al update",Toast.LENGTH_LONG).show();
                else {
                    ParcelasActivity.RefreshGrid(dp.get_nomenclatura().get_circunscripcion(), dp.get_nomenclatura().get_seccion(), dp.get_nomenclatura().get_manzOFracc(),dp.get_id().toString(),Constants.ACTION_UPDATE);
                }

                dialog.dismiss();
            }

            private boolean ValidarDatos(DatosParcelaEntitie dp) {

                boolean ok = true;
                boolean comInd = false;
                String error = new String();

                if(chkIndustria.isChecked()){
                    if(txtIndustriaCant.getText().toString().length() == 0 || txtIndustriaCant.getText().toString().equals("0")){
                        error += "-Debe indicar la cantidad de industrias \n";
                        ok = false;
                    }else{
                        if(getActividadesSeleccionadas().size() == 0){
                            error += "-Debe seleccionar al menos una actividad si selecciono comercio y/o industria \n";
                            ok = false;
                            comInd = true;
                        }
                    }
                }

                if(chkComercio.isChecked()){
                    if(txtComercioCant.getText().toString().length() == 0 || txtComercioCant.getText().toString().equals("0")){
                        error += "-Debe indicar la cantidad de comercios \n";
                        ok = false;
                    }else{
                        if(dp.get_actividadesModificadas().size() == 0 && !comInd){
                            error += "-Debe seleccionar al menos una actividad si selecciono comercio y/o industria \n";
                            ok = false;
                        }
                    }
                }

                if(!ok)
                    Toast.makeText(_pAct.getApplicationContext(),error,Toast.LENGTH_LONG).show();

                return ok;


            }

            private void SetCategsModificadas(DatosParcelaEntitie dp) {

                HashMap<String,Integer> categoriasModif = crearDiccionarioCategoriasVacio();

                if(chkViviUni.isChecked())
                    categoriasModif.put(Constants.VIVIENDA_UNIFAMILIAR_ABV,1);

                if(chkViviMulti.isChecked())
                    categoriasModif.put(Constants.VIVIENDA_MULTIFAMILIAR_ABV,1);

                if(chkComercio.isChecked())
                    categoriasModif.put(Constants.COMERCIO_ABV,Integer.parseInt(txtComercioCant.getText().toString()));

                if(chkIndustria.isChecked())
                    categoriasModif.put(Constants.INDUSTRIA_ABV,Integer.parseInt(txtIndustriaCant.getText().toString()));

                if(chkBaldio.isChecked())
                    categoriasModif.put(Constants.BALDIO_ABV,1);

                if(chkObraconst.isChecked())
                    categoriasModif.put(Constants.OBRACONST_ABV,1);

                dp.set_categoriasModificadas(categoriasModif);
            }

            private HashMap<String, Integer> crearDiccionarioCategoriasVacio() {

                HashMap<String,Integer> dicc = new HashMap<String, Integer>();
                dicc.put(Constants.VIVIENDA_UNIFAMILIAR_ABV,0);
                dicc.put(Constants.VIVIENDA_MULTIFAMILIAR_ABV,0);
                dicc.put(Constants.COMERCIO_ABV,0);
                dicc.put(Constants.INDUSTRIA_ABV,0);
                dicc.put(Constants.OBRACONST_ABV,0);
                dicc.put(Constants.BALDIO_ABV,0);
                return dicc;

            }
        });

        dialog.show();
    }




}
