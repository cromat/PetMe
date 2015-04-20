package com.example.computer.petme.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class AddOglasSlikeActivity extends ActionBarActivity {

    private static final String TAG = AddOglasSlikeActivity.class.getSimpleName();

    private Button predajOglas;
    private DatabaseOglasHandler dbOglas;
    private DatabaseUserHandler dbUser;
    private Oglas oglas;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oglas_slike);

        this.dbOglas = new DatabaseOglasHandler(getApplicationContext());
        oglas = dbOglas.getOglasDetails();

        System.out.println("na kraju oglasa, prije predaje : "+oglas.getId()+ " " + oglas.getNaziv() + " " +
                oglas.getOpis()+ " " + oglas.getVrsta() + " "  + oglas.getPasmina()+" "+ oglas.getBoja()+ " "+
                oglas.getStarost()+" " + oglas.getVelicina()+" "+ oglas.getOpis()+" "+oglas.getPetname()+" "
                +oglas.getSpol() + " " + oglas.getBrMob() + " " + oglas.getZupanija() + " " + oglas.getMjesto());

        this.dbUser = new DatabaseUserHandler(getApplicationContext());
        this.predajOglas = (Button) findViewById(R.id.bttnPredajOglas);




        this.predajOglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = dbUser.getUserDetails();
                predajOglas(user.getId(), oglas);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_oglas_slike, menu);
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
                Log.d(TAG, "EditUser Response: " + response.toString());

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
