package dao;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import utils.DataBaseHelper;
import models.Categoria;

/**
 * Created by Alejandro on 01/07/2017.
 */

public class CategoriaDAO {

    private String TABLE = "CATEGORIA";
    private String COL_ID = "ID";
    private String COL_DESCRIPCION = "DESCRIPCION";
    private Context context;

    public CategoriaDAO(Context context) {
        this.context = context;
    }

    public Categoria cursorToCategoria(Cursor cursor) {
        Categoria categoria = new Categoria();

        categoria.setId(cursor.isNull(cursor.getColumnIndex(COL_ID)) ? 0 : cursor.getInt(cursor.getColumnIndex(COL_ID)));
        categoria.setDescripcion(cursor.isNull(cursor.getColumnIndex(COL_DESCRIPCION)) ? "" : cursor.getString(cursor.getColumnIndex(COL_DESCRIPCION)));

        return categoria;
    }

    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<Categoria>();

        try {
            Cursor cursor = new DataBaseHelper(context).openDataBase().query(TABLE, null, null, null, null, null,null, null);
            if(cursor.moveToFirst()){
                do{
                    lista.add(cursorToCategoria(cursor));
                }
                while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;

    }
}
