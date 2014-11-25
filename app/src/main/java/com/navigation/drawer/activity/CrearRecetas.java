package com.navigation.drawer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CrearRecetas extends BaseActivity {

    private String[] strIng=new String[0];
    private String[] strPas=new String[0];

    private ExpandableListAdap listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private InputStream sI;
    private FragmentActivity fA= new FragmentActivity();
    private FragmentManager fAM=fA.getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         *  We will not use setContentView in this activty
         *  Rather than we will use layout inflater to add view in FrameLayout of our base activity layout*/

        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.crear_recetas, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
    }

    public void insPas(View view){
        EditText pas=(EditText) findViewById(R.id.pasos);
        expListView=(ExpandableListView) findViewById(R.id.lista_pasos);
        String aux= String.valueOf(pas.getText());
        if (aux.matches("")){
            System.out.println("No puso ningun paso");
            Toast.makeText(this, "No puso ningun paso", Toast.LENGTH_SHORT).show();
        }else {
            String[] listaAux = new String[strPas.length + 1];
            for (int i = 0; i < strPas.length; i++) {
                listaAux[i] = strPas[i];
            }
            listaAux[listaAux.length - 1] = aux;
            strPas = listaAux;
            prepareListData(strPas, "Toca aca para ver los pasos");
            listAdapter = new ExpandableListAdap(this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);
        }
    }

    public void insIng(View view){
        AutoCompleteTextView ingre = (AutoCompleteTextView) findViewById(R.id.ingredientes);
        //EditText cant=(EditText) findViewById(R.id.cantidad);
        expListView=(ExpandableListView) findViewById(R.id.lista_ingredientes);
        String ingr= String.valueOf(ingre.getText());
        //String canti=String.valueOf(cant.getText());
        String aux = ingr+" ";
        //+canti;
        //La cant de Crear recetas
        /*<EditText
        android:id="@+id/cantidad"
        android:layout_marginTop="20dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:inputType="number"
        android:ems="10"
        android:hint="@string/cant_ing"
        android:layout_below="@+id/descripcion"
        android:layout_alignParentRight="true"
                />*/
        if (ingr.matches("")){
            System.out.println("No puso ingrediente");
            Toast.makeText(this, "No puso ingrediente", Toast.LENGTH_SHORT).show();
        }else{
            String[] listaAux = new String[strIng.length + 1];
            for (int i = 0; i < strIng.length; i++) {
                listaAux[i] = strIng[i];
            }
            listaAux[listaAux.length - 1] = aux;
            strIng = listaAux;
            prepareListData(strIng, "Toca aca para ver los ingredientes");
            listAdapter = new ExpandableListAdap(this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);
        }
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

    public void crear(View view){
        EditText tit=(EditText) findViewById(R.id.titulo);
        EditText desc=(EditText) findViewById(R.id.descripcion);
        String path="Hola";
        if (String.valueOf(tit.getText()).matches("")|| String.valueOf(desc.getText()).matches("")||path.matches("")){
            Toast.makeText(this, "Verifica que todos los campos esten llenos", Toast.LENGTH_SHORT).show();
        }else {
            ParseadorXML.crear(String.valueOf(tit.getText()), path, String.valueOf(desc.getText()), strIng, strPas);
            Toast.makeText(this, "El archivo se a guardado correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    public void entrar(View view){
        showSimpleList(view);
        ImageButton boton= (ImageButton) findViewById(R.id.imagen);
        //startActivity(new Intent(getApplicationContext(), MainDrawer.class));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (SimpleListDialog.position==1){
            Uri selectedImage = data.getData();
            InputStream is;
            try {
                is = getContentResolver().openInputStream(selectedImage);
                sI = is;
                BufferedInputStream bis = new BufferedInputStream(is);
                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                ImageButton boton= (ImageButton) findViewById(R.id.imagen);
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, boton.getHeight(), boton.getWidth(), true);
                boton.setImageBitmap(scaled);
            } catch (FileNotFoundException e) {}
        }else if(SimpleListDialog.position==0){
            ImageButton boton= (ImageButton) findViewById(R.id.imagen);
            Bitmap scaled = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(SimpleListDialog.name), boton.getHeight(), boton.getWidth(), true);
            boton.setImageBitmap(scaled);
            new MediaScannerConnection.MediaScannerConnectionClient() {
                private MediaScannerConnection msc = null; {
                    msc = new MediaScannerConnection(getApplicationContext(), this); msc.connect();
                }
                public void onMediaScannerConnected() {
                    msc.scanFile(SimpleListDialog.name, null);
                }
                public void onScanCompleted(String path, Uri uri) {
                    msc.disconnect();
                }
            };
             /*if (data != null) {
                 if (data.hasExtra("data")) {
                     ImageButton boton= (ImageButton) findViewById(R.id.imagen);
                     boton.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
                 }
             }*/
        }
        Toast.makeText(this, "Imagen cargada correctamente", Toast.LENGTH_SHORT).show();
    }



    public void showSimpleList(View view) {
        DialogFragment dialog = new SimpleListDialog();
        dialog.show(fAM, "dialog");
    }
}
