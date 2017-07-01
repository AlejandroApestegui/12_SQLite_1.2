package apestegui.alejandro.com.a12_sqlite_12;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import adapter.CategoriaAdapter;
import dao.CategoriaDAO;
import dao.ProductoDAO;
import models.Categoria;
import models.Producto;

public class ProductoMantenimiento extends AppCompatActivity {

    private Spinner spCategoria;
    private EditText etDescripcion, etPrecio, etStock;
    private Button btnEliminar;
    private View.OnClickListener btnEliminarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            new ProductoDAO(ProductoMantenimiento.this).eliminar(getIntent().getIntExtra("id", 0));
            setResult(RESULT_OK, intent);
            finish();
        }
    };
    private View.OnClickListener btnGuardarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!validar()) {
                return;
            }

            Producto producto = new Producto();

            producto.setDescripcion(etDescripcion.getText().toString().trim());
            producto.setStock(Integer.parseInt(etStock.getText().toString().trim()));
            producto.setPrecio(Double.parseDouble(etPrecio.getText().toString().trim()));
            producto.setCategoria(((Categoria) (spCategoria.getSelectedItem())).getId());

            if (getIntent().getIntExtra("id", 0) == 0) {
                new ProductoDAO(ProductoMantenimiento.this).ingresar(producto);
            } else {
                producto.setId(getIntent().getIntExtra("id", 0));
                new ProductoDAO(ProductoMantenimiento.this).actualizar(producto);
            }

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    private boolean validar() {

        boolean valida = true;
        String mensaje = "Ingresar : ";

        if (TextUtils.isEmpty(etDescripcion.getText())) {
            mensaje = mensaje + "descripción ";
            valida = false;
        }
        if (TextUtils.isEmpty(etStock.getText())) {
            mensaje = mensaje + "stock ";
            valida = false;
        }
        if (TextUtils.isEmpty(etPrecio.getText())) {
            mensaje = mensaje + "precio ";
            valida = false;
        }


        if (!valida) {
            toast(mensaje);
        }
        return valida;
    }

    public void toast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_mantenimiento);

        spCategoria = (Spinner) findViewById(R.id.spCategoria);

        CategoriaAdapter categoriaAdapter = new CategoriaAdapter(ProductoMantenimiento.this);
        spCategoria.setAdapter(categoriaAdapter);
        categoriaAdapter.addAll(new CategoriaDAO(ProductoMantenimiento.this).listar());

        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etPrecio = (EditText) findViewById(R.id.etPrecio);
        etStock = (EditText) findViewById(R.id.etStock);

        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        findViewById(R.id.btnGuardar).setOnClickListener(btnGuardarOnClickListener);


        if (getIntent().getIntExtra("id", 0) == 0) {
            btnEliminar.setVisibility(View.INVISIBLE);
            return;
        }

        btnEliminar.setOnClickListener(btnEliminarOnClickListener);

        Producto producto = new ProductoDAO(ProductoMantenimiento.this).obtenerPorId(getIntent().getIntExtra("id", 0));

        etDescripcion.setText(producto.getDescripcion());
        etPrecio.setText("" + producto.getPrecio());
        etStock.setText("" + producto.getStock());

        for (int i = 0; i < categoriaAdapter.getCount(); i++) {

            if (String.valueOf(categoriaAdapter.getItem(i).getId()).equals(String.valueOf(producto.getCategoria()))) {
                spCategoria.setSelection(i);
                break;
            }
        }

    }
}
