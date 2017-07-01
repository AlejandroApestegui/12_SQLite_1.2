package apestegui.alejandro.com.a12_sqlite_12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import adapter.CategoriaAdapter;
import dao.CategoriaDAO;

public class ProductoMantenimiento extends AppCompatActivity {

    private Spinner spCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_mantenimiento);

        spCategoria = (Spinner) findViewById(R.id.spCategoria);

        CategoriaAdapter categoriaAdapter = new CategoriaAdapter(ProductoMantenimiento.this);
        spCategoria.setAdapter(categoriaAdapter);
        categoriaAdapter.addAll(new CategoriaDAO(ProductoMantenimiento.this).listar());
    }
}
