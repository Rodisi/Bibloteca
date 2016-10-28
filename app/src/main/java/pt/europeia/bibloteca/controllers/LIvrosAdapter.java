package pt.europeia.bibloteca.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pt.europeia.bibloteca.R;
import pt.europeia.bibloteca.models.Livro;

/**
 * Created by bruno on 28/10/2016.
 */

public class LIvrosAdapter extends RecyclerView.Adapter<LIvrosAdapter.MyViewHolder>{
    private List<Livro> listaLivros;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtView;

        public MyViewHolder(View view) {
            super(view);
            txtView = (TextView) view.findViewById(R.id.txtView);

        }
    }


    public LIvrosAdapter(List<Livro> listaLivros) {
        this.listaLivros = listaLivros;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtView.setText(listaLivros.get(position).getTitulo());

        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaLivros.size();
    }
}



