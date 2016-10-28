package pt.europeia.bibloteca.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import pt.europeia.bibloteca.R;

public class PaginaPrincipal extends AppCompatActivity {

    private RecyclerView livros_recycler_view;
    private LIvrosAdapter lIvrosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);
        livros_recycler_view= (RecyclerView) findViewById(R.id.listalivrosnovos);

        

    }
}
