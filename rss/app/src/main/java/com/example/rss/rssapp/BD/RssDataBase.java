package com.example.rss.rssapp.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rss.rssapp.RssParse.Item;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mrezapue on 3/7/17.
 */

public class RssDataBase extends SQLiteOpenHelper {

    //map
    private static final int COLUMN_ID= 0;
    private static final int COLUMN_TITLE= 1;
    private static final int COLUMN_DESC =2;
    private static final  int COLUMN_IRL=3;

    /**
     * **/

    private static  RssDataBase singleton;

    private static  final String TAG = RssDataBase.class.getSimpleName();

    /**
     * Name of Data Base
     * **/

    public static final String DATA_BASE_NAME = "Rss.db";

    /**
     * Version data base
     * **/
    public static  final int DATA_BASE_VERSION =1;


    private RssDataBase (Context context){
        super(context,DATA_BASE_NAME,null,DATA_BASE_VERSION);
    }

    /**
     * Return the singleton instance
     *@paran context where  the request are execute
     * **/
    public  static synchronized RssDataBase getInstance (Context context){
        if(singleton == null){
            singleton = new RssDataBase(context.getApplicationContext());

        }
        return singleton;

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //create the table

        db.execSQL(ScripDataBase.CREATE_ENTRY);
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // add the change in DB

        db.execSQL("DROP TABLE IF EXISTS" + ScripDataBase.ENTRADA_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Get all elements of the table entry
     *@return  cursor with records
     * **/

    public Cursor select(){
        // Select all row of table ENTRY

        return getWritableDatabase().rawQuery("select * from " + ScripDataBase.ENTRADA_TABLE_NAME,null);
    }

    /**
     * Insert a record
     *
     * @param titulo      title of entry
     * @param descripcion descrption of Item
     * @param url         url of Item
     * @param thumb_url   url of image
     */
    public void insert
    (
            String titulo,
            String descripcion,
            String url,
            String thumb_url) {

        ContentValues values = new ContentValues();
        values.put(ScripDataBase.ColumnEntradas.TITLE, titulo);
        values.put(ScripDataBase.ColumnEntradas.DESCRIPCION, descripcion);
        values.put(ScripDataBase.ColumnEntradas.URL, url);
        values.put(ScripDataBase.ColumnEntradas.URL_MINI, thumb_url);

        // insert the record in the data base
        getWritableDatabase().insert(
                ScripDataBase.ENTRADA_TABLE_NAME,
                null,
                values
        );
    }

    /**
     * Update values
     *
     * @param id          id of Item
     * @param titulo      new title of Item
     * @param descripcion new description of Item
     * @param url         new url of Item
     * @param thumb_url   new url image Item
     */
    public void update(int id,
                                  String titulo,
                                  String descripcion,
                                  String url,
                                  String thumb_url) {

        ContentValues values = new ContentValues();
        values.put(ScripDataBase.ColumnEntradas.TITLE, titulo);
        values.put(ScripDataBase.ColumnEntradas.DESCRIPCION, descripcion);
        values.put(ScripDataBase.ColumnEntradas.URL, url);
        values.put(ScripDataBase.ColumnEntradas.URL_MINI, thumb_url);

        // Modificar entrada
        getWritableDatabase().update(
                ScripDataBase.ENTRADA_TABLE_NAME,
                values,
                ScripDataBase.ColumnEntradas.ID + "=?",
                new String[]{String.valueOf(id)});

    }

    /**
     * Procesa una lista de items para su almacenamiento local
     * y sincronización.
     *
     * @param entries lista de items
     */
    public void sincronizarEntradas(List<Item> entries) {

        HashMap<String, Item> entryMap = new HashMap<String, Item>();
        for (Item e : entries) {
            entryMap.put(e.getTitle(), e);
        }

        /*
        #2  Get entry locals
         */
        Log.i(TAG, "Consultar items actualmente almacenados");
        Cursor c = select();
        assert c != null;
        Log.i(TAG, "Se encontraron " + c.getCount() + " entradas, computando...");

        /*
        #3  Comenzar a comparar las entradas
         */
        int id;
        String titulo;
        String descripcion;
        String url;

        while (c.moveToNext()) {

            id = c.getInt(COLUMN_ID);
            titulo = c.getString(COLUMN_TITLE);
            descripcion = c.getString(COLUMN_DESC);
            url = c.getString(COLUMN_IRL);

            Item match = entryMap.get(titulo);
            if (match != null) {
                // Filtrar entradas existentes. Remover para prevenir futura inserción
                entryMap.remove(titulo);

                /*
                #3.1 Comprobar si la entrada necesita ser actualizada
                */
                if ((match.getTitle() != null && !match.getTitle().equals(titulo)) ||
                        (match.Description() != null && !match.Description().equals(descripcion)) ||
                        (match.getLink() != null && !match.getLink().equals(url))) {
                    // Actualizar entradas
                    update(
                            id,
                            match.getTitle(),
                            match.Description(),
                            match.getLink(),
                            match.getContent().getUrl()
                    );

                }
            }
        }
        c.close();

        /*
        #4 Add new entry
        */
        for (Item e : entryMap.values()) {
            Log.i(TAG, "Insertado: titulo=" + e.getTitle());
            if(e.getContent()!=null) {
                insert(
                        e.getTitle(),
                        e.Description(),
                        e.getLink(),
                        e.getContent().getUrl()
                );
            }
        }
        Log.i(TAG, "Se actualizaron los registros");


    }

}
