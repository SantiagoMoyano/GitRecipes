package com.navigation.drawer.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 23/11/14.
 */
public class MostrarReceta extends MisRecetas{
    private ExpandableListAdap listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.mostrar_recetas, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(1, true);
        setTitle(listArray[1]);
        String[][] receta=ParseadorXML.getRecipeById(click);
        EditText titulo=(EditText) findViewById(R.id.titulo_m);
        EditText desc=(EditText) findViewById(R.id.descripcion_m);
        ImageButton img =(ImageButton) findViewById(R.id.imagen_m);
        ExpandableListView ing=(ExpandableListView) findViewById(R.id.lista_ingredientes_m);
        ExpandableListView pas=(ExpandableListView) findViewById(R.id.lista_pasos_m);
        titulo.setText(receta[0][0]);
        desc.setText(receta[0][1]);
        //img(receta[0][2]);
        prepareListData(receta[1], "Toca aca para ver los ingradientes");
        listAdapter = new ExpandableListAdap(this, listDataHeader, listDataChild);
        ing.setAdapter(listAdapter);
        prepareListData(receta[2], "Toca aca para ver los pasos");
        listAdapter = new ExpandableListAdap(this, listDataHeader, listDataChild);
        pas.setAdapter(listAdapter);
    }

    private void prepareListData(String[] array, String str) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add(str);

        List<String> ingred = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            ingred.add(array[i]);
        }
        listDataChild.put(listDataHeader.get(0),ingred);
    }

}
