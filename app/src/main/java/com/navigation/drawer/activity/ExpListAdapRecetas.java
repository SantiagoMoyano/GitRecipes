package com.navigation.drawer.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpListAdapRecetas extends ArrayAdapter<Receta> {
    private Context context;
    private ArrayList<Receta> datos;

    public ExpListAdapRecetas(Context context, ArrayList<Receta> datos) {
        super(context, R.layout.lista_receta_item, datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.lista_receta_item, parent, false);
        /*ImageButton imagen = (ImageButton) item.findViewById(R.id.imagen_mis_recetas);
        InputStream is = datos.get(position).getPath();
        BufferedInputStream bis = new BufferedInputStream(is);
        Bitmap bitmap = BitmapFactory.decodeStream(bis);
        imagen.setImageBitmap(bitmap);*/

        TextView titulo = (TextView) item.findViewById(R.id.titulo_mis_recetas);
        titulo.setText(datos.get(position).getReceta());

        TextView descripcion = (TextView) item.findViewById(R.id.descripcion_mis_recetas);
        descripcion.setText(datos.get(position).getDescripcion());

        return item;
    }

}