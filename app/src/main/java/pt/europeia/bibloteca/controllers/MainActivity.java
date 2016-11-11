package pt.europeia.bibloteca.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import pt.europeia.bibloteca.models.DbHelper;
import pt.europeia.bibloteca.R;
import pt.europeia.bibloteca.models.User;

/**
 * staring Acctivity . responsible for the login
 */
public class MainActivity extends AppCompatActivity {

    private EditText email ;
    private EditText password ;
    private Button login ;

    /**
     * Runs when the activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email =(EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);
        login =(Button) findViewById(R.id.login);
    }

    /**
     * OnClick event fot the button login
     * uses the method validateUser to check the database for a valid username and password combination . if the method returns a valid User it displays a welcome message and goes to {@link PaginaPrincipal}
     * if not it display a error message
     * @param v in this case the login Button
     */
    public void login (View v){
        Context context = getApplicationContext();

        String user = String.valueOf(email.getText());
        String pass = String.valueOf(password.getText());

        DbHelper helper = new DbHelper(getApplicationContext());
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User logado=helper.validateUser(user,pass);
        if(logado!=null){
            Toast toast = Toast.makeText(context, "Bem vindo "+logado.getNome(), Toast.LENGTH_LONG);
            toast.show();
            Intent myIntent = new Intent(this, PaginaPrincipal.class);
            myIntent.putExtra("userLogado",logado);
            startActivity(myIntent);
        }else{
            Toast toast = Toast.makeText(context, "Login Incorreto", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

}
