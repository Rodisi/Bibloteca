package pt.europeia.bibloteca.controllers;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pt.europeia.bibloteca.R;
import pt.europeia.bibloteca.models.Livro;

/**
 * This activity shows to the user the details from the book selected in the Activity  {@link PaginaPrincipal PaginaPrincipal}
 * The Activity gets the extra Serializable  {@link Livro} from the intent and outputs to the revelant Views
 */
public class LivroDetalhe extends AppCompatActivity {

    public TextView txttitulo;
    public TextView txtautor;
    public TextView txteditora;
    public TextView txtisbn;
    public TextView txtdata;
    public ImageView imgcapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_detalhe);
        Livro livro = (Livro) getIntent().getSerializableExtra("livro");
        txttitulo=(TextView) findViewById(R.id.txttitulo);
        txtautor=(TextView) findViewById(R.id.txtautor);
        txteditora=(TextView) findViewById(R.id.txteditora);
        txtisbn=(TextView) findViewById(R.id.txtisbn);
        txtdata=(TextView) findViewById(R.id.txtdata);
        imgcapa=(ImageView) findViewById(R.id.capa);

        txttitulo.setText(livro.getTitulo());
        txtautor.setText("Autor: "+ livro.getAutor());
        txteditora.setText("Editora : "+ livro.getEditora());
        txtisbn.setText("ISBN: "+ livro.getIsbn());
        long millis = livro.getData() * 1000;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String  date = sdf.format(millis);

        txtdata.setText("Data; " + date);
        Bitmap bp = getBitmapFromAsset(livro.getCapa());

        imgcapa.setImageBitmap(bp);




    }

    /**
     * This class receives a String containing the image file name , fetch it form the assets folder and returns a bitmap object with the image
     * @param strName the file name from the image
     * @return a Bitmap object containing the requested image
     */
    private Bitmap getBitmapFromAsset(String strName)
    {
        AssetManager assetManager = getAssets();
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
