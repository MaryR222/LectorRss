package com.example.rss.rssapp.UI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.rss.rssapp.Adapter.RssAdapter;
import com.example.rss.rssapp.BD.RssDataBase;
import com.example.rss.rssapp.BD.ScripDataBase;
import com.example.rss.rssapp.R;
import com.example.rss.rssapp.RssParse.Rss;
import com.example.rss.rssapp.network.ClientHttp;
import com.example.rss.rssapp.network.XmlRequest;

public class RssMainActivity extends AppCompatActivity {


    private static final String TAG = RssMainActivity.class.getSimpleName();

    /*
    URL del feed
     */
    public static final String URL_FEED = "http://www.europapress.es/rss/rss.aspx?ch=00069";

    private ListView listView;

    private RssAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_main);
        listView = (ListView) findViewById(R.id.list);


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

        ClientHttp.getInstance(this).addToRequestQueue(new XmlRequest<>(URL_FEED,
                Rss.class, null
                        , new Response.Listener<Rss>() {
            @Override
            public void onResponse(Rss response) {
                //save
                RssDataBase.getInstance(RssMainActivity.this).sincronizarEntradas(response.getChannel().getItems());
                //
                new LoadData().execute();
            }
        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Volley: " + error.getMessage());
                    }
                }
                )
        );

    }else{
            Log.i(TAG, "La conexion a internet no está disponible");
            adapter= new RssAdapter(
                    this,
                    RssDataBase.getInstance(this).select(),
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            listView.setAdapter(adapter);
        }

        //item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) adapter.getItem(position);

                // get url of item selected
                String url = c.getString(c.getColumnIndex(ScripDataBase.ColumnEntradas.URL));
                String title = c.getString(c.getColumnIndex(ScripDataBase.ColumnEntradas.TITLE));
                String description = c.getString(c.getColumnIndex(ScripDataBase.ColumnEntradas.DESCRIPCION));
                String image = c.getString(c.getColumnIndex(ScripDataBase.ColumnEntradas.URL_MINI));

                Intent i = new Intent(RssMainActivity.this, DetailPreview.class);

                // set url
                i.putExtra("url-extra", url);
                i.putExtra("title", title);
                i.putExtra("description", description);
                i.putExtra("image", image);



                startActivity(i);
            }
        });

    }

    public class LoadData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            // Carga inicial de registros
            return RssDataBase.getInstance(RssMainActivity.this).select();

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            // Create adapter
            adapter = new RssAdapter(
                    RssMainActivity.this,
                    cursor,
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);


            listView.setAdapter(adapter);
        }
    }



}
