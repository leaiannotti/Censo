package com.utils;

/**
 * Created by liannotti on 21/08/14.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;

/**
 * @author leandroian
 *
 */
public class IOFileManager {

    /**
     * @return
     */
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param filename
     * @param textfile
     */
    public void writeFile(String filename, String textfile){
        try {
            if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
                File file = new File(Environment.getExternalStorageDirectory(), filename );
                OutputStreamWriter outw = new OutputStreamWriter(new FileOutputStream(file));
                outw.write(textfile);
                outw.close();
            }
        } catch (Exception e) {}
    }

    /**
     * @param filename
     * @return
     */
    public String readFileFromSD(String filename){
        try{
            if(isExternalStorageAvailable()){
                File file = new File(Environment.getExternalStorageDirectory(), filename);
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String t = br.readLine();

                br.close();
                return t;
            } else {return "";}
        } catch(Exception e){return "";}
    }

    /**
     * @param context
     * @param filename
     * @return
     */
    public String readFileIntern(Context context,String filename){
        try{
            String filePath = context.getFilesDir().getAbsolutePath();
            File file = new File(filePath, filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String t = br.readLine();
            br.close();
            return t;
        } catch(Exception e){return "";}
    }


    /**
     * @param filename
     * @return
     */
    public static ArrayList<String> readFileToArrayFromSd(String filename){
        ArrayList<String> stringList = new ArrayList<String>();

        try{
            if(isExternalStorageAvailable()){

                File file = new File(Environment.getExternalStorageDirectory(), filename);

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"ISO-8859-1"));
                String t;

                while ((t = br.readLine()) != null){
                    stringList.add(t.trim());
                }

                br.close();

                return stringList;
            } else {return null;}

        }catch(Exception e){

            String str = e.getMessage();
            str.toString();


            return null;
        }
    }

    /**
     * @param filename
     * @return
     */
    public static ArrayList<String> readFileToArrayInternal(Context context,String filename){
        ArrayList<String> stringList = new ArrayList<String>();

        try{
            String filePath = context.getFilesDir().getAbsolutePath();
            File file = new File(filePath, filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String t;

            while ((t = br.readLine()) != null){
                stringList.add(t.trim());
            }
            br.close();
            return stringList;
        } catch(Exception e){

            String str = e.getMessage();
            str.toString();

            return null;}
    }



}


