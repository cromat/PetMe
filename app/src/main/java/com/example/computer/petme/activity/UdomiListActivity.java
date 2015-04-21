package com.example.computer.petme.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.computer.petme.R;
import com.example.computer.petme.adapter.OglasiAdapter;
import com.example.computer.petme.app.AppConfig;
import com.example.computer.petme.app.AppController;
import com.example.computer.petme.modules.Oglas;
import com.example.computer.petme.modules.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UdomiListActivity extends ActionBarActivity {

    private String zadnjiDatum;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recycleList;
    private List<Oglas> oglasiArray;
    private static final String TAG = UdomiListActivity.class.getSimpleName();
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udomi_list);

        zadnjiDatum = "prvi";

        oglasiArray = new ArrayList<>();

        getOglasi();

        recycleList = (RecyclerView)findViewById(R.id.recycler_view);

        recycleList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleList.setLayoutManager(layoutManager);

        adapter = new OglasiAdapter(oglasiArray);
        recycleList.setAdapter(adapter);
        adapter.notifyItemInserted(oglasiArray.size());


        recycleList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ( (visibleItemCount+pastVisiblesItems) >= totalItemCount-5) {
                        loading = false;
                        Log.d("...", "Last Item Wow !");
                        getOglasi();

                    }
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_udomi_list, menu);
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

    private void getOglasi(){
        // Tag used to cancel the request
        String tag_string_req = "req_get_all_oglasi";
        Log.d("GetAllOglasi Response: " ,"uso");


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_ALL_OGLASI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "GetAllOglasi Response: " + response.toString());
                Log.d("GetAllOglasi Response: " ,"uso u response");

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jObj = new JSONObject();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jObj = jsonArray.getJSONObject(i);

                        Oglas oglas = new Oglas(jObj.getInt("id"),
                                jObj.getString("naziv"),
                                jObj.getString("opis"),
                                jObj.getInt("iduser"),
                                jObj.getString("datum"),
                                jObj.getString("petname"),
                                jObj.getString("vrsta"),
                                jObj.getString("pasmina"),
                                Boolean.valueOf(jObj.getString("spol")),
                                jObj.getString("velicina"),
                                jObj.getString("starost"),
                                jObj.getString("boja"),
                                jObj.getString("mobitel"),
                                jObj.getString("zupanija"),
                                jObj.getString("mjesto"));
                        Log.d("GetAllOglasi Response: " ,"dodaje oglas");

                        Log.d("id", String.valueOf(oglas.getId()));
                        Log.d("opis", String.valueOf(oglas.getOpis()));
                        Log.d("zupanija", String.valueOf(oglas.getZupanija()));

                        oglasiArray.add(oglas);
                        adapter.notifyItemInserted(oglasiArray.size());
                    }
                    zadnjiDatum = oglasiArray.get(oglasiArray.size()-1).getDatum();
                    Log.d("Datum ",zadnjiDatum);
                    loading = true;


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "GetUser Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("zadnjiDatum", zadnjiDatum);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}