package com.example.laughshare;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    //To display the image after getting the JSON

    ProgressBar progressBar;
    //To show the user that image is loading | Glide will take time to load & download the image

    String imageUrl;
    private GestureDetectorCompat mGestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        IsFirstRunAndload();
        mGestureDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if ((e1.getX() > e2.getX())) { //load meme when swiped R2L
                    loadMeme();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void IsFirstRunAndload() {
        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstrun) {
            //... Display the dialog message here ...
            // Save the state
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(R.string.warningDialogHeading);
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "Okay",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            loadMeme();

                            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                                    .edit()
                                    .putBoolean("firstrun", false)
                                    .apply();

                            dialog.cancel();
                        }
                    });


            AlertDialog alert11 = builder1.create();


            alert11.show();

        } else {
            loadMeme();
        }


    }


    public void ShareMeme(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, Checkout this meme from Reddit : \n" + imageUrl);
        startActivity(intent);
    }


    private void loadMeme() {


        progressBar.setVisibility(View.VISIBLE);

        String url = "https://meme-api.herokuapp.com/gimme";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //on response we want to use the image url to display the meme
                        try {
                            imageUrl = response.getString("url");
                            // if we are getting it correctly , we need to display that image with the help of GLIDE LIBRARY
                            Glide.with(MainActivity.this).load(imageUrl).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(imageView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);

                        if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof TimeoutError) {
                            showDialog();
                        } else {
                            Toast.makeText(MainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    private void showDialog() {
        //crete &Show  Alert Dialog
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this).setView(inflater.inflate(R.layout.dialog_view, null)).
                        setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                loadMeme();
                            }
                        });
        builder.create().show();
    }
}