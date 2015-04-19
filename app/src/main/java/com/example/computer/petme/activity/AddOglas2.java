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
import android.widget.Spinner;

import com.example.computer.petme.R;
import com.example.computer.petme.helper.DatabaseOglasHandler;
import com.example.computer.petme.modules.Oglas;

public class AddOglas2 extends ActionBarActivity {

    private Spinner velicinaSpinner;
    private Spinner starostSpinner;
    private Spinner bojaSpinner;
    private Button dalje;

    private Oglas oglas;
    private DatabaseOglasHandler dbOglas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oglas2);

        dbOglas = new DatabaseOglasHandler(getApplicationContext());

        velicinaSpinner = (Spinner) findViewById(R.id.spinnerVelicina);
        starostSpinner = (Spinner) findViewById(R.id.spinnerStarost);
        bojaSpinner = (Spinner) findViewById(R.id.spinnerBoja);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.velicina_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        velicinaSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.starost_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        starostSpinner.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.boja_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bojaSpinner.setAdapter(adapter3);

        oglas = dbOglas.getOglasDetails();

        dalje = (Button)findViewById(R.id.bttnNext2);

        dalje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("2 aktivnost : "+oglas.getId()+ " " + oglas.getNaziv() + " " +
                        oglas.getOpis()+ " " + oglas.getVrsta() + " "  + oglas.getPasmina());

                Log.d("oglas, naziv Ziv", oglas.getPetname());
                Log.d("Velicina", velicinaSpinner.getSelectedItem().toString());
                oglas.setVelicina(velicinaSpinner.getSelectedItem().toString());
                oglas.setStarost(starostSpinner.getSelectedItem().toString());
                oglas.setBoja(bojaSpinner.getSelectedItem().toString());

                dbOglas.updateOglas(oglas);

                //dbOglas.deleteOglasi();
                //dbOglas.addOglas(oglas);

                startActivity(new Intent(getApplicationContext(), AddOglas3.class));

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_oglas2, menu);
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
