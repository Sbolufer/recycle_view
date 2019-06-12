package esmyfirstandroidproject.covalco.recycle_view_example;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;

import java.util.ArrayList;



public class CrudActivity extends Activity implements View.OnClickListener,
                                                      AdapterView.OnItemSelectedListener {

    private Button btnCrear;
    private Button btnVer;
    private Button btnEliminar;
    private EditText editNombre;
    private EditText editComentario;
    private EditText txtNombre;
    private EditText txtComentario;

    private Spinner spinComentarios;
    private ArrayAdapter spinnerAdapter;

    private ArrayList<Comentario> lista;
    private Comentario c;

    private MyOpenHelper db;

    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        //Instanciar l'estil b'asic de validacions
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //recuperem els controls als que volem accedir
        editNombre      = (EditText) findViewById(R.id.editNombre);
        editComentario  = (EditText) findViewById(R.id.editComentario);
        txtNombre       = (EditText) findViewById(R.id.txtNombre);
        txtComentario   = (EditText) findViewById(R.id.txtComentario);

        //DESCATIVEM CASELLES
        txtNombre.setEnabled(false);
        txtComentario.setEnabled(false);

        btnCrear        = (Button)findViewById(R.id.btnCrear);
        btnVer          = (Button)findViewById(R.id.btnVer);
        btnEliminar     = (Button)findViewById(R.id.btnEliminar);

        btnCrear.setOnClickListener(this);
        btnVer.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

        // Iniciem controlador
        db=new MyOpenHelper(this);

        //Iniciem spinner y llista de comentaris
        spinComentarios=(Spinner) findViewById(R.id.spinComentarios);
        lista=db.getComments();

        //Creem adapter i l'associem al spinner
        spinnerAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, lista);
        spinComentarios.setAdapter(spinnerAdapter);
        spinComentarios.setOnItemSelectedListener(this);

        addValidationToView();
    }

    @Override
    public void onClick(View view) {
        //Per cada botó
        switch (view.getId()){
            case R.id.btnCrear:
                if (awesomeValidation.validate()){
                    //Insertar elemento bd
                    db.insertar(editNombre.getText().toString(),editComentario.getText().toString());
                    //Actualitzar llista de comentaris
                    lista=db.getComments();
                    //actuaitzam adapter i associem al spinner
                    spinnerAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, lista);
                    spinComentarios.setAdapter(spinnerAdapter);

                    Toast.makeText(this, "Form Ok!", Toast.LENGTH_LONG).show();

                    editNombre.setText("");
                    editComentario.setText("");
                }
                break;
            case R.id.btnVer:
                awesomeValidation.clear();
                // esborrem comentaris seleccionats si es el cas
                if (c!=null){
                    txtNombre.setText(c.getNombre());
                    txtComentario.setText(c.getComentario());
                }
                break;
            case R.id.btnEliminar:
                awesomeValidation.clear();
                // esborrem comentaris seleccionats si es el cas
                if (c!=null){
                    db.borrar(c.getId());
                    lista = db.getComments();
                    spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, lista);
                    spinComentarios.setAdapter(spinnerAdapter);
                    //netejem dades panell inferior
                    txtNombre.setText("");
                    txtComentario.setText("");
                    c=null;
                }
                break;
        }

    }

    private void addValidationToView(){
        awesomeValidation.addValidation(this, R.id.editNombre,RegexTemplate.NOT_EMPTY, R.string.invalid_nombre);
        awesomeValidation.addValidation(this, R.id.editComentario,RegexTemplate.NOT_EMPTY, R.string.invalid_comentario);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.spinComentarios){
            if (lista.size()>0){
              c=lista.get(i);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
