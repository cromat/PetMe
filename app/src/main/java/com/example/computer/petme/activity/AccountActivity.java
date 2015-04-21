package com.example.computer.petme.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.computer.petme.R;
import com.example.computer.petme.helper.DatabaseUserHandler;
import com.example.computer.petme.helper.SQLiteHandler;
import com.example.computer.petme.modules.User;

import java.util.HashMap;

public class AccountActivity extends ActionBarActivity {

    private DatabaseUserHandler db;

    private TextView txtUsername;
    private TextView txtFullName;
    private TextView txtEmail;
    private TextView txtPhone;
    private TextView txtLokacija;
    private TextView txtDatumReg;

    private Button bttnMojiOglasi;
    private Button bttnPostavke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        txtUsername = (TextView)findViewById(R.id.txtUsernameAcc);
        txtFullName = (TextView)findViewById(R.id.txtFullName);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtLokacija = (TextView)findViewById(R.id.txtLokacija);
        txtDatumReg = (TextView)findViewById(R.id.txtDatumReg);
        txtPhone = (TextView)findViewById(R.id.txtPhone);

        bttnPostavke = (Button)findViewById(R.id.bttnPostavke);
        bttnMojiOglasi = (Button)findViewById(R.id.bttnMojiOglasi);

        db = new DatabaseUserHandler(getApplicationContext());

        User user = db.getUserDetails();

        txtUsername.setText(user.getUsername());
        txtFullName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        txtPhone.setText(user.getPhone());
        txtLokacija.setText(user.getLokacija());

        bttnPostavke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EditAccountActivity.class));
            }
        });



    }


    @Override
    protected void onResume() {
        super.onResume();

        User user = db.getUserDetails();

        txtUsername.setText(user.getUsername());
        txtFullName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        txtPhone.setText(user.getPhone());
        txtLokacija.setText(user.getLokacija());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
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