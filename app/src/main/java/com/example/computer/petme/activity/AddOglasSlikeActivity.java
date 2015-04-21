package com.example.computer.petme.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddOglasSlikeActivity extends ActionBarActivity {

    private static final String TAG = AddOglasSlikeActivity.class.getSimpleName();

    private static final int SELECT_SLIKA_1 = 1;
    private static final int SELECT_SLIKA_2 = 2;
    private static final int SELECT_SLIKA_3 = 3;
    private static final int SELECT_SLIKA_4 = 4;
    private static final int SELECT_SLIKA_5 = 5;

    private Button predajOglas;
    private DatabaseOglasHandler dbOglas;
    private DatabaseUserHandler dbUser;
    private Oglas oglas;
    private User user;
    private String idNovogOglasa;
    HttpEntity resEntity;
    ProgressDialog progressDialog;

    private ImageView slika1;
    private ImageView slika2;
    private ImageView slika3;
    private ImageView slika4;
    private ImageView slika5;

    private Uri uriSlika1;
    private Uri uriSlika2;
    private Uri uriSlika3;
    private Uri uriSlika4;
    private Uri uriSlika5;

    String selectedPath1 = "NONE";
    String selectedPath2 = "NONE";
    String selectedPath3 = "NONE";
    String selectedPath4 = "NONE";
    String selectedPath5 = "NONE";

    ArrayList<String> slikeString;

    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oglas_slike);

        this.dbOglas = new DatabaseOglasHandler(getApplicationContext());
        this.oglas = dbOglas.getOglasDetails();
        this.dbUser = new DatabaseUserHandler(getApplicationContext());
        this.predajOglas = (Button) findViewById(R.id.bttnPredajOglas);
        slikeString = new ArrayList<String>();

        slika1 = (ImageView) findViewById(R.id.imageViewGlavnaSlika);
        slika2 = (ImageView) findViewById(R.id.imageViewSlika2);
        slika3 = (ImageView) findViewById(R.id.imageViewSlika3);
        slika4 = (ImageView) findViewById(R.id.imageViewSlika4);
        slika5 = (ImageView) findViewById(R.id.imageViewSlika5);

        user = dbUser.getUserDetails();
        predajOglas();

        uriSlika1 = null;
        uriSlika2 = null;
        uriSlika3 = null;
        uriSlika4 = null;
        uriSlika5 = null;

        slika1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,SELECT_SLIKA_1);
            }
        });

        slika2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, SELECT_SLIKA_2);
            }
        });

        slika3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, SELECT_SLIKA_3);
            }
        });

        slika4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, SELECT_SLIKA_4);
            }
        });

        slika5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, SELECT_SLIKA_5);
            }
        });


        this.predajOglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(selectedPath1 + " " + selectedPath2);
                if(!(selectedPath1.trim().equalsIgnoreCase("NONE")) && !(selectedPath2.trim().equalsIgnoreCase("NONE"))
                        && !(selectedPath3.trim().equalsIgnoreCase("NONE")) && !(selectedPath4.trim().equalsIgnoreCase("NONE"))
                        && !(selectedPath5.trim().equalsIgnoreCase("NONE"))){
                    progressDialog = ProgressDialog.show(AddOglasSlikeActivity.this, "", "Uploading files to server.....", false);
                    Thread thread=new Thread(new Runnable(){
                        public void run(){
                            System.out.println("Pocetak doFileUpload");
                            doFileUpload();
                            runOnUiThread(new Runnable(){
                                public void run() {
                                    if(progressDialog.isShowing())
                                        progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Oglas je predan, zahvaljujemo !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    dbOglas.deleteOglasi();
                                    finish();
                                }
                            });
                        }
                    });
                    thread.start();
                }else{
                    Toast.makeText(getApplicationContext(),"Molimo vas odaberite fotografije ljubimca !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_SLIKA_1){
                uriSlika1 = data.getData();
                System.out.println("UriSLika1: " + uriSlika1);
                selectedPath1 = getPath(uriSlika1);
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriSlika1);
                    slika1.setImageBitmap(bitmap);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            else if (requestCode == SELECT_SLIKA_2){
                uriSlika2 = data.getData();
                selectedPath2 = getPath(uriSlika2);
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriSlika2);
                    slika2.setImageBitmap(bitmap);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            else if (requestCode == SELECT_SLIKA_3){
                uriSlika3 = data.getData();
                selectedPath3 = getPath(uriSlika3);
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriSlika3);
                    slika3.setImageBitmap(bitmap);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            else if (requestCode == SELECT_SLIKA_4){
                uriSlika4 = data.getData();
                selectedPath4 = getPath(uriSlika4);
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriSlika4);
                    slika4.setImageBitmap(bitmap);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            else if (requestCode == SELECT_SLIKA_5){
                uriSlika5 = data.getData();
                selectedPath5 = getPath(uriSlika5);
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriSlika5);
                    slika5.setImageBitmap(bitmap);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }


    private void doFileUpload(){

        File file1 = new File(selectedPath1);
        File file2 = new File(selectedPath2);
        File file3 = new File(selectedPath3);
        File file4 = new File(selectedPath4);
        File file5 = new File(selectedPath5);

        String urlString = AppConfig.URL_UPLOAD_SLIKE;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urlString);

            FileBody bin1 = new FileBody(file1);
            FileBody bin2 = new FileBody(file2);
            FileBody bin3 = new FileBody(file3);
            FileBody bin4 = new FileBody(file4);
            FileBody bin5 = new FileBody(file5);

            MultipartEntity reqEntity = new MultipartEntity();

            reqEntity.addPart("uploadedfile1", bin1);
            reqEntity.addPart("uploadedfile2", bin2);
            reqEntity.addPart("uploadedfile3", bin3);
            reqEntity.addPart("uploadedfile4", bin4);
            reqEntity.addPart("uploadedfile5", bin5);
            reqEntity.addPart("idoglas", new StringBody(idNovogOglasa));

            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            if (resEntity != null) {
                Log.i("RESPONSE",response_str);
            }
        }
        catch (Exception ex){
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
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

    private void predajOglas(){
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
                    idNovogOglasa = String.valueOf(jObj.getInt("idoglas"));
                    System.out.println("idOglasa: " + idNovogOglasa);

                    // Check for error node in json
                    if (!error) {
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Oglas nije pohranjen!", Toast.LENGTH_LONG).show();
                    }

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
                params.put("idUser", String.valueOf(user.getId()));
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
