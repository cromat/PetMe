package com.example.computer.petme.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.computer.petme.modules.Oglas;

import java.util.ArrayList;

/**
 * Created by computer on 18.4.2015..
 */
public class DatabaseOglasHandler extends SQLiteOpenHelper {
    private static final String TAG = DatabaseOglasHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "bazaoglasa";

    // Login table name
    private static final String TABLE_OGLAS = "oglasi";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "naziv";
    private static final String KEY_OPIS = "opis";
    private static final String KEY_IDUSER = "iduser";
    private static final String KEY_DATUM = "datum";
    private static final String KEY_PETNAME = "petname";
    private static final String KEY_VRSTA = "vrsta";
    private static final String KEY_PASMINA = "pasmina";
    private static final String KEY_SPOL = "spol";
    private static final String KEY_VELICINA = "velicina";
    private static final String KEY_STAROST = "starost";
    private static final String KEY_BOJA = "boja";
    private static final String KEY_MOB = "mobitel";
    private static final String KEY_ZUPANIJA = "zupanija";
    private static final String KEY_MJESTO = "mjesto";

    public DatabaseOglasHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_OGLAS_TABLE = "CREATE TABLE " + TABLE_OGLAS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_OPIS + " TEXT,"
                + KEY_IDUSER + " INTEGER,"
                + KEY_DATUM + " TEXT,"
                + KEY_PETNAME + " TEXT,"
                + KEY_VRSTA + " TEXT,"
                + KEY_PASMINA + " TEXT,"
                + KEY_SPOL + " BOOLEAN,"
                + KEY_VELICINA + " TEXT,"
                + KEY_STAROST + " TEXT,"
                + KEY_BOJA + " TEXT,"
                + KEY_MOB + " TEXT,"
                + KEY_ZUPANIJA + " TEXT,"
                + KEY_MJESTO + " TEXT" + ")";
        db.execSQL(CREATE_OGLAS_TABLE);

        Log.d(TAG, "Database tables OGLASI created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_OGLAS);

        String upgradeQuery = "ALTER TABLE bazaoglasa ADD (zupanija TEXT, mjesto TEXT)";
        if (oldVersion == 1 && newVersion == 2)
            db.execSQL(upgradeQuery);

        // Create tables again
        //onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addOglas(int idOglas, String naziv, String opis, int idUser, String datum, String petname, String vrsta, String pasmina, Boolean spol,
                         String velicina, String starost, String boja, String mob, String zupanija, String mjesto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, idOglas); // id
        values.put(KEY_NAME, naziv); // name
        values.put(KEY_OPIS, opis); // opis
        values.put(KEY_IDUSER, idUser); // id user , korisnikov id
        values.put(KEY_DATUM, datum); // datum
        values.put(KEY_PETNAME, petname); // ime zivotinje, petname
        values.put(KEY_VRSTA, vrsta); // vrsta
        values.put(KEY_PASMINA, pasmina); // pasmina
        values.put(KEY_SPOL, spol); // spol
        values.put(KEY_VELICINA, velicina); // velicina
        values.put(KEY_STAROST, starost); // starost
        values.put(KEY_BOJA, boja); // boja
        values.put(KEY_MOB, mob); // broj mobitela
        values.put(KEY_ZUPANIJA, zupanija); //zupanija
        values.put(KEY_MJESTO, mjesto); //mjesto

        // Inserting Row
        long id = db.insert(TABLE_OGLAS, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite table user: " + id);
    }

    public void addOglas(Oglas oglas) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, oglas.getId());
        values.put(KEY_NAME, oglas.getNaziv()); // Name
        values.put(KEY_OPIS, oglas.getOpis()); // Username
        values.put(KEY_IDUSER, oglas.getIdUser()); // Email
        values.put(KEY_DATUM, oglas.getDatum()); // Phone
        values.put(KEY_PETNAME, oglas.getPetname()); // Name
        values.put(KEY_VRSTA, oglas.getVrsta()); // Username
        values.put(KEY_PASMINA, oglas.getPasmina()); // Email
        values.put(KEY_SPOL, oglas.getSpol()); // Phone
        values.put(KEY_VELICINA, oglas.getVelicina()); // Name
        values.put(KEY_STAROST, oglas.getStarost()); // Username
        values.put(KEY_BOJA, oglas.getBoja()); // Email
        values.put(KEY_MOB, oglas.getBrMob()); // broj mobitela
        values.put(KEY_ZUPANIJA, oglas.getZupanija()); //zupanija
        values.put(KEY_MJESTO, oglas.getMjesto()); //mjesto

        // Inserting Row
        long id = db.insert(TABLE_OGLAS, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite table user: " + id);
    }
    /**
     * Storing empty oglas details in database
     * */
    public void addEmptyOglas() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, 0);
        values.put(KEY_NAME, ""); // Name
        values.put(KEY_OPIS, ""); // Username
        values.put(KEY_IDUSER, 0); // Email
        values.put(KEY_DATUM, ""); // Datum
        values.put(KEY_PETNAME, ""); // Ime Zivotinje - Petname
        values.put(KEY_VRSTA, ""); // Vrsta - tip zivotinje... pas,macka...
        values.put(KEY_PASMINA, ""); // Pasmina
        values.put(KEY_SPOL, ""); // spol bool
        values.put(KEY_VELICINA, ""); // velicina
        values.put(KEY_STAROST, ""); // starost
        values.put(KEY_BOJA, ""); // boja
        values.put(KEY_MOB, ""); // broj mobitela
        values.put(KEY_ZUPANIJA, ""); //zupanija
        values.put(KEY_MJESTO, ""); //mjesto

        // Inserting Row
        long id = db.insert(TABLE_OGLAS, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite table user: " + id);
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<Oglas> getOglasiDetails() {
        //HashMap<String, String> user = new HashMap<String, String>();
        ArrayList<Oglas> oglasi = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_OGLAS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();

        for(int i = 0 ; i < cursor.getCount() ; i++ ){
            oglasi.add( new Oglas(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            Boolean.valueOf(String.valueOf(cursor.getInt(8))),
                            cursor.getString(9),
                            cursor.getString(10),
                            cursor.getString(11),
                            cursor.getString(12),
                            cursor.getString(13),
                            cursor.getString(14))
            );
        }
        cursor.close();
        db.close();
        // return user

        return oglasi;
    }

    public Oglas getOglasDetails() {
        //HashMap<String, String> user = new HashMap<String, String>();
        Oglas oglas = new Oglas();
        String selectQuery = "SELECT  * FROM " + TABLE_OGLAS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            oglas = new Oglas(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    Boolean.valueOf(String.valueOf(cursor.getInt(8))),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14));
        }
        cursor.close();
        db.close();
        // return oglas

        return oglas;
    }

    /**
     * Getting user login status return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_OGLAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteOglasi() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_OGLAS, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite OGLASI");
    }

    public void updateOglas(Oglas oglas) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, oglas.getId());
        values.put(KEY_NAME, oglas.getNaziv()); // Name
        values.put(KEY_OPIS, oglas.getOpis()); // Username
        values.put(KEY_IDUSER, oglas.getIdUser()); // Email
        values.put(KEY_DATUM, oglas.getDatum()); // Phone
        values.put(KEY_PETNAME, oglas.getPetname()); // Name
        values.put(KEY_VRSTA, oglas.getVrsta()); // Username
        values.put(KEY_PASMINA, oglas.getPasmina()); // Email
        values.put(KEY_SPOL, oglas.getSpol()); // Phone
        values.put(KEY_VELICINA, oglas.getVelicina()); // Name
        values.put(KEY_STAROST, oglas.getStarost()); // Username
        values.put(KEY_BOJA, oglas.getBoja()); // Email
        values.put(KEY_MOB, oglas.getBrMob()); // broj mobitela
        values.put(KEY_ZUPANIJA, oglas.getZupanija()); //zupanija
        values.put(KEY_MJESTO, oglas.getMjesto()); //mjesto

        // Inserting Row
        //long id = db.update(TABLE_OGLAS, values, KEY_ID, new String[]{String.valueOf(oglas.getId())});
        long id = db.update(TABLE_OGLAS, values, null, null);
        db.close(); // Closing database connection

        Log.d(TAG, "Oglas updated into sqlite table oglasibaze: " + id);
    }

}
