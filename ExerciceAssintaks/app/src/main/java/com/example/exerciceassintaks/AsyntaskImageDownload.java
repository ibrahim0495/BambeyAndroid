package com.example.exerciceassintaks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyntaskImageDownload extends AsyncTask<String, Integer, Bitmap> {

    private Activity myactivity;
    public AsyntaskImageDownload(Activity activity) {
        myactivity=activity;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Log.i("ASYNTASKIMAGEDOWNLOAD", "In doInBackGround");
        publishProgress(1);
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.i("ASYNTASKIMAGEDOWNLOAD", "Connexion ok");
                throw new Exception("Failed to connect");
            }
            InputStream is = con.getInputStream();
            publishProgress(0);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Image", "Failed to load image", e);
            Log.e("error", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
       Log.i("ASYNTASKIMAGEDOWNLOAD", "in onPostExecute");
        ImageView iv = (ImageView) myactivity.findViewById(R.id.remote_image);
        Log.i("ASYNTASKIMAGEDOWNLOAD", "After findViewById...");
        if (iv == null){
            Log.i("ASYNTASKIMAGEDOWNLOAD", "iv null");
        }
        if (bitmap == null){
            Log.i("ASYNTASKIMAGEDOWNLOAD", "bitmap null");
        }
        if (iv != null && bitmap != null) {
            Log.i("ASYNTASKIMAGEDOWNLOAD", "Bitmap found");
            iv.setImageBitmap(bitmap);
        }
        else {
            Log.i("ASYNTASKIMAGEDOWNLOAD", "Problem with bitmap");
        }
        super.onPostExecute(bitmap);
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        TextView tv = (TextView) myactivity.findViewById(R.id.tv_loading);
        if (values[0] == 1) {
            tv.setText("Loading...");
        } else {
            tv.setText("");
        }

        super.onProgressUpdate(values);
    }
}
