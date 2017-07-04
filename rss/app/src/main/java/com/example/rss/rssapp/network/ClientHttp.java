package com.example.rss.rssapp.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by mrezapue on 3/7/17.
 */

public final class ClientHttp {

    // Atributos
    private static ClientHttp singleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private static Context context;


    private ClientHttp(Context context) {
        ClientHttp.context = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(40);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    /**
     * Return the instance of singlton
     * @param context where the request are execute
     * @return intance
     */
    public static synchronized ClientHttp getInstance(Context context) {
        if (singleton == null) {
            singleton = new ClientHttp(context.getApplicationContext());
        }
        return singleton;
    }

    /**
     *Gets the request  of intance
     * @return cola de peticiones
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Add the request to the queue
     * @param req petición
     * @param <T> Resultado final de tipo T
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
