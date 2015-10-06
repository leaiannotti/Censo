package com.censoMain.lists;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.censoMain.buttonListeners.AddParcelaButtonListener;
import com.censoMain.buttonListeners.UpdateGridListener;
import com.censoMain.censofiscalgisB.ParcelasActivity;
import com.censoMain.censofiscalgisB.R;
import com.censoMain.constantes.Constants;
import com.model.bussinesEntities.ActividadEntitie;

/**
 * Created by liannotti on 19/07/14.
 */
public class ActividadesListDialog implements View.OnClickListener{

    public List<ActividadEntitie> _inititalSelectedActivities;
    public List<ActividadEntitie> _selectedActivities;
    private Activity _parent;
    private ParcelasActivity _pAct;
    private boolean _esAlta;
    private int _cantidadAct;

    private ArrayAdapter<ActividadEntitie> _allActivitiesAdapter;

    public int get_cantidadAct() {
        return _cantidadAct;
    }
    public void set_cantidadAct(int _cantidadAct) {
        this._cantidadAct = _cantidadAct;
    }
    public List<ActividadEntitie> get_inititalSelectedActivities() {
        return _inititalSelectedActivities;
    }
    public void set_inititalSelectedActivities(List<ActividadEntitie> _inititalSelectedActivities) {
        this._inititalSelectedActivities = _inititalSelectedActivities;
    }
    public List<ActividadEntitie> get_selectedActivities() {
        return _selectedActivities;
    }
    public void set_selectedActivities(List<ActividadEntitie> _selectedActivities) {
        this._selectedActivities = _selectedActivities;
    }
    public ArrayAdapter<ActividadEntitie> get_allActivitiesAdapter() {
        return _allActivitiesAdapter;
    }
    public void set_allActivitiesAdapter(ArrayAdapter<ActividadEntitie> _allActivitiesAdapter) {
        this._allActivitiesAdapter = _allActivitiesAdapter;
    }


    public ActividadesListDialog(ParcelasActivity p,AddParcelaButtonListener addParcelaButtonListener){
        _pAct=p;
        _parent = addParcelaButtonListener;
        _inititalSelectedActivities = new ArrayList<ActividadEntitie>();
        _selectedActivities = new ArrayList<ActividadEntitie>();
        _esAlta = true;
    }

    public ActividadesListDialog(ParcelasActivity pAct, UpdateGridListener updateGridListener) {
        _pAct = pAct;
        _parent = updateGridListener;
        _inititalSelectedActivities = new ArrayList<ActividadEntitie>(updateGridListener.getActividadesSeleccionadas()); //Copia
        _selectedActivities =new ArrayList<ActividadEntitie>(updateGridListener.getActividadesSeleccionadas()); //Copia
        _esAlta = false;
    }


    @Override
    public void onClick(View view) {

        final Dialog dialogAct = new Dialog(_pAct);
        dialogAct.setContentView(R.layout.actividades_layout);
        dialogAct.setCancelable(false);
        dialogAct.setTitle(Constants.TITLE_ADMINISTRARACTIVIDADES);
        final EditText filter     = (EditText)    dialogAct.findViewById(R.id.filterAllAct);
        final ListView allAct     = (ListView)    dialogAct.findViewById(R.id.listActAll);
        final ListView selAct     = (ListView)    dialogAct.findViewById(R.id.listActSeleccionadas);
        Button   saveAct = (Button)      dialogAct.findViewById(R.id.okActividadesBtn);
        Button   cancelAct = (Button)      dialogAct.findViewById(R.id.cancelActividadesBtn);

        //Defino las variables de cantidad de actividades y actividades seleccionadas
        if(_esAlta){
            set_cantidadAct(getCantidadActividadesAdd());
            AddParcelaButtonListener lis = (AddParcelaButtonListener) _parent;
            set_inititalSelectedActivities(lis.get_actividadesSeleccionadas());
        }else{
            set_cantidadAct(getCantidadActividadesUpdate());
            UpdateGridListener lis = (UpdateGridListener) _parent;
            set_inititalSelectedActivities(lis.getActividadesSeleccionadas());
        }

        //Si la cantidad de actividades es 0, entonces no puedo agregar actividades
        if(get_cantidadAct() == 0) {
            Toast.makeText(_pAct.getApplicationContext(),"No puede agregar actividades si no hay por lo menos un comercio o industria",Toast.LENGTH_LONG).show();
            return;
        }

        //La cantidad de actividades que se puede agregar es la cantidad de comercios + industria - la cantidad de actividades ya elegidas
        set_cantidadAct(get_cantidadAct() - get_inititalSelectedActivities().size());

        //Lista para llenar la lista de todas las actividades
        final List<ActividadEntitie> allActivities = _esAlta ? _pAct.getController().CargarActividades() : CargarActividadesNoSeleccionadas();

        //Adapter para la lista de todas la actividades (esto es para fillear la lista)
        set_allActivitiesAdapter(new ArrayAdapter<ActividadEntitie>(_pAct.getApplicationContext(),R.layout.activity_item,allActivities));

        //Seteo los valores iniciales de las listas
        allAct.setAdapter(get_allActivitiesAdapter());
        selAct.setAdapter(new ArrayAdapter<ActividadEntitie>(_pAct.getApplicationContext(), R.layout.activity_item, _inititalSelectedActivities));

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ArrayAdapter<ActividadEntitie> acts = ActividadesListDialog.this._allActivitiesAdapter;
                acts.getFilter().filter(charSequence.toString().trim());
                allAct.setAdapter(acts);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        selAct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Aumento la cantidad.
                int cantidad = get_cantidadAct();
                cantidad ++;
                set_cantidadAct(cantidad);

                //Refresco la nueva lista de actividades seleccionadas
                List<ActividadEntitie> selectedActivities = get_selectedActivities();
                ActividadEntitie actSeleccionada = selectedActivities.get(i);
                selectedActivities.remove(actSeleccionada);
                set_selectedActivities(selectedActivities);

                //Agrego la activdad a todas las actividades
                allActivities.add(actSeleccionada);

                //Seteo los nuevos adapters
                set_allActivitiesAdapter(new ArrayAdapter<ActividadEntitie>(_pAct.getApplicationContext(),R.layout.activity_item,allActivities));
                allAct.setAdapter(get_allActivitiesAdapter());
                selAct.setAdapter(new ArrayAdapter<ActividadEntitie>(_pAct.getApplicationContext(),R.layout.activity_item,selectedActivities));

                //Limpio el filtro (Capricho)
                filter.setText("");
            }
        });

        allAct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Decremento la cantidad
                int cantidad = get_cantidadAct();
                if(cantidad == 0){
                    Toast.makeText(_pAct,"No puede agregar mas actividades",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    cantidad--;
                }
                set_cantidadAct(cantidad);

                //Modifico las actividades seleccionadas
                List<ActividadEntitie> selectedActivities = get_selectedActivities();
                ActividadEntitie actSeleccionada = get_allActivitiesAdapter().getItem(i);
                selectedActivities.add(actSeleccionada);
                set_selectedActivities(selectedActivities);

                //Saco la actividad de la lista de todas las actividades
                allActivities.remove(actSeleccionada);


                //Seteo los adapters nuevamente
                set_allActivitiesAdapter(new ArrayAdapter<ActividadEntitie>(_pAct.getApplicationContext(), R.layout.activity_item, allActivities));
                allAct.setAdapter(_allActivitiesAdapter);
                selAct.setAdapter(new ArrayAdapter<ActividadEntitie>(_pAct.getApplicationContext(),R.layout.activity_item,selectedActivities));


                //Limpio el filtro
                filter.setText("");

            }
        });

        saveAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(_esAlta){
                    AddParcelaButtonListener lis = (AddParcelaButtonListener) _parent;
                    lis.set_actividadesSeleccionadas(get_selectedActivities());
                    dialogAct.dismiss();
                }else{
                    UpdateGridListener lis = (UpdateGridListener) _parent;
                    lis.setActividadesSeleccionadas(get_selectedActivities());
                    dialogAct.dismiss();
                }
            }
        });

        cancelAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_selectedActivities(new ArrayList<ActividadEntitie>(get_inititalSelectedActivities()));
                dialogAct.dismiss();
            }
        });

        dialogAct.show();

    }

    private List<ActividadEntitie> CargarActividadesNoSeleccionadas() {

        List<ActividadEntitie> actividades = _pAct.getController().CargarActividades();

        for(ActividadEntitie a : get_inititalSelectedActivities()){
            actividades.remove(a);
        }

        return actividades;

    }

    private int getCantidadActividadesAdd() {

        AddParcelaButtonListener parent = (AddParcelaButtonListener) _parent;
        int industrias = (parent.getCantIndustrias() == null || parent.getCantIndustrias().getText().toString().trim().length() == 0) ?
                0 : Integer.parseInt(parent.getCantIndustrias().getText().toString());

        int comercios = (parent.getCantComercios() == null || parent.getCantComercios().getText().toString().trim().length() == 0) ?
                0 : Integer.parseInt(parent.getCantComercios().getText().toString());

        return industrias + comercios;

    }

    private int getCantidadActividadesUpdate() {

        UpdateGridListener parent = (UpdateGridListener) _parent;
        int industrias = (parent.getCantIndustrias() == null || parent.getCantIndustrias().getText().toString().trim().length() == 0) ?
                0 : Integer.parseInt(parent.getCantIndustrias().getText().toString());

        int comercios = (parent.getCantComercios() == null || parent.getCantComercios().getText().toString().trim().length() == 0) ?
                0 : Integer.parseInt(parent.getCantComercios().getText().toString());

        return industrias + comercios;

    }

}

