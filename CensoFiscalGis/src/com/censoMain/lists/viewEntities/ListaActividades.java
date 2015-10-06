package com.censoMain.lists.viewEntities;

import com.model.bussinesEntities.ActividadEntitie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liannotti on 19/07/14.
 */
public class ListaActividades implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> innerAct;

    public void convertToListString (List<ActividadEntitie> actividades){

        if(actividades == null){
            innerAct = new ArrayList<String>();
            return;
        }

        List<String> acts = new ArrayList<String>();

        for(ActividadEntitie a : actividades)
        {
            acts.add(a.toString());
            this.innerAct = acts;
        }
    }

    public ListaActividades(){}

    public ListaActividades(List<String> acts){
        this.innerAct = acts;
    }
    public List<String> getList()
    {
        return this.innerAct;
    }


}
