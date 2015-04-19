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

    private void predajOglas(final int idUser, final Oglas oglas){
        // Tag used to cancel the request
        String tag_string_req = "req_upload_oglas";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPLOAD_OGLAS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "UploadOglas Response: " + response.toString());

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        dbOglas.deleteOglasi();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Oglas nije pohranjen!", Toast.LENGTH_LONG).show();
                    }

                    finish();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "UploadOglas Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("idUser", String.valueOf(idUser));
                params.put("naziv", oglas.getNaziv());
                params.put("opis", oglas.getOpis());
                params.put("petname", oglas.getPetname());
                params.put("vrsta", oglas.getVrsta());
                params.put("pasmina", oglas.getPasmina());
                params.put("spol", String.valueOf(oglas.getSpol()));
                params.put("velicina", oglas.getVelicina());
                params.put("starost", oglas.getStarost());
                params.put("boja", oglas.getBoja());
                params.put("mobitel", oglas.getBrMob());
                params.put("zupanija", oglas.getZupanija());
                params.put("mjesto", oglas.getMjesto());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
