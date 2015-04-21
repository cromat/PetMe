package com.example.computer.petme.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.computer.petme.R;
import com.example.computer.petme.modules.Oglas;

import java.io.InputStream;
import java.util.List;

/**
 * Created by computer on 20.4.2015..
 */
public class OglasiAdapter extends RecyclerView.Adapter<OglasiAdapter.OglasViewHolder>{

    private List<Oglas> oglasi;

    @Override
    public int getItemCount () {
        return oglasi.size();
    }


    @Override
    public void onBindViewHolder (OglasViewHolder pojoViewHolder, int i) {
        pojoViewHolder.naslov.setText(oglasi.get(i).getNaziv());
        pojoViewHolder.ime.setText(oglasi.get(i).getPetname());
        pojoViewHolder.lokacija.setText(oglasi.get(i).getZupanija());
    }

    @Override
    public OglasiAdapter.OglasViewHolder onCreateViewHolder (ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_oglas_item, parent, false);
        return new OglasViewHolder(v);
    }

    public OglasiAdapter (List<Oglas> list) {

        this.oglasi = list;

    }

    public static class OglasViewHolder extends RecyclerView.ViewHolder {

        public TextView ime;
        public TextView naslov;
        public TextView lokacija;
        public ImageView slika;

        public OglasViewHolder (View view) {
            super(view);
            ime = (TextView) view.findViewById(R.id.listViewIme);
            naslov = (TextView) view.findViewById(R.id.listViewNaslov);
            lokacija = (TextView) view.findViewById(R.id.listViewLokacija);
            /*slika = (ImageView) view.findViewById(R.id.simpleItemImage);
            new DownloadImageTask(slika)
                    .execute("http://www.sevcikphoto.com/images/elephas_maximus_slon_8.jpg");*/
        }
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}