package pt.europeia.bibloteca.models;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pt.europeia.bibloteca.models.Livro;
import pt.europeia.bibloteca.models.User;

/**
 * Created by bruno on 21/10/2016.
 * Class responsible for everything relevant to the internal database , from running SQL queries  to creating and updating the database itself in the device
 */

public class DbHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "";

    private static String DB_NAME = "teste.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    private static final int DB_Version = 1;

    private  static  SimpleDateFormat fromDB = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Constructor for the DbHelper . Since it extends the SQLiteOpenHelper we call the super() for most of the construction . Based in the SDK version the location of the file containing the database can be different
     *
     * @param context
     */
    public DbHelper(Context context) {

        super(context, DB_NAME, null, DB_Version);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    /**
     *
     * @throws IOException
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {

            // By calling this method here onUpgrade will be called on a
            // writeable database, but only if the version number has been
            // bumped
            this.getWritableDatabase();
        }
        dbExist = checkDataBase();
        if (!dbExist) {
            // By calling this method and empty database will be created into
            // the default system path of your application so we are gonna be
            // able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            Log.v("Database Upgrade", "Database version higher than old.");
        myContext.deleteDatabase(DB_NAME);

    }

    /**
     * checks the database  for a user that matches the username password combo
     * @param user username
     * @param pass password
     * @return a valid {@link User} if it finds one , or null if not
     */
    public User validateUser(String user, String pass){
        String query = "SELECT * FROM users WHERE username='"+user+"'AND pass='"+pass+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            User userlogado = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));

            return userlogado;
        }
        return null;
    }

    /**
     * retrieves form database a list of livros limited by the lim ordered by ID
     * @param lim max number of Livro to retrieve
     * @return a list of {@link Livro}
     */
    public ArrayList<Livro> getLivros(int lim){
        ArrayList<Livro> listaLivros = new ArrayList<Livro>();
        String query ="SELECT * FROM livros  LIMIT "+lim;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Date data= null;
                try {
                    data = fromDB.parse(cursor.getString(6));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Livro liv = new Livro(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),data);
                listaLivros.add(liv);
            } while (cursor.moveToNext());
        }
        return  listaLivros;
    }

    /**
     * retrieves form database a list of livros  based in the parameters
     * @param tipo type of search ( titulo, editora,etc)
     * @param procura String with the search
     * @return a list of {@link Livro}
     */
    public ArrayList<Livro> getLivrosProcuraSimples(String tipo, String procura){
        ArrayList<Livro> listaLivros = new ArrayList<Livro>();
        String query ="SELECT * FROM livros WHERE "+tipo+" LIKE'"+procura+"%' ORDER BY data";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Date data= null;
                try {
                    data = fromDB.parse(cursor.getString(6));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Livro liv = new Livro(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),data);
                listaLivros.add(liv);
            } while (cursor.moveToNext());
        }

        return listaLivros;
    }











}
