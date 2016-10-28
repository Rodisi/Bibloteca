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

public class MainActivity extends AppCompatActivity {

    EditText email ;
    EditText password ;
    Button login ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email =(EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);
        login =(Button) findViewById(R.id.login);
    }

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
