package com.controller.ActivityControllers;

import android.content.Context;

import com.censoMain.constantes.Constants;
import com.controller.DbHelpers.UsuariosDBHelper;

/*
 * Controller de la clase para supervisar el login del usuario.
 */
public class LoginController {

	UsuariosDBHelper dbHelper;
    Context myContext;


    private Long usrId;

    public Long getUsrId() {
        return usrId;
    }

    public void setUsrId(Long usrId) {
        this.usrId = usrId;
    }

    public LoginController(Context con){
		myContext = con;
        dbHelper = new UsuariosDBHelper(con);
        dbHelper.open();
	}
	
	public int loginUser(String user, String pass) {
		
		/*
		if(user.isEmpty())
			return Constants.ERROR_USUARIO_VACIO;
		
		if(pass.isEmpty())
			return Constants.ERROR_PASSWORD_VACIO;
		
		UsuarioEntitie userEnt = dbHelper.loadUsuarioByUser(user,null);
		
		if(userEnt == null)
			return Constants.ERROR_USUARIO_NO_ENCONTRADO;
		
		if(!pass.equals(userEnt.get_password()))
			return Constants.ERROR_PASSWORD_NO_COINCIDE;

        this.setUsrId(userEnt.get_id());
        */

        return Constants.ERROR_OK;
		
	}

}
