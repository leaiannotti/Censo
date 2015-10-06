package com.censoMain.censofiscalgisB;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.censoMain.constantes.Constants;
import com.controller.ActivityControllers.LoginController;
import com.utils.IOFileManager;

public class LoginActivity extends Activity {

	public static EditText userText;
	EditText passText;
	Button accederBtn;
	Button checkDb;
	TextView registroText;
	TextView olvidoPass;
	TextView versionText;

	private LoginController controller;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		controller = new LoginController(getApplicationContext());
		userText = (EditText) findViewById(R.id.userText);
		passText = (EditText) findViewById(R.id.passwordText);
		accederBtn = (Button) findViewById(R.id.loginButton);
		olvidoPass = (TextView) findViewById(R.id.olvidoPassText);
		versionText = (TextView) findViewById(R.id.versionText);
		
		
		PackageInfo pInfo;
		String version;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			version = "";
			e.printStackTrace();
		}
		
		String nroTablet = "No especificado";
		
		ArrayList<String> valoresConfig = IOFileManager.readFileToArrayFromSd("Configuracion_Censo/CensoConfig.txt");

		if(valoresConfig != null){
			for(String s : valoresConfig){
	
		            if(s.contains("NroTablet"))
		            	nroTablet = s.substring("NroTablet :".length());
		            else 
		            	nroTablet = "0";
		        }
		
		}else
		{
			nroTablet = "0";
			
		}
        versionText.setText("Versión de la aplicación: " + version + "               Nro de tablet: " + nroTablet);
	        
		/*
		 * accederBtn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) {
		 * 
		 * Intent in = new Intent(LoginActivity.this,ParcelasActivity.class);
		 * startActivity(in);
		 * 
		 * } });
		 */

		accederBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				int error = controller.loginUser(userText.getText().toString(),
						passText.getText().toString());

				switch (error) {
				case Constants.ERROR_USUARIO_VACIO:
					userText.setBackgroundColor(3);
					Toast.makeText(getApplicationContext(),
							"Debe ingresar un usuario", Toast.LENGTH_LONG)
							.show();
					break;

				case Constants.ERROR_PASSWORD_VACIO:
					passText.setBackgroundColor(3);
					Toast.makeText(getApplicationContext(),
							"Debe ingresar el password", Toast.LENGTH_LONG)
							.show();
					break;

				case Constants.ERROR_USUARIO_NO_ENCONTRADO:
					userText.setBackgroundColor(3);
					Toast.makeText(getApplicationContext(),
							"El usuario no esta registrado en el sistema",
							Toast.LENGTH_LONG).show();
					break;

				case Constants.ERROR_PASSWORD_NO_COINCIDE:
					passText.setBackgroundColor(3);
					Toast.makeText(getApplicationContext(),
							"El password no es correcto", Toast.LENGTH_LONG)
							.show();
					break;

				case Constants.ERROR_OK:

                    //controllerPar.InicializarDatos();

					Intent in = new Intent(LoginActivity.this,ParcelasActivity.class);

                    //Bundle b = new Bundle();
                    //b.putLong("UsrId",controller.getUsrId());
					//startActivity(in,b);
					startActivity(in);
                    break;

				default:
					break;
				}
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}
