package com.navigation.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class MisRecetas extends BaseActivity {
    //private SwipeRefreshLayout mSwipeRefreshLayout;
    private View parent;
    public int click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         *  We will not use setContentView in this activty
         *  Rather than we will use layout inflater to add view in FrameLayout of our base activity layout*/

        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.mis_recetas, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        //trySetupSwipeRefresh();
        hola(parent);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        this.parent=parent;
        return super.onCreateView(parent, name, context, attrs);
    }

    public ArrayList<Receta> rellenarRecetas() {
        ArrayList<Receta> recetas=new ArrayList<Receta>();
        String[][][] strRes=ParseadorXML.leerXML();
        if (strRes!=null) {
            for (int i = 0; i < strRes.length; i++) {
                Receta resAux = new Receta();
                for (int j = 0; j < strRes[i].length; j++) {
                    switch (j) {
                        case 0:
                            for (int h = 0; h < strRes[i][j].length; h++) {
                                switch (h) {
                                    case 0:
                                        resAux.setReceta(strRes[i][j][h]);
                                        break;
                                    case 1:
                                        resAux.setDescripcion(strRes[i][j][h]);
                                        break;
                                    case 2:
                                        //resAux.setPath(sI);
                                        break;
                                }
                            }
                            break;
                        case 1:
                            String[] ing = new String[strRes[i][j].length];
                            for (int h = 0; h < strRes[i][j].length; h++) {
                                ing[h] = strRes[i][j][h];
                            }
                            resAux.setIngrediente(ing);
                            break;
                        case 2:
                            String[] pas = new String[strRes[i][j].length];
                            for (int h = 0; h < strRes[i][j].length; h++) {
                                pas[h] = strRes[i][j][h];
                            }
                            resAux.setPasos(pas);
                            break;
                    }
                }
                try {
                    recetas.add(resAux);
                }catch (Exception e){
                    Log.e("Exception Hola", ": " + e.toString());
                }
            }
        }
        return recetas;
    }

    public void recetaClickeada(View view){
        click=1;
        startActivity(new Intent(this, MostrarReceta.class));
    }

    public void hola(View view){
        ListView listView=(ListView) findViewById(R.id.lista_recetas);
        ArrayList<Receta> rellenada= rellenarRecetas();
        if (rellenada!=null) {
            ExpListAdapRecetas listViewAdapter = new ExpListAdapRecetas(this, rellenada);
            listView.setAdapter(listViewAdapter);
        }
    }

    /*private void trySetupSwipeRefresh() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    hola(parent);
                }
            });

            if (mSwipeRefreshLayout instanceof MultiSwipeRefreshLayout) {
                MultiSwipeRefreshLayout mswrl = (MultiSwipeRefreshLayout) mSwipeRefreshLayout;
                mswrl.setCanChildScrollUpCallback(this);
            }
        }
    }
    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return true;
    }*/
}
