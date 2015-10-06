package com.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.os.Environment;

import com.model.bussinesEntities.ActividadEntitie;
import com.model.bussinesEntities.CalleEntitie;
import com.model.bussinesEntities.CategoriaEntitie;
import com.model.bussinesEntities.DatosParcelaEntitie;
import com.model.bussinesEntities.ParcelaEntitie;

public class ReadFile {

	public static ArrayList<CalleEntitie> LeerArchivoCalles(String file){
		
		ArrayList<CalleEntitie> res = null;
		
		try{
			
			FileInputStream fstream = new FileInputStream(Environment.getExternalStorageDirectory() + file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
		
			CalleEntitie c;
			String[] calleArr;
			res = new ArrayList<CalleEntitie>();
			
			while ((strLine = br.readLine()) != null)   {
			
				c = new CalleEntitie();	
				
				calleArr = strLine.split("\\s*" + "@" + "\\s*");
				
				if(calleArr.length < 2) continue;
				
				c.set_calle(calleArr[1]);
				c.set_codigoCalle(calleArr[0]);
				
				res.add(c);
			}
			br.close();
			
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		
		return res;
		
	}
	
	public static ArrayList<CategoriaEntitie> LeerArchivoCategoria(String file){
		
		ArrayList<CategoriaEntitie> res = null;
		
		try{
			
			FileInputStream fstream = new FileInputStream(Environment.getExternalStorageDirectory() + file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
		
			CategoriaEntitie c;
			String[] categoriaArr;
			res = new ArrayList<CategoriaEntitie>();
			
			while ((strLine = br.readLine()) != null)   {
			
				c = new CategoriaEntitie();	
				
				categoriaArr = strLine.split("\\s*" + "@" + "\\s*");
				
				if(categoriaArr.length < 2) continue;
				
				c.set_codCategoria(categoriaArr[0]);
				c.set_descCategoria(categoriaArr[1]);
				
				res.add(c);
			}
			br.close();
			
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		
		return res;
		
	}

	public static ArrayList<ActividadEntitie> LeerArchivoActividades(String file){
		
		ArrayList<ActividadEntitie> res = null;
		
		try{
			
			FileInputStream fstream = new FileInputStream(Environment.getExternalStorageDirectory() + file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
		
			ActividadEntitie c;
			String[] actividadesArr;
			res = new ArrayList<ActividadEntitie>();
			
			while ((strLine = br.readLine()) != null)   {
			
				c = new ActividadEntitie();	
				
				actividadesArr = strLine.split("\\s*" + "@" + "\\s*");
				
				if(actividadesArr.length < 2) continue;
				
				c.set_descripcionActividad(actividadesArr[1]);
				c.set_codigoActividad(actividadesArr[0]);
				
				res.add(c);
			}
			
			br.close();
			
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		
		return res;
		
	}
	
	public static ArrayList <ParcelaEntitie> LeerArchivoParcela(String file){
	
		ArrayList<ParcelaEntitie> res = null;
		
		try{
			
			FileInputStream fstream = new FileInputStream(Environment.getExternalStorageDirectory() + file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			String[] nomenclatura;
			
			res = new ArrayList<ParcelaEntitie>();
			ParcelaEntitie p;
			
			String circsec,circ,secc,tipoyManzana,tipo,manzana,parcela;
			
			while ((strLine = br.readLine()) != null)   {
				
				p = new ParcelaEntitie();
				
				nomenclatura = strLine.split("\\s*" + " " + "\\s*");
				
				
				if(nomenclatura.length != 3) continue;
				
				//Seteo la circunscripcion y la seccion;
				circsec = nomenclatura[0];
				
				if(circsec.length() != 3) continue;
				
				circ = circsec.substring(0,2);
				secc = circsec.substring(2);
				
				if("01".equals(circ)) p.set_circunscripcion("I");
				else if ("02".equals(circ)) p.set_circunscripcion("II");
				else continue;
				
				p.set_seccion(secc);
				
				//Seteo la manzana y el tipo de manzana
				
				tipoyManzana = nomenclatura[1];
				
				if(tipoyManzana.length() < 2) continue;
				
				tipo = tipoyManzana.substring(0, 1);
				manzana = tipoyManzana.substring(1);
				
				p.set_tipoManz(tipo);
				p.set_manzOFracc(manzana);
				
				//Seteo la parcela
				
				parcela = nomenclatura[2];
				
				p.set_parcela(parcela);
				
				res.add(p);
			}
			
			//Close the input stream
			in.close();
			  
	    }catch (Exception e){//Catch exception if any
	    	System.err.println("Error: " + e.getMessage());
	    }
		
		return res;
	}
	
	/**
	 * 
	 * @param file
	 * Representacion en una linea:
	 * idcalle @ ncalle @ naltura @ nomenclatura_parcela @ codAct @ descCat @ codAct @ descAct @ observacion
	 * @param file
	 * @return
     */
	public static ArrayList <DatosParcelaEntitie> LeerArchivoDatosParcela(String file){
		
		ArrayList<DatosParcelaEntitie> res = null;
		
		try{
			
			FileInputStream fstream = new FileInputStream(Environment.getExternalStorageDirectory() + file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			String[] datos;
			
			res = new ArrayList<DatosParcelaEntitie>();
			DatosParcelaEntitie p;
			
			CalleEntitie calle;
			ParcelaEntitie parcela;
			String altura,obser;
			
			while ((strLine = br.readLine()) != null)   {
				
				p = new DatosParcelaEntitie();
				
				datos = strLine.split("@");
				
				
				if(datos.length != 9) continue;
				
				//Seteo la calle;
				
				calle = new CalleEntitie(datos[0].trim(), datos[1].trim());
				altura = datos[2].trim();
				
				parcela = FormatearParcela(datos[3].trim());
				
				obser = datos[8].trim();

				p.set_altura(altura);
				p.set_calle(calle);
				p.set_nomenclatura(parcela);
				p.set_observaciones(obser);
				
				
				res.add(p);
			}
			
			//Close the input stream
			in.close();
			  
	    }catch (Exception e){//Catch exception if any
	    	System.err.println("Error: " + e.getMessage());
	    }
		
		return res;
	}

	private static ParcelaEntitie FormatearParcela(String parcela) {
		
		ParcelaEntitie p = null;
		
		if(parcela.length() != 0){
		
			p = new ParcelaEntitie();
			String[] nomenclatura;
			String circ,secc,circsec,tipoyManzana,tipo,manzana;
			
			nomenclatura = parcela.split("\\s*" + " " + "\\s*");
			
			
			if(nomenclatura.length != 3) return null;
			
			//Seteo la circunscripcion y la seccion;
			circsec = nomenclatura[0];
			
			if(circsec.length() != 3) return null;
			
			circ = circsec.substring(0,2);
			secc = circsec.substring(2);
			
			if("01".equals(circ)) p.set_circunscripcion("I");
			else if ("02".equals(circ)) p.set_circunscripcion("II");
			else return null;
			
			p.set_seccion(secc);
			
			//Seteo la manzana y el tipo de manzana
			
			tipoyManzana = nomenclatura[1];
			
			if(tipoyManzana.length() < 2) return null;
			
			tipo = tipoyManzana.substring(0, 1);
			manzana = tipoyManzana.substring(1);
			
			p.set_tipoManz(tipo);
			p.set_manzOFracc(manzana);
			
			//Seteo la parcela
			
			parcela = nomenclatura[2];
			
			p.set_parcela(parcela);
		}
		
		return p;
	}
}
