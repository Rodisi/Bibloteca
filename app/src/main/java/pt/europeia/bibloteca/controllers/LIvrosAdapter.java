package pt.europeia.bibloteca.controllers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import pt.europeia.bibloteca.R;
import pt.europeia.bibloteca.models.Livro;

import static java.security.AccessController.getContext;

/**
 * Created by bruno on 28/10/2016.
 */

public class LIvrosAdapter extends RecyclerView.Adapter<LIvrosAdapter.MyViewHolder>{
    private List<Livro> listaLivros;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txttitulo;
        public TextView txtautor;
        public ImageView imgcapa;



        public MyViewHolder(View view) {
            super(view);
            txttitulo = (TextView) view.findViewById(R.id.txttitulo);
            txtautor = (TextView) view.findViewById(R.id.txtautor);
            imgcapa = (ImageView) view.findViewById(R.id.imgcapa);
        }
    }


    public LIvrosAdapter(List<Livro> listaLivros , Context context) {
        this.listaLivros = listaLivros;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txttitulo.setText(listaLivros.get(position).getTitulo());
        holder.txtautor.setText(listaLivros.get(position).getAutor());

        Bitmap bitmap = getBitmapFromAsset(listaLivros.get(position).getCapa());

        holder.imgcapa.setImageBitmap(bitmap);

        holder.txttitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaLivros.size();
    }

    private Bitmap getBitmapFromAsset(String strName)
    {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
}



