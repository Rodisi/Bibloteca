package pt.europeia.bibloteca.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import pt.europeia.bibloteca.R;
import pt.europeia.bibloteca.models.DbHelper;
import pt.europeia.bibloteca.models.Livro;

public class PaginaPrincipal extends AppCompatActivity {

    private RecyclerView livros_recycler_view;
    private LIvrosAdapter lIvrosAdapter;
    ArrayList<Livro>listaLivros= new ArrayList<Livro>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);
        livros_recycler_view= (RecyclerView) findViewById(R.id.listalivrosnovos);
        DbHelper helper = new DbHelper(getApplicationContext());
        listaLivros=helper.getLivrosPorData(5);

        lIvrosAdapter=new LIvrosAdapter(listaLivros,getApplicationContext());
        livros_recycler_view.setAdapter(lIvrosAdapter);
        livros_recycler_view.setLayoutManager(new LinearLayoutManager(this));



        

    }
}
