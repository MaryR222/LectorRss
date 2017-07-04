package com.example.rss.rssapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.rss.rssapp.BD.ScripDataBase;
import com.example.rss.rssapp.network.ClientHttp;

/**
 * Created by mrezapue on 3/7/17.
 */

public class DetailPreview extends AppCompatActivity {

    private Button go;
    private TextView title;
    private TextView description;
    NetworkImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_detail);

        //view
        go= (Button) findViewById(R.id.go);
        title = (TextView)  findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        image =(NetworkImageView) findViewById(R.id.imageDetail);


        // Get extra
        final String urlExtra = getIntent().getStringExtra("url-extra");

        String titleExtra = getIntent().getStringExtra("title");

        String decriptionExtra = getIntent().getStringExtra("description");

        String imageExtra = getIntent().getStringExtra("image");



        title.setText(titleExtra);
        description.setText(decriptionExtra);

        ImageLoader imageLoader = ClientHttp.getInstance(this).getImageLoader();


        image.setImageUrl(imageExtra, imageLoader);

        //go to url
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DetailPreview.this, DatailActivity.class);

                // Setear url
                i.putExtra("url-extra", urlExtra);

                startActivity(i);




            }
        });


    }
}
