package pt.europeia.bibloteca.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import pt.europeia.bibloteca.R;
import pt.europeia.bibloteca.models.DbHelper;
import pt.europeia.bibloteca.models.Livro;

public class PaginaPrincipal extends AppCompatActivity {

    private RecyclerView livros_recycler_view;
    public LIvrosAdapter lIvrosAdapter;
    private EditText procura;
    private Spinner tipo;
    ArrayList<Livro>listaLivros= new ArrayList<Livro>();
    DbHelper helper;



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
        listaLivros=helper.getLivrosPorData(5);

        lIvrosAdapter=new LIvrosAdapter(listaLivros,getApplicationContext());
        livros_recycler_view.setAdapter(lIvrosAdapter);
        livros_recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

    private class procuraWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String textoProcura=procura.getText().toString();
            String tipoProcura=String.valueOf(tipo.getSelectedItem());

            listaLivros=helper.getLivrosProcuraSimples(tipoProcura ,textoProcura );
            lIvrosAdapter.updateList(listaLivros);


        }
    }


}
