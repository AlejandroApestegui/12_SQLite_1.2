package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import apestegui.alejandro.com.a12_sqlite_12.R;
import models.Categoria;

/**
 * Created by Alejandro on 01/07/2017.
 */

public class CategoriaAdapter extends ArrayAdapter<Categoria> {

    public CategoriaAdapter(@NonNull Context context) {
        super(context, 0, new ArrayList<Categoria>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView tvNombreCategoria;
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.categoria_item, parent, false);

        Categoria categoria = getItem(position);

        tvNombreCategoria = (TextView) convertView.findViewById(R.id.tvNombreCategoria);
        tvNombreCategoria.setText(categoria.getDescripcion().toString());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView tvNombreCategoria;
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.categoria_item, parent, false);

        Categoria categoria = getItem(position);

        tvNombreCategoria = (TextView) convertView.findViewById(R.id.tvNombreCategoria);
        tvNombreCategoria.setText(categoria.getDescripcion().toString());

        return convertView;
    }
}
