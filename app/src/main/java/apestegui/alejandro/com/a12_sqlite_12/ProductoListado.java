package apestegui.alejandro.com.a12_sqlite_12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import adapter.CategoriaAdapter;
import adapter.ProductoAdapter;
import dao.CategoriaDAO;
import dao.ProductoDAO;
import models.Categoria;
import utils.DataBaseHelper;

public class ProductoListado extends AppCompatActivity {
    private ListView lvProducto;
    private ProductoAdapter productoAdapter;
    private CategoriaAdapter categoriaAdapter;
    private AdapterView.OnItemClickListener lvProductoOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };
    private Spinner spFiltro;
    private AdapterView.OnItemSelectedListener spFiltroOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            listarProducto();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_listado);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(ProductoListado.this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        spFiltro = (Spinner) findViewById(R.id.spFiltro);
        lvProducto = (ListView)findViewById(R.id.lvProductos);


        categoriaAdapter = new CategoriaAdapter(ProductoListado.this);
        spFiltro.setAdapter(categoriaAdapter);
        spFiltro.setOnItemSelectedListener(spFiltroOnItemSelectedListener);
        Categoria categoria = new Categoria();
        categoria.setId(0);
        categoria.setDescripcion("--Todos--");
        categoriaAdapter.add(categoria);
        categoriaAdapter.addAll(new CategoriaDAO(ProductoListado.this).listar());

        productoAdapter = new ProductoAdapter(ProductoListado.this, new ProductoDAO(ProductoListado.this).listar());
        lvProducto.setAdapter(productoAdapter);

        //listarProducto();
    }

    public void toast(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void listarProducto() {
        toast(""+((Categoria)spFiltro.getSelectedItem()).getId());
        productoAdapter.clear();
        if (((Categoria)spFiltro.getSelectedItem()).getId()==0)
            productoAdapter.addAll(new ProductoDAO(ProductoListado.this).listar());
        else
            productoAdapter.addAll(new ProductoDAO(ProductoListado.this).listarCategoria(((Categoria)spFiltro.getSelectedItem()).getId()));
    }
}
