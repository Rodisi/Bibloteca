package pt.europeia.bibloteca.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import pt.europeia.bibloteca.R;
import pt.europeia.bibloteca.models.DbHelper;
import pt.europeia.bibloteca.models.Livro;

/**
 * Activity responsible for Main page of the app
 */

public class PaginaPrincipal extends AppCompatActivity {

    private RecyclerView livros_recycler_view;
    public LivrosAdapter livrosAdapter;
    private EditText procura;
    private Spinner tipo;
    ArrayList<Livro>listaLivros= new ArrayList<Livro>();
    DbHelper helper;


    /**
     * When the activity is created we create a adapter with the types of search for the Spinner
     * We also feed the RecyclerView a list with the newer 5 books
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        procura=(EditText) findViewById(R.id.procura);
        procura.addTextChangedListener(new procuraWatcher());

        tipo=(Spinner) findViewById(R.id.tipo);
        ArrayList<String> tipos=new ArrayList<String>();
        tipos.add("titulo");
        tipos.add("autor");
        tipos.add("editora");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(dataAdapter);




        livros_recycler_view= (RecyclerView) findViewById(R.id.listalivrosnovos);
        helper = new DbHelper(getApplicationContext());
        listaLivros=helper.getLivros(5);

        livrosAdapter =new LivrosAdapter(listaLivros,getApplicationContext());
        livros_recycler_view.setAdapter(livrosAdapter);
        livros_recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * TextWatcher responsible for the search. When you change the text the method afterTextChanged is called
     */
    private class procuraWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        /**
         * When the text of the search is changed  it gets a new list of {@link Livro} based on the search parameters and updates the Adapter
         * @param editable
         */
        @Override
        public void afterTextChanged(Editable editable) {
            String textoProcura=procura.getText().toString();
            String tipoProcura=String.valueOf(tipo.getSelectedItem());

            listaLivros=helper.getLivrosProcuraSimples(tipoProcura ,textoProcura );
            livrosAdapter.updateList(listaLivros);


        }
    }


}
