package pt.europeia.bibloteca.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pt.europeia.bibloteca.R;
import pt.europeia.bibloteca.models.DbHelper;
import pt.europeia.bibloteca.models.Livro;
import pt.europeia.bibloteca.models.LivroRecomendado;
import pt.europeia.bibloteca.models.User;

/**
 * This activity shows to the user the details from the book selected in the Activity  {@link PaginaPrincipal PaginaPrincipal}
 * The Activity gets the extra Serializable  {@link Livro} from the intent and outputs to the relevant Views
 */
public class LivroDetalhe extends AppCompatActivity {

    private TextView txttitulo;
    private TextView txtautor;
    private TextView txteditora;
    private TextView txtisbn;
    private TextView txtdata;
    private ImageView imgcapa;
    private  Livro livro;
    private User useratual;
    String user;

    /**
     * Runs when the activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_detalhe);
        useratual = (User) getIntent().getSerializableExtra("userlogado");
        livro = (Livro) getIntent().getSerializableExtra("livro");
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

        SimpleDateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtdata.setText("Data; " + userDateFormat.format(livro.getData()));
        Bitmap bp = getBitmapFromAsset(livro.getCapa());

        imgcapa.setImageBitmap(bp);




    }

    /**
     * This class receives a String containing the image file name , fetch it form the assets folder and returns a Bitmap object with the image
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

    /**
     * Share the details of the book and let the user choose how to share it (wich app)
     * @param v a
     */
    public void share(View v) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareText = livro.getTitulo() +" de " + livro.getAutor() + " da editora " +livro.getEditora();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, livro.getTitulo());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(sharingIntent, "Partilhar via"));
    }

    /**
     * Recommend the current book
     * @param v
     */
    public void partilhar(View v) {
        user="";
        final DbHelper helper=new DbHelper(getApplicationContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insira username");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user = input.getText().toString();
                if(helper.recomendarLivro(useratual,user,livro)){
                    Toast.makeText(getApplicationContext(), "Partilhado",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Erro",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}
