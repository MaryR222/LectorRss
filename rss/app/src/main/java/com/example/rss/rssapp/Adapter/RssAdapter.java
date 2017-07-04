package com.example.rss.rssapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.rss.rssapp.BD.ScripDataBase;
import com.example.rss.rssapp.R;
import com.example.rss.rssapp.network.ClientHttp;

/**
 * Created by mrezapue on 3/7/17.
 */

public class RssAdapter extends CursorAdapter {


    private static final String TAG = RssAdapter.class.getSimpleName();

    /**
     * View holder para evitar multiples llamadas de findViewById()
     */
    static class ViewHolder {
        TextView title;
        TextView description;
        NetworkImageView image;

        int tituloI;
        int descripcionI;
        int imagenI;
    }

    public RssAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_layout, null, false);

        ViewHolder vh = new ViewHolder();


        vh.title = (TextView) view.findViewById(R.id.title);
        vh.description = (TextView) view.findViewById(R.id.descripcion);
        vh.image = (NetworkImageView) view.findViewById(R.id.image);

        // Setear indices
        vh.tituloI = cursor.getColumnIndex(ScripDataBase.ColumnEntradas.TITLE);
        vh.descripcionI = cursor.getColumnIndex(ScripDataBase.ColumnEntradas.DESCRIPCION);
        vh.imagenI = cursor.getColumnIndex(ScripDataBase.ColumnEntradas.URL_MINI);

        view.setTag(vh);

        return view;
    }

    public void bindView(View view, Context context, Cursor cursor) {

        final ViewHolder vh = (ViewHolder) view.getTag();

        // Set title
        vh.title.setText(cursor.getString(vh.tituloI));

        // description
        int ln = cursor.getString(vh.descripcionI).length();
        String descripcion = cursor.getString(vh.descripcionI);
        if (ln >= 150)
            vh.description.setText(descripcion.substring(0, 70)+"...");
        else vh.description.setText(descripcion);

        // get url image
        String thumbnailUrl = cursor.getString(vh.imagenI);


        ImageLoader imageLoader = ClientHttp.getInstance(context).getImageLoader();


        vh.image.setImageUrl(thumbnailUrl, imageLoader);

    }
}
