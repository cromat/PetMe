package com.example.computer.petme.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.computer.petme.R;
import com.example.computer.petme.helper.DatabaseOglasHandler;
import com.example.computer.petme.modules.Oglas;

public class AddOglas1 extends ActionBarActivity {

    private EditText nazivZivotinje;
    private Spinner vrstaSpinner;
    private EditText pasmina;

    private Button dalje;

    private DatabaseOglasHandler dbOglas;
    private Oglas oglas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oglas1);

        dbOglas = new DatabaseOglasHandler(getApplicationContext());
        dbOglas.deleteOglasi();

        nazivZivotinje = (EditText)findViewById(R.id.editTextNazivZivotinje);
        vrstaSpinner = (Spinner) findViewById(R.id.spinnerVrsta);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vrsta_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vrstaSpinner.setAdapter(adapter);

        pasmina = (EditText)findViewById(R.id.editTextPasmina);
        dalje = (Button)findViewById(R.id.bttnNext1);

        dbOglas.addEmptyOglas();
        dalje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oglas = dbOglas.getOglasDetails();

                System.out.println("Prije : "+oglas.getId()+ " " + oglas.getNaziv() + " " +
                        oglas.getOpis()+ " " + oglas.getVrsta() + " "  + oglas.getPasmina());
                oglas.setPetname(nazivZivotinje.getText().toString());
                oglas.setVrsta(vrstaSpinner.getSelectedItem().toString());
                oglas.setPasmina(pasmina.getText().toString());

                System.out.println("Poslije : "+oglas.getId()+ " ," + oglas.getNaziv() + ", " +
                        oglas.getOpis()+ ", " + oglas.getVrsta() + ", "  + oglas.getPasmina());

                dbOglas.deleteOglasi();

                System.out.println("Velicina poslije brisanja svih oglasa : " + dbOglas.getRowCount());
                dbOglas.addOglas(oglas);
                System.out.println("Velicina poslije dodavanja oglasa : " + dbOglas.getRowCount());
                oglas = dbOglas.getOglasDetails();
                System.out.println("Poslije update oglasa : "+oglas.getId()+ " " + oglas.getNaziv() + " " +
                        oglas.getOpis()+ " " + oglas.getVrsta() + " "  + oglas.getPasmina());

                Intent intent = new Intent(getApplicationContext(), AddOglas2.class);

                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_oglas1, menu);
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
