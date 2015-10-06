package com.censoMain.buttonListeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.censoMain.censofiscalgisB.ParcelasActivity;
import com.censoMain.constantes.Constants;
import com.censoMain.customControlls.CustomDataTable;
import com.model.bussinesEntities.DatosParcelaEntitie;

/**
 * Created by liannotti on 22/07/14.
 */
public class DeleteGridListener implements AdapterView.OnItemLongClickListener {

    private ParcelasActivity _pAct;

    public DeleteGridListener(ParcelasActivity p){
        this._pAct=p;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> av, View v,
                                   int pos, long id) {


        CustomDataTable dt = _pAct.getGridParcelas().getDataSource();
        CustomDataTable.DataRow dr = dt.getRow(pos);

        final DatosParcelaEntitie dp = (DatosParcelaEntitie) dr.getTag();

        if (dp.get_estado().equals(Constants.ACTION_UPDATE) || dp.get_estado().equals(Constants.NO_ACTION) ) {

            AlertDialog.Builder alertDialogEliminar = new AlertDialog.Builder(_pAct);
            // set title
            alertDialogEliminar.setTitle(Constants.ELIMINAR_DATOS);

            alertDialogEliminar.setMessage("Desea eliminar los datos relacionados con esta parcela?");
            alertDialogEliminar.setCancelable(false);

            alertDialogEliminar.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(
                                DialogInterface dialog,
                                int which) {

                            boolean exito = _pAct.getController().EliminarDatosParcela(dp);

                            if (!exito)
                                Toast.makeText(_pAct.getApplicationContext(),"Error al delete",Toast.LENGTH_LONG).show();
                            else {
                            	ParcelasActivity.RefreshGrid(dp.get_nomenclatura().get_circunscripcion(),dp.get_nomenclatura().get_seccion(),
                                        dp.get_nomenclatura().get_manzOFracc(),dp.get_id().toString(),Constants.ACTION_DELETE);
                            }

                            dialog.dismiss();
                        }
                    });

            alertDialogEliminar.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogEliminar.create();

            alertDialog.show();

        } else if(dp.get_estado().equals(Constants.ACTION_INSERT) ){

            //TODO SI EL ESTADO ES INSERTADO,ELIMINAR LA PARCELA COMPLETAMENTE

            AlertDialog.Builder alertDialogEliminar = new AlertDialog.Builder(_pAct);
            // set title
            alertDialogEliminar.setTitle(Constants.ELIMINAR_DATOS);

            alertDialogEliminar.setMessage("¿Desea eliminar los datos relacionados con esta parcela? (Se eliminara permanentemente)");
            alertDialogEliminar.setCancelable(false);

            alertDialogEliminar.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(
                                DialogInterface dialog,
                                int which) {

                            boolean exito = _pAct.getController().EliminarDatosParcelaPermanente(dp);

                            if (!exito)
                                Toast.makeText(_pAct.getApplicationContext(),"Error al delete",Toast.LENGTH_LONG).show();
                            else {
                            	ParcelasActivity.RefreshGrid(dp.get_nomenclatura().get_circunscripcion(),dp.get_nomenclatura().get_seccion(),
                                        dp.get_nomenclatura().get_manzOFracc(),dp.get_id().toString(),Constants.ACTION_DELETE_PERMANENTLY);
                            }

                            dialog.dismiss();
                        }
                    });

            alertDialogEliminar.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogEliminar.create();

            alertDialog.show();


        }else{

            AlertDialog.Builder alertDialogDeshacer = new AlertDialog.Builder(_pAct);

            // set title
            alertDialogDeshacer.setTitle("Deshacer eliminar datos...");

            // set dialog message
            alertDialogDeshacer.setMessage("Desea deshacer la eliminacion de los datos relacionados con esta parcela?");
            alertDialogDeshacer.setCancelable(false);

            alertDialogDeshacer.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,int which) {

                            boolean exito = _pAct.getController().DeshacerEliminarDatosParcela(dp);

                            if (!exito)
                                Toast.makeText(_pAct.getApplicationContext(),"Error al deshacer el eliminar",Toast.LENGTH_LONG).show();
                            else {
                            	ParcelasActivity.RefreshGrid(dp.get_nomenclatura().get_circunscripcion(),
                                        dp.get_nomenclatura().get_seccion(),dp.get_nomenclatura().get_manzOFracc(),dp.get_id().toString(),Constants.NO_ACTION);
                            }

                            dialog.dismiss();
                        }
                    });

            alertDialogDeshacer.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogDeshacer.create();

            alertDialog.show();
        }

        return true;

    }
}
