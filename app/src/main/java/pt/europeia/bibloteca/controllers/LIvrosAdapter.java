package pt.europeia.bibloteca.controllers;

import android.content.Context;
import android.content.Intent;
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
 * Adapter created for the RecyclerView  in {@link PaginaPrincipal}. Created a separated class because it could be used by  an other RecyclerView that needs to display {@link Livro}
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

    /**
     * Constructor . Receives a List of {@link Livro} nad the Context
     * @param listaLivros LIst of {@link Livro} to feed to the Adapter
     * @param context Context
     */
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

    /**
     * data for each item from the RecyclerView. puts each field from the Livro at the relevant View. Also sets a new OnClickListener in the titulo that receives the Livro at the position and starts the Activity {@link LivroDetalhe}
     * putting in the intent the Livro to feed to that activity
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txttitulo.setText(listaLivros.get(position).getTitulo());
        holder.txtautor.setText(listaLivros.get(position).getAutor());

        Bitmap bitmap = getBitmapFromAsset(listaLivros.get(position).getCapa());

        holder.imgcapa.setImageBitmap(bitmap);

        holder.txttitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Livro livro = listaLivros.get(position);
                Intent myIntent = new Intent(context, LivroDetalhe.class);
                myIntent.putExtra("livro", livro);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaLivros.size();
    }


    /**
     * Slighty different form the same named method at {@link LivroDetalhe}because of Context used, but does the same thing, fetches a Bitmap object with the image from assets based on the String it receives
     * @param strName filename of the image
     * @return a Bitmap object with the image
     */
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

    /**
     * Method responsible for updating the Adapter . Receives a new List of {@link Livro} and changes the list associated to the adapter  to the new one. Then it notifies the Adapter that the dataset was updated.
     * @param data the new list of {@link Livro} to feed to the Adapter
     */
    public void updateList(List<Livro> data) {
        listaLivros = data;
        notifyDataSetChanged();
    }



}



