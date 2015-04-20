package com.example.computer.petme.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.computer.petme.R;
import com.example.computer.petme.helper.DatabaseUserHandler;
import com.example.computer.petme.helper.SQLiteHandler;
import com.example.computer.petme.helper.SessionManager;

import java.util.HashMap;


public class MainActivity extends Activity {

    private ImageView imgOglas;
    private ImageView imgUdomi;
    private ImageView imgPronadi;
    private ImageView imgAccount;
    private Button btnLogout;

    private SQLiteHandler db;
    private DatabaseUserHandler dbUser;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAccount = (ImageView) findViewById(R.id.imgAccount);
        imgUdomi = (ImageView) findViewById(R.id.imgUdomi);
        imgPronadi = (ImageView) findViewById(R.id.imgPronadi);
        imgOglas = (ImageView) findViewById(R.id.imgOglas);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        dbUser = new DatabaseUserHandler(getApplicationContext());



        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        imgAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AccountActivity.class));
            }
        });

        imgOglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddOglas1.class));
            }
        });

        imgPronadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PronadiActivity.class));
            }
        });

        imgUdomi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UdomiActivity.class));
            }
        });
    }



    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();
        dbUser.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
