package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import apestegui.alejandro.com.a12_sqlite_12.R;
import models.Producto;

/**
 * Created by Alejandro on 01/07/2017.
 */

public class ProductoAdapter extends ArrayAdapter<Producto> {

    public ProductoAdapter(@NonNull Context context, List<Producto> lista) {
        super(context, 0, lista);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView tvDescripcion, tvCategoria, tvPrecio, tvStock;
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.producto_item, parent, false);

        Producto producto = getItem(position);

        tvDescripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);
        tvCategoria = (TextView) convertView.findViewById(R.id.tvCategoria);
        tvPrecio = (TextView) convertView.findViewById(R.id.tvPrecio);
        tvStock = (TextView) convertView.findViewById(R.id.tvStock);

        tvDescripcion.setText(producto.getDescripcion());
        tvCategoria.setText(producto.getDescripcionCategoria());
        tvPrecio.setText("S/." + producto.getPrecio());
        tvStock.setText("" + producto.getStock());

        return convertView;
    }
}
