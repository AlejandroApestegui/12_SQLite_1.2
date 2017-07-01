package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import models.Producto;
import utils.DataBaseHelper;

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
        producto.setCategoria(cursor.isNull(4) ? 0 : cursor.getInt(4));
        producto.setDescripcionCategoria(cursor.isNull(5) ? "" : cursor.getString(5));

        return producto;
    }

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<Producto>();

        try {
            Cursor cursor = new DataBaseHelper(context).openDataBase().rawQuery("select a.id, a.descripcion, a.stock, a.precio, a.categoria, b.descripcion from producto a join categoria b on a.categoria = b.id", null);

            if (cursor.moveToFirst()) {
                do {
                    lista.add(cursorToProducto(cursor));
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;

    }

    public Producto obtenerPorId(int id) {
        Producto producto = new Producto();

        try {
            Cursor cursor = new DataBaseHelper(context).openDataBase().query(TABLE, null, COL_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.moveToFirst()) {
                producto = cursorToProducto(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producto;

    }

    public List<Producto> listarCategoria(int id) {
        List<Producto> lista = new ArrayList<Producto>();

        try {
            Cursor cursor = new DataBaseHelper(context).openDataBase().rawQuery("select a.id, a.descripcion, a.stock, a.precio, a.categoria, b.descripcion from producto a join categoria b on a.categoria = b.id where b.id = " + id, null);

            if (cursor.moveToFirst()) {
                do {
                    lista.add(cursorToProducto(cursor));
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void eliminar(int id) {
        try {
            new DataBaseHelper(context).openDataBase().delete(TABLE, COL_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {

        }
    }

    public void ingresar(Producto producto) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DESCRIPCION, producto.getDescripcion());
        contentValues.put(COL_PRECIO, producto.getPrecio());
        contentValues.put(COL_STOCK, producto.getStock());
        contentValues.put(COL_CATEGORIA, producto.getCategoria());

        try {
            new DataBaseHelper(context).openDataBase().insert(TABLE, null, contentValues);
        } catch (Exception e) {

        }
    }

    public void actualizar(Producto producto) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DESCRIPCION, producto.getDescripcion());
        contentValues.put(COL_PRECIO, producto.getPrecio());
        contentValues.put(COL_STOCK, producto.getStock());
        contentValues.put(COL_CATEGORIA, producto.getCategoria());

        try {
            new DataBaseHelper(context).openDataBase().update(TABLE, contentValues, COL_ID + " = ?", new String[]{String.valueOf(producto.getId())});
        } catch (Exception e) {

        }
    }
}
