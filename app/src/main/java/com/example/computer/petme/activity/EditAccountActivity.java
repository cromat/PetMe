package com.example.computer.petme.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.computer.petme.R;
import com.example.computer.petme.app.AppConfig;
import com.example.computer.petme.app.AppController;
import com.example.computer.petme.helper.DatabaseUserHandler;
import com.example.computer.petme.helper.SQLiteHandler;
import com.example.computer.petme.modules.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditAccountActivity extends ActionBarActivity {

    private DatabaseUserHandler dbUser;

    private EditText edtUsername;
    private EditText edtFullname;
    private EditText edtLocation;
    private EditText edtPhone;
    private EditText edtEmail;
    private User user;
    private Button bttnPohrani;

    private static final String TAG = EditAccountActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtFullname = (EditText) findViewById(R.id.edtFullname);
        edtLocation = (EditText) findViewById(R.id.edtLokacija);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        bttnPohrani = (Button)findViewById(R.id.bttnPohraniPromjene);

        dbUser = new DatabaseUserHandler(getApplicationContext());

        user = dbUser.getUserDetails();

        edtUsername.setText(user.getUsername());
        edtFullname.setText(user.getName());
        edtLocation.setText(user.getLokacija());
        edtPhone.setText(user.getPhone());
        edtEmail.setText(user.getEmail());

        bttnPohrani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setLokacija(edtLocation.getText().toString());
                user.setName(edtFullname.getText().toString());
                user.setPhone(edtPhone.getText().toString());
                editUser(user);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_account, menu);
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

    private void editUser(final User user) {
        // Tag used to cancel the request
        String tag_string_req = "req_edit_user";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_EDIT_ACCOUNT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "EditUser Response: " + response.toString());
                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // Inserting row in users table
                        dbUser.updateUser(user);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Edit Account", "Edit Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user.getUsername());
                params.put("fullname", user.getName());
                params.put("lokacija", user.getLokacija());
                params.put("phone", user.getPhone());

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}