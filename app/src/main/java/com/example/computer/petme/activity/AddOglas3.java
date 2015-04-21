package com.example.computer.petme.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.computer.petme.R;
import com.example.computer.petme.app.AppConfig;
import com.example.computer.petme.app.AppController;
import com.example.computer.petme.helper.DatabaseOglasHandler;
import com.example.computer.petme.helper.DatabaseUserHandler;
import com.example.computer.petme.modules.Oglas;
import com.example.computer.petme.modules.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddOglas3 extends ActionBarActivity {

    private static final String TAG = AddOglas3.class.getSimpleName();

    private EditText nazivOglasa;
    private EditText opisOglasa;
    private EditText brojMobitela;
    private EditText mjesto;
    private Spinner zupanijaSpinner;

    private Button dalje;

    private DatabaseUserHandler dbUser;
    private DatabaseOglasHandler dbOglas;

    private Oglas oglas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oglas3);

        nazivOglasa = (EditText) findViewById(R.id.editTextNazivOglasa);
        opisOglasa = (EditText)findViewById(R.id.editTextOpisOglasa);
        brojMobitela = (EditText) findViewById(R.id.editTextbrojMobitela);
        mjesto = (EditText)findViewById(R.id.editTextMjesto);
        zupanijaSpinner = (Spinner)findViewById(R.id.spinnerZupanija);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.zupanije_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zupanijaSpinner.setAdapter(adapter);

        dbUser = new DatabaseUserHandler(getApplicationContext());
        dbOglas = new DatabaseOglasHandler(getApplicationContext());

        oglas = dbOglas.getOglasDetails();

        dalje = (Button)findViewById(R.id.bttnPredaj);

        dalje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oglas.setNaziv(String.valueOf(nazivOglasa.getText()));
                oglas.setOpis(String.valueOf(opisOglasa.getText()));
                oglas.setBrMob(String.valueOf(brojMobitela.getText()));
                oglas.setZupanija(String.valueOf(zupanijaSpinner.getSelectedItem()));
                oglas.setMjesto(String.valueOf(mjesto.getText()));


                dbOglas.updateOglas(oglas);


                startActivity(new Intent(getApplicationContext(), AddOglasSlikeActivity.class));

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_oglas3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}