package dao;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import utils.DataBaseHelper;
import models.Producto;

/**
 * Created by Alejandro on 01/07/2017.
 */

public class ProductoDAO {

    private String TABLE = "PRODUCTO";
    private String COL_ID = "ID";
    private String COL_DESCRIPCION = "DESCRIPCION";
    private String COL_STOCK = "STOCK";
    private String COL_PRECIO = "PRECIO";
    private String COL_CATEGORIA = "CATEGORIA";
    private Context context;

    public ProductoDAO(Context context) {
        this.context = context;
    }

    public Producto cursorToProducto(Cursor cursor) {
        Producto producto = new Producto();

        producto.setId(cursor.isNull(0) ? 0 : cursor.getInt(0));
        producto.setDescripcion(cursor.isNull(1) ? "" : cursor.getString(1));
        producto.setStock(cursor.isNull(2) ? 0 : cursor.getInt(2));
        producto.setPrecio(cursor.isNull(3) ? 0 : cursor.getDouble(3));
        producto.setCategoria(cursor.isNull(4)?0:cursor.getInt(4));
        producto.setDescripcionCategoria(cursor.isNull(5)?"":cursor.getString(5));

        return producto;
    }

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<Producto>();

        try {
            //Cursor cursor = new DataBaseHelper(context).openDataBase().query(TABLE, null, null, null, null, null,null, null);
            Cursor cursor = new DataBaseHelper(context).openDataBase().rawQuery("select a.id, a.descripcion, a.stock, a.precio, a.categoria, b.descripcion from producto a join categoria b on a.categoria = b.id", null);

            if(cursor.moveToFirst()){
                do{
                    lista.add(cursorToProducto(cursor));
                }
                while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;

    }

    public List<Producto> listarCategoria(int id) {
        List<Producto> lista = new ArrayList<Producto>();

        try {
            //Cursor cursor = new DataBaseHelper(context).openDataBase().query(TABLE, null, null, null, null, null,null, null);
            Cursor cursor = new DataBaseHelper(context).openDataBase().rawQuery("select a.id, a.descripcion, a.stock, a.precio, a.categoria, b.descripcion from producto a join categoria b on a.categoria = b.id where b.id = "+id, null);

            if(cursor.moveToFirst()){
                do{
                    lista.add(cursorToProducto(cursor));
                }
                while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
