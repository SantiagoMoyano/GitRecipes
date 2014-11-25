package com.navigation.drawer.activity;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by root on 15/11/14.
 */
public abstract class ParseadorXML {
    private static File sdCard = Environment.getExternalStorageDirectory();
    private static String[][][] array = new String[0][3][3];

    public static void crear(String titulo, String path, String desc, String[] ing, String[] pasos) {
        File newxmlfile = new File(sdCard.getAbsolutePath() + "/Recetas.xml");
        String[][][] auxiliar = leerXML();
        try {
            newxmlfile.createNewFile();
        } catch (IOException e) {
            Log.e("IOException", "Exception in create new File: " + e.toString());
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newxmlfile);

        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.toString());
        }

        try {
            XmlSerializer parser = Xml.newSerializer();

            parser.setOutput(fos, "UTF-8");
            parser.startDocument(null, true);

            parser.startTag("", "cant");
            parser.attribute("", "Count", String.valueOf(array.length + 1));
            parser.endTag("", "cant");


            if (auxiliar != null) {
                for (int i = 0; i < auxiliar.length; i++) {
                    parser.startTag("", "Receta");
                    parser.startTag("", "Id");
                    parser.attribute("", "Id", String.valueOf(i));
                    parser.endTag("", "Id");
                    for (int j = 0; j < auxiliar[i].length; j++) {
                        switch (j) {
                            case 0:
                                parser.startTag("", "InfoReceta");
                                for (int h = 0; h < auxiliar[i][j].length; h++) {
                                    switch (h) {
                                        case 0:
                                            parser.attribute("", "Titulo", auxiliar[i][j][h]);
                                            break;
                                        case 1:
                                            parser.attribute("", "Descripcion", auxiliar[i][j][h]);
                                            break;
                                        case 2:
                                            parser.attribute("", "Imagen", auxiliar[i][j][h]);
                                            break;
                                    }
                                }
                                parser.endTag("", "InfoReceta");
                                break;
                            case 1:
                                parser.startTag("", "Ingredientes");
                                for (int h = 0; h < auxiliar[i][j].length; h++) {
                                    parser.attribute("", "Ingrediente" + h, auxiliar[i][j][h]);
                                }
                                parser.endTag("", "Ingredientes");
                                break;
                            case 2:
                                parser.startTag("", "Pasos");
                                for (int h = 0; h < auxiliar[i][j].length; h++) {
                                    parser.attribute("", "Paso" + h, auxiliar[i][j][h]);
                                }
                                parser.endTag("", "Pasos");
                                break;
                        }
                    }
                    parser.endTag("", "Receta");
                }
            }

            parser.startTag("", "Receta");
            parser.attribute("", "Id", String.valueOf(array.length + 1));

            parser.startTag("", "InfoReceta");
            parser.attribute("", "Titulo", titulo);
            parser.attribute("", "Descripcion", desc);
            parser.attribute("", "Imagen", path);
            parser.endTag("", "InfoReceta");

            parser.startTag("", "Ingredientes");
            for (int i = 0; i < ing.length; i++) {
                parser.attribute("", "Ingrediente" + i, ing[i]);
            }
            parser.endTag("", "Ingredientes");

            parser.startTag("", "Pasos");
            for (int i = 0; i < pasos.length; i++) {
                parser.attribute("", "Paso" + i, pasos[i]);
            }
            parser.endTag("", "Pasos");

            parser.endTag("", "Receta");

            parser.endDocument();
            parser.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[][][] leerXML() {
        FileInputStream fin = null;
        File newxmlfile = new File(sdCard.getAbsolutePath() + "/Recetas.xml");

        try {
            fin = new FileInputStream(newxmlfile);
        } catch (Exception e) {
            Log.e("Exception", ": " + e.toString());
        }

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(fin, "UTF-8");

            String[][][] help = null;
            int count = 0;
            int event = parser.next();
            int countRes = 0;
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG) {
                    Log.d("Start tag", "<" + parser.getName() + ">");
                    if (parser.getName().equals("cant")) {
                        help = new String[Integer.parseInt(parser.getAttributeValue(0))][3][3];
                    }
                    if (parser.getName().equals("Receta")) {
                        System.out.println(array.length);
                        count = 0;
                    } else if (parser.getName().equals("InfoReceta") || parser.getName().equals("Ingredientes") || parser.getName().equals("Pasos")) {
                        String[] aux = new String[parser.getAttributeCount()];
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            Log.d("Atributo", "\t" + parser.getAttributeName(i) + " = " + parser.getAttributeValue(i));
                            aux[i] = parser.getAttributeValue(i);
                        }
                        help[countRes][count] = aux;
                        count += 1;
                    }

                    /*if (parser.getName().equals("Pasos")) {
                        String[] aux = new String[parser.getAttributeCount()];
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            Log.d("Atributo", "\t" + parser.getAttributeName(i) + " = " + parser.getAttributeValue(i));
                            aux[i] = parser.getAttributeValue(i);
                        }
                        help[array.length][2] = aux;
                    }
                    if(parser.getName().equals("InfoReceta")){
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            Log.d("Atributo", "\t" + parser.getAttributeName(i) + " = " + parser.getAttributeValue(i));
                            help[array.length][0][i] = parser.getAttributeValue(i);
                        }
                    }*/
                }


                if (event == XmlPullParser.TEXT && parser.getText().trim().length() != 0) {
                    Log.d("Esto texto", "\t\t" + parser.getText());
                }

                if (event == XmlPullParser.END_TAG) {
                    Log.d("End Tag", "</" + parser.getName() + ">");
                    if (parser.getName().equals("Receta")) {
                        String[][] auxHelp = help[0];
                        //                       for(int i = 0; i < array.length; i++){
                        //                          help[i]=array[i];
                        //                    }
                        //                  help[countRes]=auxHelp;
                        array = help;
                        countRes += 1;
                    }
                }

                event = parser.next();
            }
            fin.close();

            for (int i = 0; i < array[0].length; i++) {
                for (int j = 0; j < array[0][i].length; j++) {
                    System.out.println(i + " " + j + " " + array[0][i][j]);
                }
            }
            return array;
            //return help;
        } catch (Exception e) {
            Log.e("Exception", ": " + e.toString());
        }
        return null;
    }

    public static String[][] getRecipeById(int num) {
        FileInputStream fin = null;
        File newxmlfile = new File(sdCard.getAbsolutePath() + "/Recetas.xml");

        try {
            fin = new FileInputStream(newxmlfile);
        } catch (Exception e) {
            Log.e("Exception", ": " + e.toString());
        }

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(fin, "UTF-8");

            String[][] help = new String[5][0];
            boolean con = false;
            int count=0;
            int event = parser.next();
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG) {
                    Log.d("Start tag", "<" + parser.getName() + ">");
                    if (parser.getName().equals("Id")) {
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            Log.d("Atributo", "\t" + parser.getAttributeName(i) + " = " + parser.getAttributeValue(i));
                            if (parser.getAttributeValue(i).equals(String.valueOf(num))) {
                                con = true;
                            } else {
                                con = false;
                            }
                        }
                    } else if (parser.getName().equals("InfoReceta") || parser.getName().equals("Ingredientes") || parser.getName().equals("Pasos")) {
                        if (con) {
                            String[] aux = new String[parser.getAttributeCount()];
                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                Log.d("Atributo", "\t" + parser.getAttributeName(i) + " = " + parser.getAttributeValue(i));
                                aux[i] = parser.getAttributeValue(i);
                            }
                            help[count]=aux;
                        }
                    }
                }


                if (event == XmlPullParser.TEXT && parser.getText().trim().length() != 0) {
                    Log.d("Esto texto", "\t\t" + parser.getText());
                }

                if (event == XmlPullParser.END_TAG) {
                    Log.d("End Tag", "</" + parser.getName() + ">");
                }

                event = parser.next();
            }
            fin.close();
            return help;
        } catch (Exception e) {
            Log.e("Exception", ": " + e.toString());
        }
        return null;
    }

}

