package com.model.wsConnection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import com.censoMain.constantes.Constants;
import com.controller.ActivityControllers.ParcelaController;
import com.model.bussinesEntities.ActividadEntitie;
import com.model.bussinesEntities.CalleEntitie;
import com.model.bussinesEntities.DatosParcelaEntitie;
import com.model.bussinesEntities.ParcelaEntitie;
import com.utils.IOFileManager;

public class CallSoap {
	public  String SOAP_ACTION;
    public  String OPERATION_NAME;
	public  String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
	public  String SOAP_ADDRESS;

    public CallSoap() {

		
        ArrayList<String> valoresConfig = IOFileManager.readFileToArrayFromSd("Configuracion_Censo/CensoConfig.txt");

        if(valoresConfig != null){
        
	        for(String s : valoresConfig){
	
	            if(s.contains("RutaWebService"))
	                SOAP_ADDRESS = s.substring("RutaWebService :".length());
	            else if(s.contains("NombreMetodo")){
	                OPERATION_NAME = s.substring("NombreMetodo :".length());
	                SOAP_ACTION = WSDL_TARGET_NAMESPACE + OPERATION_NAME;
	            }
	        }
        }
        else{
        	
        	SOAP_ADDRESS = "http://192.168.2.106/CensoWS/Service.asmx";
        	OPERATION_NAME = "RecibirDatosParcela";
        	SOAP_ACTION = WSDL_TARGET_NAMESPACE + OPERATION_NAME;
        }

	}

	@TargetApi(9)
	public String EnviarDatosParcela(List<DatosParcelaEntitie> datos,ParcelaController controller) {

		boolean envioOk = true;

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);


		for (DatosParcelaEntitie datosParcelaEntitie : datos) {
			
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
					OPERATION_NAME);

            DatoParcelaEntitieWS dpWs = new DatoParcelaEntitieWS(datosParcelaEntitie);
            String imagen = generateImagen(datosParcelaEntitie.get_pathImagen());

            PropertyInfo pi = new PropertyInfo();
            pi.setName("datos");
            pi.setValue(dpWs);
            pi.setType(dpWs.getClass());
            request.addProperty(pi);
            request.addProperty("imagen", imagen);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;

			envelope.setOutputSoapObject(request);

			envelope.addMapping(WSDL_TARGET_NAMESPACE, "DatosParcela",DatoParcelaEntitieWS.class);
            envelope.addMapping(WSDL_TARGET_NAMESPACE, "CalleEntitie", CalleEntitie.class);
            envelope.addMapping(WSDL_TARGET_NAMESPACE, "List<ActividadEntitie>", ListActivitiesSerializableWS.class);
            envelope.addMapping(WSDL_TARGET_NAMESPACE, "ActividadEntitie", ActividadEntitie.class);
            envelope.addMapping(WSDL_TARGET_NAMESPACE, "ParcelaEntitie", ParcelaEntitie.class);


            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			httpTransport.debug = true;
			String response = null;
			try {
				httpTransport.call(SOAP_ACTION, envelope);

                response = envelope.getResponse().toString();
			} catch (Exception exception) {
				response = exception.toString();
			}
			
			if(response.equals(Constants.ENVIO_DATOS_OK)){
                controller.updateEnviado(datosParcelaEntitie);
            }else
            	envioOk = false;
		}
		
		if(envioOk) return "0";
		else return "-1";

	}


    private String generateImagen(String path_imagen){

        //Quitar cuando se arregle lo de la imagen
        //return "";

        Bitmap bm = null;
        
        if(path_imagen == null)
        	return "";
        	
        if(path_imagen.length() == 0)
        	return "";
        
        File fImagen = new File(path_imagen);
        if(!fImagen.exists()) return "";
        
        //Obtener la codificacion en array de bytes de la imagen
        try{
    		bm = BitmapFactory.decodeFile(path_imagen);
            //bm = getResizedBitmap(bm,1600,1200);
        }
        catch(OutOfMemoryError e)
        {
    	    Log.e("Error al codificar la imagen", e.getMessage());
        }
    
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        //byte[] bytarray = Base64.decode(encodedImage, Base64.DEFAULT);
        //Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
        //        bytarray.length);

        return encodedImage;
}


    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }
}